package com.salah.model;

public class Timings {
    private String code;
    private String month;
    private String day;
    private String suhur;
    private String fajr;
    private String sunrise;
    private String zawal;
    private String zuhr;
    private String assr;
    private String sunset;
    private String magrib;
    private String isha;
    private String location;

    public Timings() {
    }

    public Timings(String code, String month, String day, String suhur, String fajr, String sunrise, String zawal, String zuhr, String assr, String sunset, String magrib, String isha, String location) {
        this.code = code;
        this.month = month;
        this.day = day;
        this.suhur = suhur;
        this.fajr = fajr;
        this.sunrise = sunrise;
        this.zawal = zawal;
        this.zuhr = zuhr;
        this.assr = assr;
        this.sunset = sunset;
        this.magrib = magrib;
        this.isha = isha;
        this.location = location;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getSuhur() {
        return suhur;
    }

    public void setSuhur(String suhur) {
        this.suhur = suhur;
    }

    public String getFajr() {
        return fajr;
    }

    public void setFajr(String fajr) {
        this.fajr = fajr;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getZawal() {
        return zawal;
    }

    public void setZawal(String zawal) {
        this.zawal = zawal;
    }

    public String getZuhr() {
        return zuhr;
    }

    public void setZuhr(String zuhr) {
        this.zuhr = zuhr;
    }

    public String getAssr() {
        return assr;
    }

    public void setAssr(String assr) {
        this.assr = assr;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getMagrib() {
        return magrib;
    }

    public void setMagrib(String magrib) {
        this.magrib = magrib;
    }

    public String getIsha() {
        return isha;
    }

    public void setIsha(String isha) {
        this.isha = isha;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
