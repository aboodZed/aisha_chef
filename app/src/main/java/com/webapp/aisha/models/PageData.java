package com.webapp.aisha.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PageData {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("page_code")
    @Expose
    private String page_code;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("updated_at")
    @Expose
    private long updated_at;

    public int getId() {
        return id;
    }

    public String getPage_code() {
        return page_code;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public long getUpdated_at() {
        return updated_at;
    }
}
