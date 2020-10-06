package com.webapp.aisha.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Offer implements Serializable {

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
    private String img_url;

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

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("agents")
    @Expose
    private User agents;

    public String getImg_url() {
        return img_url;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public User getAgents() {
        return agents;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", details='" + description + '\'' +
                ", img_url=" + img_url +
                ", price='" + price + '\'' +
                ", duration=" + duration +
                ", need_pre_order=" + need_pre_order +
                ", pre_order_days=" + pre_order_days +
                ", is_active=" + is_active +
                ", status='" + status + '\'' +
                ", agents=" + agents +
                '}';
    }
}
