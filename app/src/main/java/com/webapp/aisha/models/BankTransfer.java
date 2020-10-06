package com.webapp.aisha.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankTransfer {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("amount")
    @Expose
    private double amount;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("full_name")
    @Expose
    private String full_name;

    @SerializedName("bank_name")
    @Expose
    private String bank_name;

    @SerializedName("account_number")
    @Expose
    private String account_number;

    @SerializedName("receipt_img")
    @Expose
    private String receipt_img_url;

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

    public String getFull_name() {
        return full_name;
    }

    public String getBank_name() {
        return bank_name;
    }

    public String getAccount_number() {
        return account_number;
    }

    public String getReceipt_img_url() {
        return receipt_img_url;
    }

    public long getCreated_at() {
        return created_at;
    }

    @Override
    public String toString() {
        return "BankTransfer{" +
                "id=" + id +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", full_name='" + full_name + '\'' +
                ", bank_name='" + bank_name + '\'' +
                ", account_number='" + account_number + '\'' +
                ", receipt_img_url='" + receipt_img_url + '\'' +
                ", created_at=" + created_at +
                '}';
    }
}
