package com.webapp.aisha.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MealExtra implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String title;

    @SerializedName("name_in_arabic")
    @Expose
    private String title_in_arabic;

    @SerializedName("price")
    @Expose
    private double price;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTitle_in_arabic() {
        return title_in_arabic;
    }

    public double getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitle_in_arabic(String title_in_arabic) {
        this.title_in_arabic = title_in_arabic;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "MealExtra{" +
                "title='" + title + '\'' +
                ", title_in_arabic='" + title_in_arabic + '\'' +
                ", price=" + price +
                '}';
    }
}
