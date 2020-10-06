package com.webapp.aisha.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class DayTime implements Serializable {

    @SerializedName("day")
    @Expose
    private String day;

    @SerializedName("hours")
    @Expose
    private ArrayList<String> hours;

    public DayTime(String day, ArrayList<String> hours) {
        this.day = day;
        this.hours = hours;
    }

    public String getDay() {
        return day;
    }

    public ArrayList<String> getHours() {
        return hours;
    }

    @Override
    public String toString() {
        return "DayTime{" +
                "day='" + day + '\'' +
                ", hours=" + hours +
                '}';
    }
}
