package com.webapp.aisha.services.api.fun;

import android.app.Activity;

import com.webapp.aisha.R;
import com.webapp.aisha.models.Order;
import com.webapp.aisha.models.Result;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderData {

    public void getOrders(Activity activity, String status, RequestListener<Result<ArrayList<Order>>> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().getOrders(status)
                    .enqueue(new Callback<Result<ArrayList<Order>>>() {
                        @Override
                        public void onResponse(Call<Result<ArrayList<Order>>> call, Response<Result<ArrayList<Order>>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body(), response.body().getStatus().getMessage());
                            } else {
                                ToolUtils.showError(activity, response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<ArrayList<Order>>> call, Throwable t) {
                            requestListener.onFail(t.getLocalizedMessage());
                        }
                    });

        } else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }

    public void getOrder(Activity activity, int id, RequestListener<Order> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().getOrder(id)
                    .enqueue(new Callback<Result<Order>>() {
                        @Override
                        public void onResponse(Call<Result<Order>> call, Response<Result<Order>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body().getData(), response.body().getStatus().getMessage());
                            } else {
                                ToolUtils.showError(activity, response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<Order>> call, Throwable t) {
                            requestListener.onFail(t.getLocalizedMessage());
                        }
                    });
        } else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }

    public void setPreparing(Activity activity, int id, RequestListener<Order> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().setOrderPreparing(id)
                    .enqueue(new Callback<Result<Order>>() {
                        @Override
                        public void onResponse(Call<Result<Order>> call, Response<Result<Order>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body().getData(), response.body().getStatus().getMessage());
                            } else {
                                ToolUtils.showError(activity, response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<Order>> call, Throwable t) {
                            requestListener.onFail(t.getLocalizedMessage());
                        }
                    });
        }else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }

    public void setReady(Activity activity, int id, RequestListener<Order> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().setOrderReady(id)
                    .enqueue(new Callback<Result<Order>>() {
                        @Override
                        public void onResponse(Call<Result<Order>> call, Response<Result<Order>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body().getData(), response.body().getStatus().getMessage());
                            } else {
                                ToolUtils.showError(activity, response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<Order>> call, Throwable t) {
                            requestListener.onFail(t.getLocalizedMessage());
                        }
                    });
        }else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }

    public void setOnWay(Activity activity, int id, RequestListener<Order> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().setOrderOnWay(id)
                    .enqueue(new Callback<Result<Order>>() {
                        @Override
                        public void onResponse(Call<Result<Order>> call, Response<Result<Order>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body().getData(), response.body().getStatus().getMessage());
                            } else {
                                ToolUtils.showError(activity, response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<Order>> call, Throwable t) {
                            requestListener.onFail(t.getLocalizedMessage());
                        }
                    });
        }else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }
}
