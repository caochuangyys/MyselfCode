package com.nft.core.util;

import cn.hutool.core.date.LocalDateTimeUtil;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
public class DateUtils {

    /**
     * 根据生日计算年龄
     * @param birthday
     * @return
     */
    public static int getAgeByBirth(Date birthday) {
        int age = 0;
        if (birthday == null){
            return age;
        }
        try {
            Calendar now = Calendar.getInstance();
            // 当前时间
            now.setTime(new Date());

            Calendar birth = Calendar.getInstance();
            birth.setTime(birthday);

            //如果传入的时间，在当前时间的后面，返回0岁
            if (birth.after(now)) {
                age = 0;
            } else {
                age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
                    age += 1;
                }
            }
            return age;
        } catch (Exception e) {
            //兼容性更强,异常后返回数据
            return 0;
        }
    }

    /**
     * 格式化app列表时间  当天显示时分，一年内显示月日，一年外显示年月日
     * @param dateTime
     * @return
     */
    public static String formatAppListTime(LocalDateTime dateTime){

        if (dateTime == null){
            return "--";
        }
        LocalDateTime now = LocalDateTimeUtil.now();
        if (dateTime.isAfter(LocalDateTimeUtil.beginOfDay(now))){
            return LocalDateTimeUtil.format(dateTime, "HH:mm");
        }else if (dateTime.getYear() < now.getYear()){
            //不是本年
            return LocalDateTimeUtil.format(dateTime, "yyyy年MM月dd日");
        }else{
            return LocalDateTimeUtil.format(dateTime, "MM月dd日");
        }

    }

    /**
     * 判断是否是今天
     * @param dateTime
     * @return
     */
    public static boolean isToday(LocalDateTime dateTime){
        LocalDateTime now = LocalDateTimeUtil.now();

        return dateTime.isAfter(LocalDateTimeUtil.beginOfDay(now)) && dateTime.isBefore(LocalDateTimeUtil.endOfDay(now));


    }
}
