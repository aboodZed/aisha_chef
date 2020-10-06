package com.webapp.aisha.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderTotal implements Serializable {

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("value")
    @Expose
    private double value;

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "OrderTotal{" +
                "code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", value=" + value +
                '}';
    }
}

