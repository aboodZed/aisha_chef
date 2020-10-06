package com.webapp.aisha.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Meal implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("image")
    @Expose
    private String image_url;

    @SerializedName("price")
    @Expose
    private double price;

    @SerializedName("duration")
    @Expose
    private int duration;

    @SerializedName("need_pre_order")
    @Expose
    private boolean need_pre_order;

    @SerializedName("pre_order_days")
    @Expose
    private int pre_order_days;

    @SerializedName("is_active")
    @Expose
    private boolean is_active;

    @SerializedName("images")
    @Expose
    private ArrayList<MealImage> mealImages;

   @SerializedName("category")
    @Expose
    private MainCategory mainCategory;

    @SerializedName("sizes")
    @Expose
    private ArrayList<MealFor> mealFors;

    @SerializedName("options")
    @Expose
    private ArrayList<MealExtra> mealExtras;


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage_url() {
        return image_url;
    }

    public double getPrice() {
        return price;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isNeed_pre_order() {
        return need_pre_order;
    }

    public int getPre_order_days() {
        return pre_order_days;
    }

    public boolean isIs_active() {
        return is_active;
    }

   public ArrayList<MealImage> getMealImages() {
        return mealImages;
    }

    public MainCategory getMainCategory() {
        return mainCategory;
    }

    public ArrayList<MealFor> getMealFors() {
        return mealFors;
    }

    public ArrayList<MealExtra> getMealExtras() {
        return mealExtras;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image_url='" + image_url + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", need_pre_order=" + need_pre_order +
                ", pre_order_days=" + pre_order_days +
                ", is_active=" + is_active +
                ", mealImages=" + mealImages +
                ", mainCategory=" + mainCategory +
                ", mealSizes=" + mealFors +
                ", mealOptions=" + mealExtras +
                '}';
    }

}
