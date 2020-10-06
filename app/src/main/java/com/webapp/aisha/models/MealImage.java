package com.webapp.aisha.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MealImage implements Serializable {
    @SerializedName("full")
    @Expose
    private String full;

    @SerializedName("large")
    @Expose
    private String large;

    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;

    public String getFull() {
        return full;
    }

    public String getLarge() {
        return large;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
