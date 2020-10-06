package com.webapp.aisha.feture.offers;

import android.app.Activity;

import com.webapp.aisha.utils.NavigateUtils.NavigationView;

public class OfferPresenter {

    private Activity activity;
    private NavigationView view;

    public OfferPresenter(Activity activity, NavigationView view) {
        this.activity = activity;
        this.view = view;
    }
}
