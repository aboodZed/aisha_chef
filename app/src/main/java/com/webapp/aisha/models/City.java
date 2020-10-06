package com.webapp.aisha.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class City implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("times")
    @Expose
    private ArrayList<DayTime> times;

    @SerializedName("price")
    @Expose
    private double price;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<DayTime> getTimes() {
        return times;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", times=" + times +
                ", price=" + price +
                '}';
    }
}
