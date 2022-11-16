package com.salah.model;

public class Masjid {

    private String code;
    private String name;
    private String fajr;
    private String zuhr;
    private String jumma;
    private String assr;
    private String magrib;
    private String isha;

    public Masjid() {
    }

    public Masjid(String code, String name, String fajr, String zuhr, String jumma, String assr, String isha) {
        this.code = code;
        this.name = name;
        this.fajr = fajr;
        this.zuhr = zuhr;
        this.jumma = jumma;
        this.assr = assr;
        this.magrib = magrib;
        this.isha = isha;
    }

    public Masjid(String code, String name, String fajr, String zuhr, String jumma, String assr, String magrib, String isha) {
        this.code = code;
        this.name = name;
        this.fajr = fajr;
        this.zuhr = zuhr;
        this.jumma = jumma;
        this.assr = assr;
        this.magrib = magrib;
        this.isha = isha;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFajr() {
        return fajr;
    }

    public void setFajr(String fajr) {
        this.fajr = fajr;
    }

    public String getZuhr() {
        return zuhr;
    }

    public void setZuhr(String zuhr) {
        this.zuhr = zuhr;
    }

    public String getJumma() {
        return jumma;
    }

    public void setJumma(String jumma) {
        this.jumma = jumma;
    }

    public String getAssr() {
        return assr;
    }

    public void setAssr(String assr) {
        this.assr = assr;
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
}
