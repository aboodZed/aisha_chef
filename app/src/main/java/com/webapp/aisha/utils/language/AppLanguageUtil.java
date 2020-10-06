package com.webapp.aisha.utils.language;

import android.content.Context;
import android.content.res.Configuration;

import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.AppSettingsPreferences;

import java.util.Locale;

public class AppLanguageUtil {

    public static final String AR = "ar";
    public static final String EN = "en";

    private static AppLanguageUtil instance = null;
    private AppSettingsPreferences appSettingsPreferences;

    public static synchronized AppLanguageUtil getInstance() {
        if (instance == null) {
            instance = new AppLanguageUtil();
        }
        return instance;
    }

    public AppLanguageUtil() {
        appSettingsPreferences = new AppSettingsPreferences(getContext());
    }

    public void setAppFirstRunLng() {
        switch (Locale.getDefault().getLanguage()) {
            case AR:
                setAppLanguage(getContext(), AR);
                break;
            case EN:
            default:
                setAppLanguage(getContext(), EN);
                break;
        }
        appSettingsPreferences.setFirstRun();
    }

    private static void setLocale(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
    }

    public void setAppLanguage(Context context, String newLanguage) {
        setLocale(context, newLanguage);
        appSettingsPreferences.setAppLanguage(newLanguage);
    }

    private AppController getContext() {
        return AppController.getInstance();
    }
}
