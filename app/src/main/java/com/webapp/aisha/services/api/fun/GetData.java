package com.webapp.aisha.services.api.fun;

import android.app.Activity;

import com.webapp.aisha.R;
import com.webapp.aisha.models.City;
import com.webapp.aisha.models.Config;
import com.webapp.aisha.models.MainCategory;
import com.webapp.aisha.models.PageData;
import com.webapp.aisha.models.Result;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetData {

    public void getCities(Activity activity, RequestListener<ArrayList<City>> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().getCities().enqueue(new Callback<Result<ArrayList<City>>>() {
                @Override
                public void onResponse(Call<Result<ArrayList<City>>> call, Response<Result<ArrayList<City>>> response) {
                    if (response.isSuccessful()) {
                        requestListener.onSuccess(response.body().getData(), response.body().getStatus().getMessage());
                    } else {
                        ToolUtils.showError(activity, response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<Result<ArrayList<City>>> call, Throwable t) {
                    requestListener.onFail(t.getLocalizedMessage());
                }
            });
        } else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }

    public void getCategory(Activity activity, RequestListener<ArrayList<MainCategory>> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().getCategories()
                    .enqueue(new Callback<Result<ArrayList<MainCategory>>>() {
                        @Override
                        public void onResponse(Call<Result<ArrayList<MainCategory>>> call, Response<Result<ArrayList<MainCategory>>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body().getData(), response.body().getStatus().getMessage());
                            } else {
                                ToolUtils.showError(activity, response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<ArrayList<MainCategory>>> call, Throwable t) {
                            requestListener.onFail(t.getLocalizedMessage());
                        }
                    });
        } else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }

    public void getPages(Activity activity, RequestListener<ArrayList<PageData>> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().getPages()
                    .enqueue(new Callback<Result<ArrayList<PageData>>>() {
                        @Override
                        public void onResponse(Call<Result<ArrayList<PageData>>> call, Response<Result<ArrayList<PageData>>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body().getData(), response.body().getStatus().getMessage());
                            } else {
                                ToolUtils.showError(activity, response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<ArrayList<PageData>>> call, Throwable t) {
                            requestListener.onFail(t.getLocalizedMessage());
                        }
                    });
        } else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }

    public void getConfig(Activity activity, RequestListener<ArrayList<Config>> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().getConfig()
                    .enqueue(new Callback<Result<ArrayList<Config>>>() {
                        @Override
                        public void onResponse(Call<Result<ArrayList<Config>>> call, Response<Result<ArrayList<Config>>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body().getData(), response.body().getStatus().getMessage());
                            } else {
                                ToolUtils.showError(activity, response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<ArrayList<Config>>> call, Throwable t) {
                            requestListener.onFail(t.getLocalizedMessage());
                        }
                    });
        } else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }

    public void uploadPhoto(Activity activity, MultipartBody.Part part, RequestListener<String> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().uploadPhoto(part)
                    .enqueue(new Callback<Result<String>>() {
                        @Override
                        public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body().getData(), "");
                            } else {
                                ToolUtils.showError(activity, response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<String>> call, Throwable t) {
                            requestListener.onFail(t.getLocalizedMessage());
                        }
                    });
        } else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }
}
