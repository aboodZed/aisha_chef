package com.webapp.aisha.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderHistory implements Serializable {

    @SerializedName("status")
    @Expose
    private OrderType status;

    @SerializedName("create_at")
    @Expose
    private long create_at;

    public OrderType getStatus() {
        return status;
    }

    public long getCreate_at() {
        return create_at;
    }

    @Override
    public String toString() {
        return "OrderHistory{" +
                "status=" + status +
                ", create_at=" + create_at +
                '}';
    }
}
