package remote.com.example.huangli.punchcard.http;

/**
 * Created by wangjun on 16/3/28.
 */
public class HttpProtocol {

    protected static boolean MOCK_ENABLE = true;
    protected static boolean ENCRY_ENABLE = true;
    private static Env PROD_ENV = Env.PROD;//生产环境
    private static class Domain{

    }

    private static String get(String prod,String prodTest,String test){
        if(PROD_ENV == Env.PROD){
            return prod;
        } else if(PROD_ENV == Env.PROD_TEST){
            return prodTest;
        }
        return test;
    }

    public static class URLS{
        public final static String USER_LOGIN = "http://www.baidu.com/poji_login";
        public final static String USER_SIGNIN = "http://www.baidu.com/poji_signin";
        public final static String REQUEST_CUR_PLAN = "http://www.baidu.com/request_poji_cur_plan";
        public final static String REQUEST_PLANS = "http://www.baidu.com/request_poji_plan";
        public final static String ADD_PLAN = "http://www.baidu.com/add_poji_plan";
    }
    //http 返回码
    public static class CODE{
        public final static int NET_ERROR = -0x01;
        public final static int OK = 0x00;
        public final static int REOK = -2001;
        public final static int FAIL = 0x02;
        public final static int TOKEN_ERROR = 0x03;
    }
    //http 请求头
    public static class Header{
        public static final String CIPHER_SPEC = "x-cipher-spec";
        public static final String PACKAGE_NAME = "package-name";
        public static final String PACKAGE_VER = "package-ver";
        public static final String TOKEN = "token";
        public static final String UID = "uid";
        public static final String COUNTRY = "country";
        public static final String LANGUAGE = "lang";
    }

    //http 加密信息
    public static class Secure{
        public static final String AES_KEY = "8vA6au9Z";
    }

}
