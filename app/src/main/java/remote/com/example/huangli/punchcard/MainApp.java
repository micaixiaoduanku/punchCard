package remote.com.example.huangli.punchcard;

import android.app.Application;

import remote.com.example.huangli.punchcard.pojo.Pojo_User;
import remote.com.example.huangli.punchcard.utils.PrefUtils;

/**
 * Created by huangli on 16/6/28.
 */
public class MainApp extends Application {

    public static Pojo_User user;

    @Override
    public void onCreate() {
        super.onCreate();
        GlobalContext.setContext(this);
    }

}