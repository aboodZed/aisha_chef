package com.webapp.aisha.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PackageUser implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("total")
    @Expose
    private int total;

    @SerializedName("duration")
    @Expose
    private int duration;

    @SerializedName("created_at")
    @Expose
    private long created_at;

    @SerializedName("expire_at")
    @Expose
    private long expire_at;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getTotal() {
        return total;
    }

    public int getDuration() {
        return duration;
    }

    public long getCreated_at() {
        return created_at;
    }

    public long getExpire_at() {
        return expire_at;
    }
}
