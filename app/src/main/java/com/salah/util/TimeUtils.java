package com.salah.util;

public class TimeUtils {

    public static int getHour(String time) {
        String[] timef = time.split(":");
        int hour = Integer.parseInt(timef[0]);
        int minute = Integer.parseInt(timef[1]);
        return hour;
    }

    public static int getMinute(String time) {
        String[] timef = time.split(":");
        int hour = Integer.parseInt(timef[0]);
        int minute = Integer.parseInt(timef[1]);
        return minute;
    }

}
