package com.webapp.aisha.feture.registration.reset.stepTwo;

import android.app.Activity;

import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;

public class ResetStepTwoPresenter {

    private Activity activity;
    private NavigationView view;

    public ResetStepTwoPresenter(Activity activity, NavigationView view) {
        this.activity = activity;
        this.view = view;
    }

    public void setCode(String s) {
        ToolUtils.showLongToast(s, activity);
        view.navigate(AppContent.reset_step_3);
    }
}
