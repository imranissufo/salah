package com.salah.util;

public class TimeUtils {

    public static int getHour(String time) {
        try {
            String[] timef = time.split(":");
            int hour = Integer.parseInt(timef[0]);
            int minute = Integer.parseInt(timef[1]);
            return hour;
        }catch (Exception e){
            return 0;
        }
    }

    public static String getFormatedDate(int year, int month, int day) {
        return addZero(day)+"/"+addZero(month)+"/"+year;
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
        try {
            String[] timef = time.split(":");
            int hour = Integer.parseInt(timef[0]);
            int minute = Integer.parseInt(timef[1]);
            return minute;
        }catch (Exception e){
            return 0;
        }
    }

}
