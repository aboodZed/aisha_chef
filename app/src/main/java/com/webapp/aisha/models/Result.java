package com.webapp.aisha.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result<T> {

    @SerializedName("status")
    @Expose
    private Status status;

    @SerializedName("data")
    @Expose
    private T data;

    @SerializedName("pagination")
    @Expose
    private Pagination pagination;

    public Status getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public Pagination getPagination() {
        return pagination;
    }
}
