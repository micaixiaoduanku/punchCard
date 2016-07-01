package remote.com.example.huangli.punchcard.db;

/**
 * Created by huangli on 16/6/29.
 */
public class DbKeyParseUtil {
    public static String[] parseKey(String keydata){
        return keydata.split(",");
    }
}
