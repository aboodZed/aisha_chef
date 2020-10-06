package com.webapp.aisha.feture.registration.reset.stepThree;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.EditText;

import com.webapp.aisha.R;
import com.webapp.aisha.services.api.ApiShared;
import com.webapp.aisha.models.ResetCode;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.listener.DialogView;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.HashMap;
import java.util.Map;

public class ResetStepThreePresenter {

    private Activity activity;
    private NavigationView view;
    private DialogView dialogView;

    public ResetStepThreePresenter(Activity activity, NavigationView view, DialogView dialogView) {
        this.activity = activity;
        this.view = view;
        this.dialogView = dialogView;
    }

    public void validateInput(EditText password,
                              EditText password_confirm) {

        String spassword = password.getText().toString();
        String spassword_confirm = password_confirm.getText().toString();

        if (TextUtils.isEmpty(spassword)) {
            password.setError(activity.getString(R.string.required_field));
            return;
        }
        if (TextUtils.isEmpty(spassword_confirm)) {
            password_confirm.setError(activity.getString(R.string.required_field));
            return;
        }
        if (!spassword.equals(spassword_confirm)) {
            password_confirm.setError(activity.getString(R.string.not_match));
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("country_code", AppContent.COUNTRY_CODE);
        map.put("mobile", "");
        map.put("password", spassword);
        map.put("type", "change_password");

        refresh(map);
    }

    private void refresh(Map<String, String> map) {
        dialogView.showDialog(activity.getString(R.string.reset_your_password));
        ApiShared.getInstance().getAuthData().resetPassword(activity, map, new RequestListener<ResetCode>() {
            @Override
            public void onSuccess(ResetCode resetCode, String msg) {
                view.navigate(AppContent.login);
                dialogView.hideDialog();
            }

            @Override
            public void onFail(String msg) {
                ToolUtils.showLongToast(msg, activity);
                dialogView.hideDialog();
            }
        });
    }
}
