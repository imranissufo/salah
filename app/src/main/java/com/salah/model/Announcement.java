package com.salah.model;

public class Announcement {
    private String date;
    private String start;
    private String end;
    private String prayer;
    private String time;
    private String description;
    private String masjid;

    public Announcement(String date, String masjid) {
        this.date = date;
        this.masjid = masjid;
    }

    public Announcement(String date, String start, String end, String prayer, String time, String description,String masjid) {
        this.date = date;
        this.start = start;
        this.end = end;
        this.prayer = prayer;
        this.time = time;
        this.description = description;
        this.masjid = masjid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getPrayer() {
        return prayer;
    }

    public void setPrayer(String prayer) {
        this.prayer = prayer;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMasjid() {
        return masjid;
    }

    public void setMasjid(String masjid) {
        this.masjid = masjid;
    }
}
