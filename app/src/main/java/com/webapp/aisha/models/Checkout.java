package com.webapp.aisha.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Checkout extends Status {

    @SerializedName("checkout_id")
    @Expose
    private String checkout_id;

    public String getCheckout_id() {
        return checkout_id;
    }

    @Override
    public String toString() {
        return "Data{" +
                "checkout_id='" + checkout_id + '\'' +
                '}';
    }
}
