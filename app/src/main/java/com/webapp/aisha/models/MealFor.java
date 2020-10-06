package com.webapp.aisha.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MealFor implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("people_number")
    @Expose
    private String people_number;

    @SerializedName("price")
    @Expose
    private double price;

    public int getId() {
        return id;
    }

    public String getPeople_number() {
        return people_number;
    }

    public void setPeople_number(String people_number) {
        this.people_number = people_number;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "MealFor{" +
                "number=" + people_number +
                ", price=" + price +
                '}';
    }
}
