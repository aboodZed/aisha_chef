package com.webapp.aisha.feture.main.orders;

import android.app.Activity;

import com.webapp.aisha.utils.NavigateUtils.NavigationView;

public class OrdersPresenter {

    private Activity activity;
    private NavigationView view;

    public OrdersPresenter(Activity activity, NavigationView view) {
        this.activity = activity;
        this.view = view;
    }
}
