package fitness_equipment.test.com.fitness_equipment.utils;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 描述：时间处理器
 * 创建作者：黎丝军
 * 创建时间：2016/10/15 15:39
 */

public class TimeUtil {

    /**
     * 获取当前时间
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String getCurrentTime(String format){
        return DateFormat.format(format,System.currentTimeMillis()).toString();
    }

    /**
     * 获取当前时间,返回时间格式为yyyy-MM-dd
     * @return 时间字符串
     */
    public static String getCurrentTime(){
        return getCurrentTime("yyyy-MM-dd");
    }
    public static String getCurrentTimeYear(){
        return getCurrentTime("yyyy");
    }

    /**
     * 获取当前时间,返回时间格式为MM-dd
     * @return 时间字符串
     */
    public static String getMountCurrentTime(String month,String day){
        return getCurrentTime("MM"+month+"dd"+day);
    }

    /**
     * 获取当前全时间，返回格式为yyyy-MM-dd HH:mm:ss
     * @return 全时间
     */
    public static String getCurrentAllTime() {
        return getCurrentTime("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取时间，根据时间制定时间格式
     * @param dateStr 时间字符串
     * @param originTimeFormat 原始时间格式
     * @param timeFormat 时间格式
     * @return 现在的时间格式
     */
    public static String getTime(String dateStr,String originTimeFormat,String timeFormat) {
        SimpleDateFormat dateFormatSimple = new SimpleDateFormat(originTimeFormat);
        try {
            final Date date = dateFormatSimple.parse(dateStr);
            dateFormatSimple = new SimpleDateFormat(timeFormat);
            return dateFormatSimple.format(date);
        } catch (Exception e) {
        }
        return getCurrentTime(timeFormat);
    }

    /**
     * 获取时间，根据时间制定时间格式
     * @param dateStr 时间字符串
     * @param timeFormat 时间格式
     * @return 现在的时间格式 "date" -> "2016-12-14 09:58:57.0"
     */
    public static String getTime(String dateStr,String timeFormat) {
        return getTime(dateStr,"yyyy-MM-dd HH:mm:ss",timeFormat);
    }


    /**
     * 算出设定的闹钟是今天的还是明天的
     * */

    public    static  int  ToPanduan(String Sleep ){


        int  day =0;
        String [] sleepArry =Sleep.split(":");

        String sleepA =sleepArry[0];
        String sleepB =sleepArry[1];
        System.out.println("用户输入的时间小时"+sleepA);
        System.out.println("用户输入的时间分钟"+sleepB);

        Date date= new Date();
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("dd HH:mm");
        String  NowTime =simpleDateFormat.format(date);

        System.out.println(NowTime);


        String NowTimeA [] = NowTime.split(" ");
        // 当前系统的几号
        day= Integer.parseInt( NowTimeA[0]);
        System.out.println("几号"+day);

        String NowTimeH [] =  NowTimeA [1].split(":");

        // 当前系统的小时
        String nowTimeHH = NowTimeH[0];
        String nowTimeMM = NowTimeH[1];
        System.out.println(nowTimeHH);
        System.out.println(nowTimeMM);

        if (Integer.parseInt(nowTimeHH)> Integer.parseInt(sleepA ) ) {

            return day+1;
        }else if (Integer.parseInt(nowTimeHH)== Integer.parseInt(sleepA ) ) {

            if (Integer.parseInt(nowTimeMM) > Integer.parseInt(sleepB )){

                return day+1;
            }

        }else{

            return	day;
        }
        return day;
    }



}
