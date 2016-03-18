package main.java.com.wuji.common;

public class DateUtils {

    public static final String DEFAULT_FORMAT = "yyyyMMddHHmmssSSS";

    public static final String DEFAULT_FORMAT_YYYYMMDD = "yyyyMMdd";

    public static final String DEFAULT_YEAR_MON_DAY = "yyyy-MM-dd HH:mm:ss";

    /**
     * @return 获取当前时间精确到秒
     */
    public static Integer getCurrentTime() {

        return (int) (System.currentTimeMillis() / 1000);
    }

    /**
     * @return 获取当前时间精确到毫秒
     */
    public static Long getCurrentTimeMills() {

        return System.currentTimeMillis();
    }

    /**
     * @return 获取当前时间精确到秒
     */
    public static String getCurrentTimeStr() {

        return Integer.toString((int) (System.currentTimeMillis() / 1000));
    }

    /**
     * @return 获取当前时间精确到毫秒
     */
    public static String getCurrentTimeMillsStr() {

        return Long.toString(System.currentTimeMillis());
    }

}
