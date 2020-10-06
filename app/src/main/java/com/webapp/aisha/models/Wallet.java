package com.webapp.aisha.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wallet {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("type")
    @Expose
    private OrderType type;

    @SerializedName("total")
    @Expose
    private double total;

    @SerializedName("agent_total")
    @Expose
    private double agent_total;

    @SerializedName("sys_commission")
    @Expose
    private double sys_commission;

    @SerializedName("status")
    @Expose
    private OrderType status;

    @SerializedName("payment_method")
    @Expose
    private OrderType payment_method;

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

    public double getAgent_total() {
        return agent_total;
    }

    public double getSys_commission() {
        return sys_commission;
    }

    public OrderType getStatus() {
        return status;
    }

    public OrderType getPayment_method() {
        return payment_method;
    }

    public long getCreated_at() {
        return created_at;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "id=" + id +
                ", type=" + type +
                ", total=" + total +
                ", agent_total=" + agent_total +
                ", sys_commission=" + sys_commission +
                ", status=" + status +
                ", payment_method=" + payment_method +
                ", created_at=" + created_at +
                '}';
    }
}
