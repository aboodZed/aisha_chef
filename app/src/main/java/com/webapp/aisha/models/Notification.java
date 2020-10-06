package com.webapp.aisha.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("click_action")
    @Expose
    private String click_action;

    @SerializedName("click_id")
    @Expose
    private int click_id;

    @SerializedName("is_read")
    @Expose
    private boolean is_read;

    @SerializedName("create_at")
    @Expose
    private long create_at;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getClick_action() {
        return click_action;
    }

    public int getClick_id() {
        return click_id;
    }

    public boolean isIs_read() {
        return is_read;
    }

    public long getCreate_at() {
        return create_at;
    }

    @Override
    public String toString() {
        return "NotificationData{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", click_action='" + click_action + '\'' +
                ", click_id=" + click_id +
                ", is_read=" + is_read +
                ", create_at=" + create_at +
                '}';
    }
}
