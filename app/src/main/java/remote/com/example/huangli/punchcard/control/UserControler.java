package remote.com.example.huangli.punchcard.control;

import android.content.Context;

import remote.com.example.huangli.punchcard.constants.PrefConstants;
import remote.com.example.huangli.punchcard.utils.PrefUtils;

/**
 * Created by huangli on 16/7/10.
 */
public class UserControler {
    public static String getAccountFromPref(){
        return PrefUtils.getString(PrefConstants.UserInfo.ACCOUNT,"");
    }
    public static String getPasswordFromPref(){
        return PrefUtils.getString(PrefConstants.UserInfo.PASSWORD,"");
    }
    public static void putAccountToPref(String account){
        PrefUtils.putString(PrefConstants.UserInfo.ACCOUNT,account);
    }
    public static void putPasswordToPref(String password){
        PrefUtils.putString(PrefConstants.UserInfo.PASSWORD,password);
    }
}
