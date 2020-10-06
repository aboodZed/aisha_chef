package com.webapp.aisha.feture.main.schedule;

import android.app.Activity;
import android.util.Log;

import com.webapp.aisha.R;
import com.webapp.aisha.models.User;
import com.webapp.aisha.services.api.ApiShared;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.listener.DialogView;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.ArrayList;

public class SchedulePresenter {

    private Activity activity;
    private NavigationView view;
    private DialogView dialogView;

    public SchedulePresenter(Activity activity, NavigationView view, DialogView dialogView) {
        this.activity = activity;
        this.view = view;
        this.dialogView = dialogView;
    }

    public void updateDays(ArrayList<String> selected) {
        String s = "";
        if (!selected.isEmpty()) {
            s += selected.get(0);
            for (int i = 1; i < selected.size(); i++) {
                s += "," + selected.get(i);
            }
        } else {
            ToolUtils.showLongToast(activity.getString(R.string.select_days), activity);
            return;
        }
        Log.e("fdgdgfdgdfggfgf", s.trim());
        update(s.trim());
    }

    public void update(String s) {
        dialogView.showDialog(activity.getString(R.string.save));
        ApiShared.getInstance().getAuthData().setWorkingDays(activity, s
                , new RequestListener<User>() {
                    @Override
                    public void onSuccess(User user, String msg) {
                        AppController.getInstance().getAppSettingsPreferences().setUser(user);
                        dialogView.hideDialog();
                        view.navigate(AppContent.delivery_rate_one);
                    }

                    @Override
                    public void onFail(String msg) {
                        Log.e("gfgdfgdfgdfdfg", msg);
                        ToolUtils.showLongToast(msg, activity);
                        dialogView.hideDialog();
                    }
                });
    }
}
