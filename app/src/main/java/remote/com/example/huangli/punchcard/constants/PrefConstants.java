package remote.com.example.huangli.punchcard.constants;

/**
 * Created by wangjun on 16/3/30.
 */
public class PrefConstants {

    @Deprecated
    public static final class Network{
        //instead of Token.Token
        @Deprecated
        public static final String TOKEN = "token";

        //instead of UserInfo.UID
        @Deprecated
        public static final String uid = "userId";
    }

    public static final class Login {

    }

    public static final class Token {
        public static final String TOKEN = "token";
        public static final String CREATE_TIME = "token_create_time";//创建时间
        public static final String EXPIRE_TIME = "token_expire_time";//过期时间
    }

    public static final class UserInfo{
        public static final String PLATFORM_NAME = "platform_name";
        public static final String USER_NAME = "user_name";
        public static final String USER_ICON_URL = "user_icon_url";
        public static final String LEVEL= "level";
        public static final String COIN = "coin";
        public static final String GEO = "geo";
        public static final String UID = "userId";
    }

    public static final class Config {
        public static final String LAST_UPDATE_TIME = "config_last_update_time";
        public static final String H5_CONFIG = "config_h5";
        public static final String CATEHOTY_CONFIG = "config_category";
        public static final String RECHARGE_CONFIG = "config_recharge";
    }

    public static final class Language {
        public static final String APP = "language_app";

    }

}
