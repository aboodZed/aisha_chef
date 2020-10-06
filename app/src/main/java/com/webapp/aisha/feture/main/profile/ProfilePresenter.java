package com.webapp.aisha.feture.main.profile;

import android.app.Activity;

import com.webapp.aisha.models.Pay;
import com.webapp.aisha.models.Result;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.listener.RequestListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter {

    private Activity activity;
    private NavigationView view;

    public ProfilePresenter(Activity activity, NavigationView view) {
        this.activity = activity;
        this.view = view;
    }

    public void checkSubscribe(RequestListener<Boolean> requestListener) {
        AppController.getInstance().getApi().pay(AppController.getInstance().
                getAppSettingsPreferences().getSubscribe().getId())
                .enqueue(new Callback<Result<Pay>>() {
                    @Override
                    public void onResponse(Call<Result<Pay>> call, Response<Result<Pay>> response) {
                        requestListener.onSuccess(response.body().getStatus().isSuccess()
                                , response.body().getStatus().getMessage());
                    }

                    @Override
                    public void onFailure(Call<Result<Pay>> call, Throwable t) {
                        requestListener.onSuccess(false, t.getLocalizedMessage());
                    }
                });
    }
}
