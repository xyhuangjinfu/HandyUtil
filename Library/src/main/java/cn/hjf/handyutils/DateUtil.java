package cn.hjf.handyutils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static final String PATTERN_YMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_YMD = "yyyy-MM-dd";
    public static final String PATTERN_HMS = "HH:mm:ss";

    /**
     * 把时间格式化显示
     *
     * @param dateTime     日期
     * @param inputPattern 输入的日期格式
     * @param outPattern   输出的日期格式
     * @return
     */
    public static String format(String dateTime, String inputPattern, String outPattern) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        try {
            Date date = inputFormat.parse(dateTime);
            SimpleDateFormat outputFormat = new SimpleDateFormat(outPattern);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTime;
    }

    /**
     * 把时间格式化显示
     *
     * @param timeInMills 毫秒时间
     * @param outPattern  输出格式
     * @return
     */
    public static String format(long timeInMills, String outPattern) {
        SimpleDateFormat format = new SimpleDateFormat(outPattern);
        return format.format(new Date(timeInMills));
    }

    /**
     * 把字符串日期转换为毫秒
     *
     * @param date
     * @param inputPattern
     * @return
     */
    public static long toMillis(String date, String inputPattern) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        try {
            return inputFormat.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
