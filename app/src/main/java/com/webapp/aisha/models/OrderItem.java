package com.webapp.aisha.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderItem implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("price")
    @Expose
    private double price;

    @SerializedName("quantity")
    @Expose
    private int quantity;

    @SerializedName("total")
    @Expose
    private double total;

    @SerializedName("sizes")
    @Expose
    private ArrayList<MealFor> mealFors;

    @SerializedName("options")
    @Expose
    private ArrayList<MealExtra> mealExtras;

    @SerializedName("meal")
    @Expose
    private Meal meal;

    @SerializedName("offer")
    @Expose
    private Offer offer;

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotal() {
        return total;
    }

    public ArrayList<MealFor> getMealFors() {
        return mealFors;
    }

    public ArrayList<MealExtra> getMealExtras() {
        return mealExtras;
    }

    public Offer getOffer() {
        return offer;
    }

    public Meal getMeal() {
        return meal;
    }

    public boolean isMeal() {
        if (meal != null) {
            return true;
        }
        return false;
    }

    public boolean isOffer() {
        if (offer != null) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", price=" + price +
                ", quantity=" + quantity +
                ", total=" + total +
                ", mealFors=" + mealFors +
                ", mealExtras=" + mealExtras +
                ", meal=" + meal +
                ", offer=" + offer +
                '}';
    }
}
