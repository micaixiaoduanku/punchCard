package remote.com.example.huangli.punchcard.http;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import remote.com.example.huangli.punchcard.GlobalContext;
import remote.com.example.huangli.punchcard.constants.PrefConstants;
import remote.com.example.huangli.punchcard.http.mock.MockMaker;
import remote.com.example.huangli.punchcard.pojo.Pojo_Result;
import remote.com.example.huangli.punchcard.utils.AesUtil;
import remote.com.example.huangli.punchcard.utils.AppUtil;
import remote.com.example.huangli.punchcard.utils.LanguageUtil;
import remote.com.example.huangli.punchcard.utils.Md5Util;
import remote.com.example.huangli.punchcard.utils.PCLog;
import remote.com.example.huangli.punchcard.utils.PrefUtils;
import remote.com.example.huangli.punchcard.utils.RandomStringUtils;

/**
 * Created by wangjun on 16/3/28.
 */
public class Network {

    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private OkHttpClient mOkHttpClient;
    private MockMaker mMockMaker;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private String mCountryCode;
    private static Network mInstance;

    private Network(Context context) {
        mContext = context;
        mOkHttpClient = new OkHttpClient();
        mMockMaker = new MockMaker(context);
        mOkHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
    }

    public static Network get(Context context) {
        if (mInstance == null) {
            synchronized (Network.class) {
                if (mInstance == null) {
                    mInstance = new Network(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    public OkHttpClient getHttpClient(){
        return mOkHttpClient;
    }


    public <T> T syncPost(String url,Map<String,Object> params,Class<T> className) throws HttpError{
        if (HttpProtocol.MOCK_ENABLE&&false) {
            return null;
        } else {
            return syncHttpPost(url,params,className);
        }
    }

    public <T extends Pojo_Result> void asyncPost(String url, Map<String, Object> params, final JsonCallBack<T> jsonCallBack) {
        if (HttpProtocol.MOCK_ENABLE) {
            if (asyncMockPost(url, params, jsonCallBack) == 0) {
                asyncHttpPost(url, params, jsonCallBack);
            }
        } else {
            asyncHttpPost(url, params, jsonCallBack);
        }
    }

    private <T extends Pojo_Result> void asyncHttpPost(String url, Map<String, Object> params, final JsonCallBack<T> jsonCallBack) {
        Call call = buildHttpCall(url, params);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                callResponeFailOnUi(HttpProtocol.CODE.NET_ERROR, "", e, jsonCallBack);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    handleResponse(response, jsonCallBack);
                } catch (IOException e) {
                    jsonCallBack.onFailed(HttpProtocol.CODE.NET_ERROR, "io error", e);
                }
            }
        });
    }

    private Call buildHttpCall(String url, Map<String, Object> params){
        String fixedUrl = buildUrl(url);
        PCLog.d(TAG + " buildHttpCall2:" + fixedUrl);
        Request.Builder builder = new Request.Builder().url(fixedUrl);
        builder.headers(buildDefaultHeader());
        builder.post(toRequestBody(url, params));
        Request request = builder.build();
        return mOkHttpClient.newCall(request);
    }

    private RequestBody toRequestBody(String url,Map<String, Object> params) {
        if (HttpProtocol.ENCRY_ENABLE) {
            String data = toJson(url, params);
            String randomKey = RandomStringUtils.getRandomString(8);
            String realKey = Md5Util.md5Hex(randomKey + HttpProtocol.Secure.AES_KEY);
            byte[] binaryKey = AesUtil.hex2byte(realKey);
            String encryptContent = AesUtil.encrypt(data, binaryKey);
            String wrapEncryContent = randomKey + encryptContent;

            //        Integer.toBinaryString(a)
            PCLog.d(TAG + " data:" + data);
//            HBLog.d(TAG + " randomKey:" + randomKey);
//            HBLog.d(TAG + " realKey:" + realKey);
//            HBLog.d(TAG + " encryptStr:" + encryptContent);
//            HBLog.d(TAG + " wrapContent:" + wrapEncryContent);
            return RequestBody.create(MediaType.parse("text/plain"), wrapEncryContent);
        } else {
            String data = toJson(url, params);
            PCLog.d(TAG + " data:" + data);
            return RequestBody.create(MediaType.parse("text/plain"), data);
            /*
            FormEncodingBuilder builder = new FormEncodingBuilder();
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
            return builder.build(); */
        }
    }

    private String toJson(String url,Map<String, Object> params) {
        try {
            JSONObject jsonObject = new JSONObject();
            if(params!=null){
                for (String key : params.keySet()) {
                    jsonObject.put(key, params.get(key));
                }
            }
            jsonObject.put(HttpProtocol.Header.TOKEN, PrefUtils.getString(PrefConstants.Network.TOKEN, ""));
            jsonObject.put(HttpProtocol.Header.UID, PrefUtils.getString(PrefConstants.Network.uid, ""));
            jsonObject.put(HttpProtocol.Header.LANGUAGE, LanguageUtil.getLocalLanguage(mContext));
            jsonObject.put(HttpProtocol.Header.COUNTRY, mCountryCode);
            String returnJson = jsonObject.toString();
//            HBLog.i(TAG+" returnJson "+returnJson);
            return returnJson;
        } catch (JSONException e) {
            //ignore
        }
        return null;
    }

    private <T extends Pojo_Result> int asyncMockPost(String url, Map<String, Object> params, final JsonCallBack<T> jsonCallBack) {
        final String responseString = mMockMaker.createMock(url, params);
        if(TextUtils.isEmpty(responseString)){
            return 0;
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep((long) (0.5*1000));
                        Class<T> className = jsonCallBack.getObjectClass();
                        T t = parseResponse(responseString, className);
                        if (t.code == HttpProtocol.CODE.OK){
                            callResponeSuccessOnUi(t, jsonCallBack);
                        }else {
                            callResponeFailOnUi(t.code, t.msg, null, jsonCallBack);
                        }
                    } catch (Exception e) {
                        PCLog.w(TAG+" asyncMockPost",e);
                        callResponeFailOnUi(HttpProtocol.CODE.NET_ERROR, "", e, jsonCallBack);
                    }
                }
            }).start();
            return 1;
        }
    }

    private <T> T syncHttpPost(String url,Map<String, Object> params,Class<T> className) throws HttpError{
        PCLog.d(TAG + " syncHttpPost url:" + url);
        Call call = buildHttpCall(url, params);
        try {
            Response response = call.execute();
            String responseString = response.body().string();
            PCLog.d(TAG + " handleResponse step:" + responseString);
            if (HttpProtocol.ENCRY_ENABLE) {
                responseString = decryptString(responseString);
            }
            PCLog.d(TAG + " handleResponse " + responseString);
            JSONObject jsonObject = new JSONObject(responseString);
            int code = jsonObject.getInt("code");
            String msg = jsonObject.getString("msg");
            if (code == HttpProtocol.CODE.OK) {
                String object = jsonObject.getString("data");
                return parseResponse(object, className);
            } else {
                throw new HttpError(code,msg,null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            PCLog.w(TAG + " handleResponse ", e);
            throw new HttpError(-1,"",e);
        }
    }

    public void asyncPostPics(String url,Map<String, Object> params,List<File> files){
        List<RequestBody> fileBodys = new ArrayList<>();
        for (File file : files) {
            RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
            fileBodys.add(fileBody);
        }
        MultipartBuilder multipartBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);
        multipartBuilder.addPart(buildDefaultHeader(), toRequestBody(url,params));

        for (RequestBody fileBody : fileBodys) {
            multipartBuilder.addPart(buildDefaultHeader(), fileBody);
        }
        RequestBody requestBody = multipartBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                PCLog.e(TAG + "error ", e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                PCLog.e(TAG + response.body().string());
            }
        });
    }

    private <T extends Pojo_Result> void callResponeFailOnUi(final int code, final String message, final Exception e, final JsonCallBack<T> jsonCallBack) {
        mHandler.post(new Runnable() {
            public void run() {
                jsonCallBack.onFailed(code, message, e);
            }
        });
    }

    private <T extends Pojo_Result> void callResponeSuccessOnUi(final T t, final JsonCallBack<T> jsonCallBack) {
        mHandler.post(new Runnable() {
            public void run() {
                jsonCallBack.onSuccess(t);
            }
        });
    }

    private <T extends Pojo_Result> void handleResponse(Response response, JsonCallBack<T> jsonCallBack) throws IOException {
        try {
            String responseString = response.body().string();
            PCLog.d(TAG + " handleResponse step:" + responseString);
            if (HttpProtocol.ENCRY_ENABLE) {
                responseString = decryptString(responseString);
            }
            PCLog.d(TAG + " handleResponse " + responseString);
            JSONObject jsonObject = new JSONObject(responseString);
            int code = jsonObject.getInt("code");
            String msg = jsonObject.getString("msg");
            if (code == HttpProtocol.CODE.OK||code == HttpProtocol.CODE.REOK) {
                String object = jsonObject.getString("data");
                T t = parseResponse(object, jsonCallBack.getObjectClass());
                callResponeSuccessOnUi(t, jsonCallBack);
            } else {
                callResponeFailOnUi(code, msg, null, jsonCallBack);
            }
        } catch (Exception e) {
            callResponeFailOnUi(HttpProtocol.CODE.NET_ERROR, "json error", e, jsonCallBack);
            PCLog.w(TAG + " handleResponse ", e);
        }
    }

    public  <T> T parseResponse(String response, Class<T> className) {
        final Gson gson = new GsonBuilder().create();
        return gson.fromJson(response, className);
    }

    private String buildUrl(String url){
        if(url.contains("?")){
            return url+"&country="+mCountryCode+"&"+HttpProtocol.Header.LANGUAGE+"=" +LanguageUtil.getLocalLanguage(mContext);
        } else {
            return url+"?country="+mCountryCode+"&"+HttpProtocol.Header.LANGUAGE+"=" +LanguageUtil.getLocalLanguage(mContext);
        }
    }

    private Headers buildDefaultHeader() {
        Headers.Builder builder = new Headers.Builder();
        builder.add(HttpProtocol.Header.CIPHER_SPEC, "1");
        builder.add(HttpProtocol.Header.PACKAGE_NAME,GlobalContext.get().getPackageName());
        builder.add(HttpProtocol.Header.PACKAGE_VER, String.valueOf(AppUtil.getVersionCode(GlobalContext.get())));
//        builder.add(HttpProtocol.Header.TOKEN, PrefUtils.getString(PrefConstants.Network.TOKEN,""));
        return builder.build();
    }

    public Headers buildFileUploadHeader(){
        Headers.Builder builder = new Headers.Builder();
        builder.add(HttpProtocol.Header.CIPHER_SPEC, "1");
        builder.add(HttpProtocol.Header.PACKAGE_NAME,GlobalContext.get().getPackageName());
        builder.add(HttpProtocol.Header.PACKAGE_VER, String.valueOf(AppUtil.getVersionCode(GlobalContext.get())));
        builder.add(HttpProtocol.Header.TOKEN, PrefUtils.getString(PrefConstants.Token.TOKEN, ""));
        builder.add(HttpProtocol.Header.UID, PrefUtils.getString(PrefConstants.UserInfo.UID, ""));
        return builder.build();
    }

    public String parseResponse(String responseString){
        if (HttpProtocol.ENCRY_ENABLE) {
            return decryptString(responseString);
        }
        return responseString;
    }

    private String decryptString(String response) {
        String prefix = response.substring(0, 8);
        String fixResponse = response.substring(8);
        String realKey = Md5Util.md5Hex(prefix + HttpProtocol.Secure.AES_KEY);
        byte[] binaryKey = AesUtil.hex2byte(realKey);
        return AesUtil.decrypt(fixResponse, binaryKey);
    }

    private String decodeFileUrl(String url){
        if(url.contains("cdn.avazu.net")){
            return url.replace("cdn.avazu.net","192.168.5.254");
        }
        return url;
    }

    public interface JsonCallBack<T extends Pojo_Result> {
        void onSuccess(T t);
        void onFailed(int code, String message, Exception e);
        Class<T> getObjectClass();
    }

}
