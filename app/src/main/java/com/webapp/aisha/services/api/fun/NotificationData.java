package com.webapp.aisha.services.api.fun;

import android.app.Activity;

import com.webapp.aisha.R;
import com.webapp.aisha.models.Contact;
import com.webapp.aisha.models.Notification;
import com.webapp.aisha.models.Result;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationData {

    public void getNotification(Activity activity, String url, RequestListener<Result<ArrayList<Notification>>> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().getNotifications(url)
                    .enqueue(new Callback<Result<ArrayList<Notification>>>() {
                        @Override
                        public void onResponse(Call<Result<ArrayList<Notification>>> call, Response<Result<ArrayList<Notification>>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body(), response.body().getStatus().getMessage());
                            } else {
                                ToolUtils.showError(activity, response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<ArrayList<Notification>>> call, Throwable t) {
                            requestListener.onFail(t.getLocalizedMessage());
                        }
                    });
        } else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }

    public void sendOrderNotification(Activity activity, String body, int id, RequestListener<Object> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().sendNotification(body, id)
                    .enqueue(new Callback<Result<Object>>() {
                        @Override
                        public void onResponse(Call<Result<Object>> call, Response<Result<Object>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body().getData()
                                        , response.body().getStatus().getMessage());
                            } else {
                                ToolUtils.showError(activity, response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<Object>> call, Throwable t) {
                            requestListener.onFail(t.getLocalizedMessage());
                        }
                    });
        } else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }

    public void setRead(Activity activity, int id, RequestListener<Notification> requestListener) {
        if (ToolUtils.checkTheInternet()){
            AppController.getInstance().getApi().setNotificationRead(id)
                    .enqueue(new Callback<Result<Notification>>() {
                        @Override
                        public void onResponse(Call<Result<Notification>> call, Response<Result<Notification>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body().getData(), response.body().getStatus().getMessage());
                            } else {
                                ToolUtils.showError(activity, response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<Notification>> call, Throwable t) {
                            requestListener.onFail(t.getLocalizedMessage());
                        }
                    });
        }else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }

    public void contact(Activity activity, Map<String, String> map, RequestListener<Contact> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().contact(map)
                    .enqueue(new Callback<Result<Contact>>() {
                        @Override
                        public void onResponse(Call<Result<Contact>> call, Response<Result<Contact>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body().getData(), response.body().getStatus().getMessage());
                            } else {
                                ToolUtils.showError(activity, response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<Contact>> call, Throwable t) {
                            requestListener.onFail(t.getLocalizedMessage());
                        }
                    });
        } else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }
}

