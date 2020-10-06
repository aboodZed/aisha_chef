package com.webapp.aisha.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Config {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("key")
    @Expose
    private String key;

    @SerializedName("value")
    @Expose
    private Object value;

    public int getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Config{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
