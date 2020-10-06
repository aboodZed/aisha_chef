package com.webapp.aisha.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pagination {

    @SerializedName("total")
    @Expose
    private int total;

    @SerializedName("per_page")
    @Expose
    private int per_page;

    @SerializedName("next_page_url")
    @Expose
    private String next_page_url;

    @SerializedName("prev_page_url")
    @Expose
    private String prev_page_url;

    @SerializedName("current_page")
    @Expose
    private int current_page;

    @SerializedName("last_page")
    @Expose
    private int last_page;

    @SerializedName("from")
    @Expose
    private int from;

    @SerializedName("to")
    @Expose
    private int to;

    public int getTotal() {
        return total;
    }

    public int getPer_page() {
        return per_page;
    }

    public String getNext_page_url() {
        return next_page_url;
    }

    public String getPrev_page_url() {
        return prev_page_url;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public int getLast_page() {
        return last_page;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "total=" + total +
                ", per_page=" + per_page +
                ", next_page_url='" + next_page_url + '\'' +
                ", prev_page_url='" + prev_page_url + '\'' +
                ", current_page=" + current_page +
                ", last_page=" + last_page +
                ", from=" + from +
                ", to=" + to +
                '}';
    }
}
