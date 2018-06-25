package com.mengxin.img.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static String getTime(){
        Date date = new Date();
        return dateFormat.format(date);
    }
    public static String getTomorrowTime(){
        Date date = new Date();
        Long temp = date.getTime();
        temp = temp + 1000*60*60*24L;
        date.setTime(temp);
        return dateFormat.format(date);
    }
}
