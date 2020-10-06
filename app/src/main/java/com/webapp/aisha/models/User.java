package com.webapp.aisha.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("names")
    @Expose
    private ArrayList<NameInLanguage> names;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("avatar")
    @Expose
    private String avatar_url;

    @SerializedName("commercial_certification")
    @Expose
    private String commercial_certification_url;

    @SerializedName("proof_profile_image")
    @Expose
    private String proof_profile_image_url;

    @SerializedName("country_code")
    @Expose
    private String country_code;

    @SerializedName("mobile")
    @Expose
    private String mobile;

    @SerializedName("full_mobile")
    @Expose
    private String full_mobile;

    @SerializedName("lat")
    @Expose
    private double lat;

    @SerializedName("lng")
    @Expose
    private double lng;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("city")
    @Expose
    private City city;

    @SerializedName("working_days")
    @Expose
    private ArrayList<String> working_days;

    @SerializedName("current_balance")
    @Expose
    private double current_balance;

    @SerializedName("rating")
    @Expose
    private double rating;

    @SerializedName("ratings_count")
    @Expose
    private int ratings_count;

    @SerializedName("language")
    @Expose
    private String language;

    @SerializedName("is_active")
    @Expose
    private boolean is_active;

    @SerializedName("is_online")
    @Expose
    private boolean is_online;

    @SerializedName("is_skip_credit_limit")
    @Expose
    private boolean is_skip_credit_limit;

    @SerializedName("iban")
    @Expose
    private String iban;

    @SerializedName("bank_names")
    @Expose
    private ArrayList<NameInLanguage> bank_names;

    @SerializedName("cities_can_delivery")
    @Expose
    private ArrayList<City> cities_can_delivery;

    @SerializedName("main_categories")
    @Expose
    private ArrayList<MainCategory> main_categories;

    @SerializedName("sub_categories")
    @Expose
    private ArrayList<MainCategory> sub_categories;

    @SerializedName("package")
    @Expose
    private PackageUser packageUser;

    @SerializedName("token")
    @Expose
    private String token;

    private String password;

    public User(ArrayList<NameInLanguage> names, String email, String country_code
            , String mobile, City city, String password) {
        this.names = names;
        this.email = email;
        this.country_code = country_code;
        this.mobile = mobile;
        this.city = city;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public ArrayList<NameInLanguage> getNames() {
        return names;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getCommercial_certification_url() {
        return commercial_certification_url;
    }

    public String getProof_profile_image_url() {
        return proof_profile_image_url;
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

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getAddress() {
        return address;
    }

    public City getCity() {
        return city;
    }

    public ArrayList<String> getWorking_days() {
        return working_days;
    }

    public double getCurrent_balance() {
        return current_balance;
    }

    public double getRating() {
        return rating;
    }

    public int getRatings_count() {
        return ratings_count;
    }

    public String getLanguage() {
        return language;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public boolean isIs_online() {
        return is_online;
    }

    public String getIban() {
        return iban;
    }

    public ArrayList<NameInLanguage> getBank_names() {
        return bank_names;
    }

    public ArrayList<City> getCities_can_delivery() {
        return cities_can_delivery;
    }

    public ArrayList<MainCategory> getMain_categories() {
        return main_categories;
    }

    public ArrayList<MainCategory> getSub_categories() {
        return sub_categories;
    }

    public PackageUser getPackageUser() {
        return packageUser;
    }

    public String getToken() {
        return token;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", names=" + names +
                ", email='" + email + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                ", commercial_certification_url='" + commercial_certification_url + '\'' +
                ", proof_profile_image_url='" + proof_profile_image_url + '\'' +
                ", country_code='" + country_code + '\'' +
                ", mobile='" + mobile + '\'' +
                ", full_mobile='" + full_mobile + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", address='" + address + '\'' +
                ", city=" + city +
                ", dayTimes=" + working_days +
                ", current_balance=" + current_balance +
                ", rating=" + rating +
                ", ratings_count=" + ratings_count +
                ", language='" + language + '\'' +
                ", is_active=" + is_active +
                ", is_online=" + is_online +
                ", iban=" + iban +
                ", bank_names=" + bank_names +
                ", cities_can_delivery=" + cities_can_delivery +
                ", main_categories=" + main_categories +
                ", sub_categories=" + sub_categories +
                ", packageUser=" + packageUser +
                ", token='" + token + '\'' +
                '}';
    }
}
