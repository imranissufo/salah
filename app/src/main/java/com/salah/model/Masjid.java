package com.salah.model;

import java.io.Serializable;

public class Masjid implements Serializable {

    private String id;
    private String code;
    private String name;
    private String fajr;
    private String zuhr;
    private String jumma;
    private String assr;
    private String magrib;
    private String isha;
    private String location;
    private String annc;
    private String anncTime;

    public Masjid() {
    }

    public Masjid(String code, String name, String fajr, String zuhr, String jumma, String assr, String magrib, String isha, String location, String annc, String anncTime) {
        this.code = code;
        this.name = name;
        this.fajr = fajr;
        this.zuhr = zuhr;
        this.jumma = jumma;
        this.assr = assr;
        this.magrib = magrib;
        this.isha = isha;
        this.location = location;
        this.annc = annc;
        this.anncTime = anncTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAnnc() {
        return annc;
    }

    public void setAnnc(String annc) {
        this.annc = annc;
    }

    public String getAnncTime() {
        return anncTime;
    }

    public void setAnncTime(String anncTime) {
        this.anncTime = anncTime;
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
