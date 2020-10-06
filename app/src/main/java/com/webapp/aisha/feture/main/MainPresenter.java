package com.webapp.aisha.feture.main;

import android.app.Activity;
import android.widget.Switch;

import com.webapp.aisha.services.api.ApiShared;
import com.webapp.aisha.models.User;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.listener.RequestListener;

class MainPresenter {

    private Activity activity;
    private NavigationView navigationView;

    public MainPresenter(Activity activity, NavigationView navigationView) {
        this.activity = activity;
        this.navigationView = navigationView;
    }

    public void updateStatus(Switch sOnline) {
        int s = 0;
        if (sOnline.isChecked()) {
            s = 1;
        }
        ApiShared.getInstance().getAuthData()
                .updateStatus(activity, s, new RequestListener<User>() {
            @Override
            public void onSuccess(User user, String msg) {
                AppController.getInstance().getAppSettingsPreferences().setUser(user);
                //ToolUtils.showLongToast(msg, activity);
            }

            @Override
            public void onFail(String msg) {
                if (sOnline.isChecked()) {
                    sOnline.setChecked(false);
                } else {
                    sOnline.setChecked(true);
                }
                ToolUtils.showLongToast(msg, activity);
            }
        });
    }
}
