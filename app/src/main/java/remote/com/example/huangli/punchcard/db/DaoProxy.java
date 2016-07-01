package remote.com.example.huangli.punchcard.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;

import remote.com.example.huangli.punchcard.dao.DaoMaster;
import remote.com.example.huangli.punchcard.dao.DaoSession;
import remote.com.example.huangli.punchcard.utils.PCLog;
import remote.com.example.huangli.punchcard.utils.PlatformUtils;
import remote.com.example.huangli.punchcard.utils.ToastUtils;

/**
 * Created by huangli on 16/6/27.
 */
public class DaoProxy {

    private static final String TAG = "DaoProxy";

    private volatile static DaoProxy sInstance;

    private DaoSession mDaoSession;

    private DaoProxy(Context app) {
        // Initiate Session
        try {
            DbHelper helper = new DbHelper(app);

            SQLiteDatabase db = helper.getWritableDatabase();
            if (Build.VERSION.SDK_INT >= PlatformUtils.VERSION_CODES.HONEYCOMB) {
                try {
                    db.enableWriteAheadLogging();
                } catch (Exception e) {
                }
            }

            DaoMaster master = new DaoMaster(db);
            mDaoSession = master.newSession();
        } catch (SQLiteException e) {
            SQLiteException ee = e;
            ToastUtils.showShortToast(app,ee.getLocalizedMessage());
        }
    }

    public synchronized static DaoProxy getInstance(Context context) {
        if (sInstance == null) {
            if (context != null) {
                Context app = context.getApplicationContext();
                sInstance = new DaoProxy(app);
            } else {
                PCLog.i(TAG + "getInstance(): context = NULL!");
            }
        }

        return sInstance;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
