package remote.com.example.huangli.punchcard.utils;

import android.content.Context;

/**
 * Created by wangjun on 16/6/6.
 */
public class LanguageUtil {

    public static String getLocalLanguage(Context context){
        String language = AppUtil.getSystemLanguage(context);
        if(language.equals(AppUtil.getMetaData(context,"country"))){
            return language;
        }
        return "en";
    }

}
