package remote.com.example.huangli.punchcard.utils;

/**
 * @author toby.du
 * @date 2014年11月8日
 */
public class PlatformUtils {

	public static boolean isOverVersion(int code){
		return VERSION.SDK_INT >= code;
	}
	
	public static boolean isLowVersion(int code){
        return VERSION.SDK_INT < code;
    }
	
	public static class VERSION {
		public static final int SDK_INT = android.os.Build.VERSION.SDK_INT;
	}

	/**
	 * Enumeration of the currently known SDK version codes. These are the
	 * values that can be found in {@link VERSION#SDK}. Version numbers
	 * increment monotonically with each official platform release.
	 */
	public static class VERSION_CODES {
		public static final int CUR_DEVELOPMENT = 10000;

		public static final int BASE = 1;

		public static final int BASE_1_1 = 2;

		public static final int CUPCAKE = 3;

		public static final int DONUT = 4;

		public static final int ECLAIR = 5;

		public static final int ECLAIR_0_1 = 6;

		public static final int ECLAIR_MR1 = 7;

		public static final int FROYO = 8;

		public static final int GINGERBREAD = 9;

		public static final int GINGERBREAD_MR1 = 10;

		public static final int HONEYCOMB = 11;

		public static final int HONEYCOMB_MR1 = 12;

		public static final int HONEYCOMB_MR2 = 13;

		public static final int ICE_CREAM_SANDWICH = 14;

		public static final int ICE_CREAM_SANDWICH_MR1 = 15;

		public static final int JELLY_BEAN = 16;

		public static final int JELLY_BEAN_MR1 = 17;

		public static final int JELLY_BEAN_MR2 = 18;

		public static final int KITKAT = 19;
		
		public static final int KITKAT_WATCH = 20;

        public static final int LOLLIPOP = 21;
	}
}
