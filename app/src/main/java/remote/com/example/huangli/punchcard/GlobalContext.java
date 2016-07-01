package remote.com.example.huangli.punchcard;

import android.content.Context;

/**
 * Created by wangjun on 16/1/7.
 */
public class GlobalContext {

    private static Context mContext;

    public static void setContext(Context context){
        mContext = context;
    }

    public static Context get(){
        return mContext;
    }

}
