package com.webapp.aisha.feture.registration.login;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.webapp.aisha.R;
import com.webapp.aisha.services.api.ApiShared;
import com.webapp.aisha.models.User;
import com.webapp.aisha.services.firebase.FirebaseShared;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.listener.DialogView;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.HashMap;
import java.util.Map;

public class LoginPresenter {

    private Activity activity;
    private NavigationView view;
    private DialogView dialogView;

    public LoginPresenter(Activity activity, NavigationView view, DialogView dialogView) {
        this.activity = activity;
        this.view = view;
        this.dialogView = dialogView;
    }

    public void validateInput(EditText phone, EditText password) {

        String sPhone = phone.getText().toString().trim();
        String sPassword = password.getText().toString().trim();

        if (TextUtils.isEmpty(sPhone)) {
            phone.setError(activity.getString(R.string.required_field));
            return;
        }

        if (sPhone.length() != 9) {
            phone.setError(activity.getString(R.string.nine_digits));
            return;
        }

        if (TextUtils.isEmpty(sPassword)) {
            password.setError(activity.getString(R.string.required_field));
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("country_code", AppContent.COUNTRY_CODE);
        map.put("mobile", sPhone);
        map.put("password", sPassword);

        getFCMToken(map);
    }

    private void getFCMToken(Map<String, String> map) {
        dialogView.showDialog(activity.getString(R.string.login));
        FirebaseShared.getInstance().getTokenData().generateFCMToken(activity, new RequestListener<String>() {
            @Override
            public void onSuccess(String s, String msg) {
                map.put("device_token", s);
                Log.e("gfdgdfgdfg", map.toString());
                login(map);
            }

            @Override
            public void onFail(String msg) {
                ToolUtils.showLongToast(msg, activity);
                dialogView.hideDialog();
            }
        });
    }

    private void login(Map<String, String> map) {
        ApiShared.getInstance().getAuthData().login(activity, map, new RequestListener<User>() {
            @Override
            public void onSuccess(User user, String msg) {
                AppController.getInstance().getAppSettingsPreferences().setUser(user);
                AppController.getInstance().getAppSettingsPreferences().setKeyAccessToken(user.getToken());
                dialogView.hideDialog();
                if (user.getPackageUser().getId() == 0) {
                    view.navigate(AppContent.subscribe);
                } else {
                    AppController.getInstance().getAppSettingsPreferences().setLogin(true);
                    view.navigate(AppContent.MAIN);
                }
            }

            @Override
            public void onFail(String msg) {
                ToolUtils.showLongToast(msg, activity);
                dialogView.hideDialog();
            }
        });
    }
}
