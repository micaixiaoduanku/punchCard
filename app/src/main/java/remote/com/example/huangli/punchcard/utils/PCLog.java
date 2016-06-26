package remote.com.example.huangli.punchcard.utils;

import android.util.Log;

/**
 * Created by huangli on 16/6/3.
 */
public class PCLog {
    private static boolean DEBUG = true;
    private static final String TAG = "yb";

    public static void i(Object o) {
        if (DEBUG) {
            Log.i(TAG, String.valueOf(o));
        }
    }

    public static void d(Object o) {
        if (DEBUG) {
            Log.d(TAG, String.valueOf(o));
        }
    }

    public static void e(Object o) {
        if (DEBUG) {
            Log.e(TAG, String.valueOf(o));
        }
    }

    public static void e(Object o,Throwable t) {
        if (DEBUG) {
            t.printStackTrace();
            Log.e(TAG, String.valueOf(o) + " error:" + t.getMessage());
        }
    }

    public static void w(Object o) {
        if (DEBUG) {
            Log.w(TAG, String.valueOf(o));
        }
    }

    public static void w(Object o,Throwable t) {
        if (DEBUG) {
            t.printStackTrace();
            Log.w(TAG, String.valueOf(o) + " error:" + t.getMessage());
        }
    }
    public static void v(Object o) {
        if (DEBUG) {
            Log.v(TAG, String.valueOf(o));
        }
    }

    public static void d(Object... os) {
        if (DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Object o: os) {
                stringBuilder.append(String.valueOf(o)).append(" ");
            }
            Log.d(TAG, stringBuilder.toString());
        }
    }
}
