package com.webapp.aisha.utils;

import android.app.Application;

import com.webapp.aisha.services.api.ApiClient;
import com.webapp.aisha.services.api.ApiInterface;

public class AppController extends Application {

    private static AppController mInstance;

    private AppSettingsPreferences appSettingsPreferences;
    private ApiInterface api;

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        appSettingsPreferences = new AppSettingsPreferences(this);
        api = ApiClient.getRetrofit().create(ApiInterface.class);
    }

    public AppSettingsPreferences getAppSettingsPreferences() {
        return appSettingsPreferences;
    }

    public ApiInterface getApi() {
        return api;
    }
}
