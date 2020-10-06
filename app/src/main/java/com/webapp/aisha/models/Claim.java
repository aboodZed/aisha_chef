package com.webapp.aisha.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Claim {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("amount")
    @Expose
    private double amount;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("created_at")
    @Expose
    private long created_at;

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public long getCreated_at() {
        return created_at;
    }

    @Override
    public String toString() {
        return "Claim{" +
                "id=" + id +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", created_at=" + created_at +
                '}';
    }
}
