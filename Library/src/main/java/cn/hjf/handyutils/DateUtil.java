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
     * @param InputPattern 输入的日期格式
     * @param outPattern   输出的日期格式
     * @return
     */
    public static String format(String dateTime, String InputPattern, String outPattern) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(InputPattern);
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

}
