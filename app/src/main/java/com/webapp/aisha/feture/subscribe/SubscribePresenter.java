package com.webapp.aisha.feture.subscribe;

import android.app.Activity;

import com.webapp.aisha.utils.NavigateUtils.NavigationView;

class SubscribePresenter {

    private Activity activity;
    private NavigationView navigationView;

    public SubscribePresenter(Activity activity, NavigationView navigationView) {
        this.activity = activity;
        this.navigationView = navigationView;
    }
}
