package remote.com.example.huangli.punchcard.db;

import android.content.Context;

import remote.com.example.huangli.punchcard.dao.DaoMaster;

/**
 * Created by huangli on 16/6/27.
 */
public class DbHelper extends DaoMaster.DevOpenHelper {

    public static final String SCHEMA_NAME = "punch_db.db";

    public DbHelper(Context context) {
        super(context, SCHEMA_NAME, null);
    }
}
