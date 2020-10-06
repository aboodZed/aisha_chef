package com.webapp.aisha.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pay {

    @SerializedName("transaction_reference")
    @Expose
    private String transaction_reference;

    @SerializedName("checkout_id")
    @Expose
    private String checkout_id;

    public String getTransaction_reference() {
        return transaction_reference;
    }

    public String getCheckout_id() {
        return checkout_id;
    }
}
