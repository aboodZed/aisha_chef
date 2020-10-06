package com.webapp.aisha.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderType implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("label")
    @Expose
    private String label;

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "OrderType{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
