package com.webapp.aisha.feture.registration.reset.stepOne;

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

public class ResetStepOnePresenter {

    private Activity activity;
    private NavigationView view;
    private DialogView dialogView;

    public ResetStepOnePresenter(Activity activity, NavigationView view, DialogView dialogView) {
        this.activity = activity;
        this.view = view;
        this.dialogView = dialogView;
    }

    public void validateInput(EditText phone) {

        String sPhone = phone.getText().toString().trim();

        if (TextUtils.isEmpty(sPhone)) {
            phone.setError(activity.getString(R.string.required_field));
            return;
        }

        if (sPhone.length() != 9) {
            phone.setError(activity.getString(R.string.nine_digits));
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("country_code","972");
        map.put("mobile",sPhone);

        sendCode(map);
    }

    private void sendCode(Map<String, String> map) {
        dialogView.showDialog(activity.getString(R.string.send_code));
        ApiShared.getInstance().getAuthData().forgetPassword(activity, map, new RequestListener<ResetCode>() {
            @Override
            public void onSuccess(ResetCode resetCode, String msg) {
                dialogView.hideDialog();
                view.navigate(AppContent.reset_step_2);
            }

            @Override
            public void onFail(String msg) {
                dialogView.hideDialog();
                ToolUtils.showLongToast(msg, activity);
            }
        });
    }
}
