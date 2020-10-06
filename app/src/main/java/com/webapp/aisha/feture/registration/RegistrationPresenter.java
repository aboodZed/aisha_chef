package com.webapp.aisha.feture.registration;

import android.app.Activity;

import com.webapp.aisha.utils.NavigateUtils.NavigationView;

public class RegistrationPresenter {

    private Activity activity;
    private RegistrationView view;
    private NavigationView navigate;

    public RegistrationPresenter(Activity activity, RegistrationView view, NavigationView navigate) {
        this.activity = activity;
        this.view = view;
        this.navigate = navigate;
    }
}
