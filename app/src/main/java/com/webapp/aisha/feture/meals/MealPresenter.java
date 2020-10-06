package com.webapp.aisha.feture.meals;

import android.app.Activity;

import com.webapp.aisha.utils.NavigateUtils.NavigationView;

public class MealPresenter {

    private Activity activity;
    private NavigationView view;

    public MealPresenter(Activity activity, NavigationView view) {
        this.activity = activity;
        this.view = view;
    }
}
