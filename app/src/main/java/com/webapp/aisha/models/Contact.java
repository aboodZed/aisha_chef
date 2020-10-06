package com.webapp.aisha.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Contact {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("from_app")
    @Expose
    private String from_app;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    public int getId() {
        return id;
    }

    public String getFrom_app() {
        return from_app;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", from_app='" + from_app + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }
}
