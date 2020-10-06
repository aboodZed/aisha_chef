package com.webapp.aisha.feture.splash;

import android.app.Activity;

import com.webapp.aisha.feture.main.MainActivity;
import com.webapp.aisha.feture.registration.RegistrationActivity;
import com.webapp.aisha.models.User;
import com.webapp.aisha.services.api.ApiShared;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.NavigateUtils;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.language.AppLanguageUtil;
import com.webapp.aisha.utils.listener.RequestListener;

public class SplashPresenter {

    private Activity activity;

    public SplashPresenter(Activity activity) {
        this.activity = activity;
    }

    public void splash() {
        ApiShared.getInstance().getAuthData().getProfile(activity, new RequestListener<User>() {
            @Override
            public void onSuccess(User user, String msg) {
                AppController.getInstance().getAppSettingsPreferences().setUser(user);
                setLanguage();
                checkLogin();
            }

            @Override
            public void onFail(String msg) {
                //ToolUtils.showLongToast(msg, activity);
                NavigateUtils.fromActivityToActivityWithPage(activity, RegistrationActivity.class
                        , true, AppContent.REGISTRATION_PAGE, AppContent.login);
            }
        });
    }

    private void setLanguage() {
        if (AppController.getInstance().getAppSettingsPreferences().isFirstRun()) {
            AppLanguageUtil.getInstance().setAppFirstRunLng();
        } else {
            AppLanguageUtil.getInstance().setAppLanguage(activity, AppController.getInstance()
                    .getAppSettingsPreferences().getAppLanguage());
        }
    }

    private void checkLogin() {
        if (AppController.getInstance().getAppSettingsPreferences().isLogin()) {
            NavigateUtils.fromActivityToActivityWithPage(activity, MainActivity.class
                    , true, AppContent.MAIN_PAGE, AppContent.home);
        } else {
            NavigateUtils.fromActivityToActivityWithPage(activity, RegistrationActivity.class
                    , true, AppContent.REGISTRATION_PAGE, AppContent.login);
        }
    }
}
