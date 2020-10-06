package com.webapp.aisha.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bank {

    @SerializedName("bank_name")
    @Expose
    private  String bank_name;

    @SerializedName("account_name")
    @Expose
    private  String account_name;

    @SerializedName("account_number")
    @Expose
    private  String account_number;

    @SerializedName("iban")
    @Expose
    private  String iban;

    public String getBank_name() {
        return bank_name;
    }

    public String getAccount_name() {
        return account_name;
    }

    public String getAccount_number() {
        return account_number;
    }

    public String getIban() {
        return iban;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "bank_name='" + bank_name + '\'' +
                ", account_name='" + account_name + '\'' +
                ", account_number='" + account_number + '\'' +
                ", iban='" + iban + '\'' +
                '}';
    }
}
