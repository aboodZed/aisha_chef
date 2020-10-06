package com.webapp.aisha.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MainCategory implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("icon")
    @Expose
    private String icon_url;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIcon_url() {
        return icon_url;
    }
}
