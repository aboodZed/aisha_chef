package com.webapp.aisha.services.api.fun;

import android.app.Activity;

import com.webapp.aisha.R;
import com.webapp.aisha.models.Pay;
import com.webapp.aisha.models.Rating;
import com.webapp.aisha.models.Result;
import com.webapp.aisha.models.Subscribe;
import com.webapp.aisha.models.User;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackageData {

    public void getSubscribe(Activity activity, RequestListener<ArrayList<Subscribe>> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().getSubscribe()
                    .enqueue(new Callback<Result<ArrayList<Subscribe>>>() {
                        @Override
                        public void onResponse(Call<Result<ArrayList<Subscribe>>> call, Response<Result<ArrayList<Subscribe>>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body().getData(), response.body().getStatus().getMessage());
                            } else {
                                ToolUtils.showError(activity, response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<ArrayList<Subscribe>>> call, Throwable t) {
                            requestListener.onFail(t.getLocalizedMessage());
                        }
                    });
        } else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }

    public void pay(Activity activity, int id, RequestListener<Pay> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().pay(id)
                    .enqueue(new Callback<Result<Pay>>() {
                        @Override
                        public void onResponse(Call<Result<Pay>> call, Response<Result<Pay>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body().getData()
                                        , response.body().getStatus().getMessage());
                            } else {
                                ToolUtils.showError(activity, response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<Pay>> call, Throwable t) {
                            requestListener.onFail(t.getLocalizedMessage());
                        }
                    });
        } else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }

    public void subscribe(Activity activity, String url, RequestListener<User> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().subscribe(url)
                    .enqueue(new Callback<Result<User>>() {
                        @Override
                        public void onResponse(Call<Result<User>> call, Response<Result<User>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body().getData()
                                        , response.body().getStatus().getMessage());
                            } else {
                                ToolUtils.showError(activity, response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<User>> call, Throwable t) {
                            requestListener.onFail(t.getLocalizedMessage());
                        }
                    });

        } else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }

    public void bankTransfer(Activity activity, int id, Map<String, String> map, MultipartBody.Part part
            , RequestListener<User> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().bankTransfer(id, map, part)
                    .enqueue(new Callback<Result<User>>() {
                        @Override
                        public void onResponse(Call<Result<User>> call, Response<Result<User>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body().getData()
                                        , response.body().getStatus().getMessage());
                            } else {
                                ToolUtils.showError(activity, response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<User>> call, Throwable t) {
                            requestListener.onFail(t.getLocalizedMessage());
                        }
                    });

        } else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }

    public void getRating(Activity activity, String url, RequestListener<Result<ArrayList<Rating>>> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().getRating(url)
                    .enqueue(new Callback<Result<ArrayList<Rating>>>() {
                        @Override
                        public void onResponse(Call<Result<ArrayList<Rating>>> call, Response<Result<ArrayList<Rating>>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body()
                                        , response.body().getStatus().getMessage());
                            } else {
                                ToolUtils.showError(activity, response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<ArrayList<Rating>>> call, Throwable t) {
                            requestListener.onFail(t.getLocalizedMessage());
                        }
                    });
        } else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }
}
