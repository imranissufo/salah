package com.salah.model;

import android.graphics.drawable.GradientDrawable;

public class Location {
    private int image;
    private String title;
    private String desc;
    private GradientDrawable gradient;

    public Location(int image, String title, String desc) {
        this.image = image;
        this.title = title;
        this.desc = desc;
    }

    public Location(GradientDrawable gradient, int image, String title) {
        this.title = title;
        this.image = image;
        this.gradient = gradient;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public GradientDrawable getGradient() {
        return gradient;
    }

    public void setGradient(GradientDrawable gradient) {
        this.gradient = gradient;
    }

}
