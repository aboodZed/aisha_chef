package com.webapp.aisha.feture.main.home;

import android.app.Activity;
import android.util.Log;

import androidx.fragment.app.FragmentManager;

import com.webapp.aisha.R;
import com.webapp.aisha.services.api.ApiShared;
import com.webapp.aisha.models.MainCategory;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.dialogs.TwoChoiceDialog;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.ArrayList;

public class HomePresenter {

    private Activity activity;
    private NavigationView view;
    private HomeView homeView;

    public HomePresenter(Activity activity, NavigationView view, HomeView homeView) {
        this.activity = activity;
        this.view = view;
        this.homeView = homeView;
        Log.e("userToken", AppController.getInstance().getAppSettingsPreferences().getKeyAccessToken());
    }

    public void getData() {
        ArrayList<MainCategory> mainCategories = new ArrayList<>();
        mainCategories.addAll(AppController.getInstance().getAppSettingsPreferences().getUser().getMain_categories());
        mainCategories.addAll(AppController.getInstance().getAppSettingsPreferences().getUser().getSub_categories());
        homeView.initRecycleView(mainCategories);
        homeView.hideDialog();
        /*
        homeView.showDialog("");
        ApiShared.getInstance().getGetData().getCategory(activity, new RequestListener<ArrayList<MainCategory>>() {
            @Override
            public void onSuccess(ArrayList<MainCategory> mainCategories, String msg) {
                homeView.initRecycleView(mainCategories);
                homeView.hideDialog();
            }

            @Override
            public void onFail(String msg) {
                ToolUtils.showLongToast(msg, activity);
                homeView.hideDialog();
            }
        });
        */
    }

    public void reservations(FragmentManager fragmentManager) {
        TwoChoiceDialog twoChoiceDialog = TwoChoiceDialog.newInstance(activity.getString(R.string.your_reservation),
                activity.getString(R.string.in_the_same_day), activity.getString(R.string.before_day));
        twoChoiceDialog.show(fragmentManager, "");
        twoChoiceDialog.setListener(new TwoChoiceDialog.Listener() {
            @Override
            public void onFirstChoiceClicked() {
                AppController.getInstance().getAppSettingsPreferences().setReservation(1);
                twoChoiceDialog.dismiss();
            }

            @Override
            public void onSecondChoiceClicked() {
                AppController.getInstance().getAppSettingsPreferences().setReservation(2);
                twoChoiceDialog.dismiss();
            }
        });
    }
}
