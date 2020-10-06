package com.webapp.aisha.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.webapp.aisha.models.Subscribe;
import com.webapp.aisha.models.User;

public class AppSettingsPreferences {

    public final static String KEY_APP_LANGUAGE = "app_language";
    public final static String KEY_FIRST_RUN = "first_run";
    public final static String KEY_USER = "user";
    public final static String KEY_LOGIN = "login";
    public final static String KEY_ACCESS_TOKEN = "access_token";
    private final static String KEY_RESERVATION = "reservation";
    private final static String KEY_SUBSCRIBE = "subscribe";

    private final static String TRACKING_ORDER = "tracking";

    private static final String PREF_NAME = "AppSettingsPreferences";
    private int PRIVATE_MODE = 0;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private Context context;

    public AppSettingsPreferences(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstRun() {
        editor.putBoolean(KEY_FIRST_RUN, false);
        editor.apply();
    }

    public boolean isFirstRun() {
        return pref.getBoolean(KEY_FIRST_RUN, true);
    }

    public void setAppLanguage(String appLanguage) {
        editor.putString(KEY_APP_LANGUAGE, appLanguage);
        editor.apply();
    }

    public String getAppLanguage() {
        return pref.getString(KEY_APP_LANGUAGE, "en");
    }

    public void setKeyAccessToken(String key) {
        editor.putString(KEY_ACCESS_TOKEN, key);
        editor.apply();
    }

    public String getKeyAccessToken() {
        return "Bearer " + pref.getString(KEY_ACCESS_TOKEN, "");
    }

    public void setUser(User user) {
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(KEY_USER, json);
        editor.apply();
    }

    public User getUser() {
        Gson gson = new Gson();
        String json = pref.getString(KEY_USER, "");
        return gson.fromJson(json, User.class);
    }

    public void setLogin(boolean b) {
        editor.putBoolean(KEY_LOGIN, b);
        editor.apply();
    }

    public boolean isLogin() {
        return pref.getBoolean(KEY_LOGIN, false);
    }

    public void setReservation(int i) {
        editor.putInt(KEY_RESERVATION, i);
        editor.apply();
    }

    public int getReservation() {
        return pref.getInt(KEY_RESERVATION, 1);
    }

    public void setSubscribe(Subscribe subscribe) {
        Gson gson = new Gson();
        String json = gson.toJson(subscribe);
        editor.putString(KEY_SUBSCRIBE, json);
        editor.apply();
    }

    public Subscribe getSubscribe() {
        Gson gson = new Gson();
        String json = pref.getString(KEY_SUBSCRIBE, "");
        return gson.fromJson(json, Subscribe.class);
    }

    public void clean() {
        editor.clear();
        editor.apply();
    }
}
