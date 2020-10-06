package com.webapp.aisha.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("type")
    @Expose
    private OrderType type;

    @SerializedName("total")
    @Expose
    private double total;

    @SerializedName("quantity")
    @Expose
    private int quantity;

    @SerializedName("status")
    @Expose
    private OrderType status;

    @SerializedName("payment_method")
    @Expose
    private OrderType payment_method;

    @SerializedName("time_day")
    @Expose
    private String time_day;

    @SerializedName("time_hour")
    @Expose
    private String time_hour;

    @SerializedName("user")
    @Expose
    private SmallUser user;

    @SerializedName("address")
    @Expose
    private Address address;

    @SerializedName("meals")
    @Expose
    private ArrayList<OrderItem> meals;

    @SerializedName("offer")
    @Expose
    private OrderItem offer;

    @SerializedName("totals")
    @Expose
    private ArrayList<OrderTotal> totals;

    @SerializedName("history")
    @Expose
    private ArrayList<OrderHistory> histories;

    @SerializedName("created_at")
    @Expose
    private long created_at;

    public int getId() {
        return id;
    }

    public OrderType getType() {
        return type;
    }

    public double getTotal() {
        return total;
    }

    public int getQuantity() {
        return quantity;
    }

    public OrderType getStatus() {
        return status;
    }

    public OrderType getPayment_method() {
        return payment_method;
    }

    public String getTime_day() {
        return time_day;
    }

    public String getTime_hour() {
        return time_hour;
    }

    public SmallUser getUser() {
        return user;
    }

    public Address getAddress() {
        return address;
    }

    public ArrayList<OrderItem> getMeals() {
        return meals;
    }

    public OrderItem getOffer() {
        return offer;
    }

    public ArrayList<OrderTotal> getTotals() {
        return totals;
    }

    public ArrayList<OrderHistory> getHistories() {
        return histories;
    }

    public long getCreated_at() {
        return created_at;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", type=" + type +
                ", total=" + total +
                ", quantity=" + quantity +
                ", status=" + status +
                ", payment_method=" + payment_method +
                ", time_day='" + time_day + '\'' +
                ", time_hour='" + time_hour + '\'' +
                ", user=" + user +
                ", address=" + address +
                ", meals=" + meals +
                ", offer=" + offer +
                ", totals=" + totals +
                ", histories=" + histories +
                ", created_at=" + created_at +
                '}';
    }
}