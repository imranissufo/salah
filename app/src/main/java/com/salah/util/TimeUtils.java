package com.salah.util;

public class TimeUtils {

    public static int getHour(String time) {
        String[] timef = time.split(":");
        int hour = Integer.parseInt(timef[0]);
        int minute = Integer.parseInt(timef[1]);
        return hour;
    }

    public static String getFormatedTime(int hour, int minute) {
        return addZero(hour)+":"+addZero(minute);
    }

    public static String addZero(int number) {
        if(number<10){
            return "0"+number;
        }
        return ""+number;
    }

    public static int getMinute(String time) {
        String[] timef = time.split(":");
        int hour = Integer.parseInt(timef[0]);
        int minute = Integer.parseInt(timef[1]);
        return minute;
    }

}
