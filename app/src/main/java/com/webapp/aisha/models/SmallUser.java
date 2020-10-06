package com.webapp.aisha.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SmallUser implements Serializable {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("avatar")
    @Expose
    private String avatar_url;

    @SerializedName("country_code")
    @Expose
    private String country_code;

    @SerializedName("mobile")
    @Expose
    private String mobile;

    @SerializedName("full_mobile")
    @Expose
    private String full_mobile;

    @SerializedName("city")
    @Expose
    private City city;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getCountry_code() {
        return country_code;
    }

    public String getMobile() {
        return mobile;
    }

    public String getFull_mobile() {
        return full_mobile;
    }

    public City getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "SmallUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                ", country_code='" + country_code + '\'' +
                ", mobile='" + mobile + '\'' +
                ", full_mobile='" + full_mobile + '\'' +
                ", city=" + city +
                '}';
    }
}
