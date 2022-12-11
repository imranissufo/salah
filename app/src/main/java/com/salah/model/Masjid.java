package com.salah.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
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

    private String anncFajrDate ="";
    private String anncFajrTime="";
    private String anncAssrDate="";
    private String anncAssrTime="";
    private String anncIshaDate="";
    private String anncIshaTime="";

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

    @Exclude
    public void setEmpty() throws IllegalAccessException {
        Field[] fields = Masjid.class.getDeclaredFields();
        System.out.printf("%d fields:%n", fields.length);
        for (Field field : fields) {
            if(field.get(this)==null){
               field.set(this, "");
            }
            System.out.printf("%s %s %s%n",
                    Modifier.toString(field.getModifiers()),
                    field.getType().getSimpleName(),
                    field.getName()
            );
        }
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("code", code);
        result.put("name", name);
        result.put("fajr", fajr);
        result.put("zuhr", zuhr);
        result.put("jumma", jumma);
        result.put("assr", assr);
        result.put("magrib", magrib);
        result.put("isha", isha);
        result.put("location", location);
        result.put("annc", annc);
        result.put("anncTime", anncTime);
        result.put("anncFajrDate", anncFajrDate);
        result.put("anncFajrTime", anncFajrTime);
        result.put("anncAssrDate", anncAssrDate);
        result.put("anncAssrTime", anncAssrTime);
        result.put("anncIshaDate", anncIshaDate);
        result.put("anncIshaTime", anncIshaTime);

        return result;
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

    public String getAnncFajrDate() {
        return anncFajrDate;
    }

    public void setAnncFajrDate(String anncFajrDate) {
        this.anncFajrDate = anncFajrDate;
    }

    public String getAnncFajrTime() {
        return anncFajrTime;
    }

    public void setAnncFajrTime(String anncFajrTime) {
        this.anncFajrTime = anncFajrTime;
    }

    public String getAnncAssrDate() {
        return anncAssrDate;
    }

    public void setAnncAssrDate(String anncAssrDate) {
        this.anncAssrDate = anncAssrDate;
    }

    public String getAnncAssrTime() {
        return anncAssrTime;
    }

    public void setAnncAssrTime(String anncAssrTime) {
        this.anncAssrTime = anncAssrTime;
    }

    public String getAnncIshaDate() {
        return anncIshaDate;
    }

    public void setAnncIshaDate(String anncIshaDate) {
        this.anncIshaDate = anncIshaDate;
    }

    public String getAnncIshaTime() {
        return anncIshaTime;
    }

    public void setAnncIshaTime(String anncIshaTime) {
        this.anncIshaTime = anncIshaTime;
    }

}
