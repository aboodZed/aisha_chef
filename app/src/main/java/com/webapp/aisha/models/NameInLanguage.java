package com.webapp.aisha.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NameInLanguage implements Serializable {

    @SerializedName("language_code")
    @Expose
    private String language_code;

    @SerializedName("name")
    @Expose
    private String name;

    public NameInLanguage(String language_code, String name) {
        this.language_code = language_code;
        this.name = name;
    }

    public String getLanguage_code() {
        return language_code;
    }

    public String getName() {
        return name;
    }
}
