package com.webapp.aisha.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rating {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("rating")
    @Expose
    private double rating;

    @SerializedName("user")
    @Expose
    private SmallUser user;

    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("created_at")
    @Expose
    private long created_at;

    public int getId() {
        return id;
    }

    public double getRating() {
        return rating;
    }

    public SmallUser getUser() {
        return user;
    }

    public String getComment() {
        return comment;
    }

    public long getCreated_at() {
        return created_at;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", rating=" + rating +
                ", user=" + user +
                ", comment='" + comment + '\'' +
                ", created_at=" + created_at +
                '}';
    }
}
