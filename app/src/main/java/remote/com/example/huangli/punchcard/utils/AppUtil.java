package remote.com.example.huangli.punchcard.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.provider.Settings;
import android.telephony.TelephonyManager;

public class AppUtil {

    public static int dp2px(Context context,float dp) {
        final float dentisy = context.getResources().getDisplayMetrics().density;
        return (int) (0.5f + dentisy * dp);
    }

    public static String getMetaData(Context context, String key) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            return applicationInfo.metaData.getString(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getSystemLanguage(Context context){
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        return config.locale.getLanguage();
    }

    private static String getStringValue(Context context, String name,String defaultValue) {
        try{
            int resid = context.getResources().getIdentifier(name, "string", context.getPackageName());
            if(resid>0){
                return context.getString(resid);
            }
        } catch (Exception e){

        }
        return defaultValue;
    }

    public static String[] getArrayValue(Context context, String name) {
        try{
            int resid = context.getResources().getIdentifier(name, "array", context.getPackageName());
            if(resid>0){
                return context.getResources().getStringArray(resid);
            }
        } catch (Exception e){ }
        return new String[0];
    }

    public static String getAndroidVersionCode(){
        return android.os.Build.VERSION.SDK;
    }

    public static String getDeviceBrand(){
        return android.os.Build.BRAND;
    }

    public static int getAppVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
        }
        return -1;
    }

    public static String getAppVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            return "unknown";
        }
    }

    public static String getIMEI(Context context) {
        String imei = null;
        TelephonyManager phoneMgr = (TelephonyManager) context.getSystemService(
                Context.TELEPHONY_SERVICE);
        if (phoneMgr != null)
            imei = phoneMgr.getDeviceId();

        return imei;
    }

    public static String getIMSI(Context context) {
        String imsi = null;
        TelephonyManager phoneMgr = (TelephonyManager) context.getSystemService(
                Context.TELEPHONY_SERVICE);
        if (phoneMgr != null)
            imsi = phoneMgr.getSubscriberId();
        return imsi;
    }


    public static int getVersionCode(Context context) {
        try {
            PackageManager pkgManager = context.getPackageManager();
            PackageInfo info = pkgManager.getPackageInfo(context.getPackageName(), 0);
            if (info != null)
                return info.versionCode;
        } catch (NameNotFoundException e) {
        }
        return 0;
    }

    public static String getVersionName(Context context) {
        try {
            PackageManager pkgManager = context.getPackageManager();
            PackageInfo info = pkgManager.getPackageInfo(context.getPackageName(), 0);
            if (info != null)
                return info.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isSimExist(Context ctx) {
        TelephonyManager telMgr = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        int state = telMgr.getSimState();
        return state == TelephonyManager.SIM_STATE_ABSENT ? false : true;
    }

    public static String getSimSerial(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(
                Context.TELEPHONY_SERVICE);
        if (tm.getSimState() == TelephonyManager.SIM_STATE_READY) {
            return tm.getSimSerialNumber();
        }
        return null;
    }

    public static String getAndroidID(Context ctx) {
        return Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

}