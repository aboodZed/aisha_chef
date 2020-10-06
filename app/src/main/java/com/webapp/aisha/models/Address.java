package com.webapp.aisha.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Address implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("lat")
    @Expose
    private String lat;

    @SerializedName("lng")
    @Expose
    private String lng;

    public String getName() {
        return name;
    }

    public double getLat() {
        return Double.parseDouble(lat);
    }

    public double getLng() {
        return Double.parseDouble(lng);
    }

    @Override
    public String toString() {
        return "Address{" +
                "name='" + name + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                '}';
    }
}
