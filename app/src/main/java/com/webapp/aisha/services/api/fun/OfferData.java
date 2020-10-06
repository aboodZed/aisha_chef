package com.webapp.aisha.services.api.fun;

import android.app.Activity;

import com.webapp.aisha.R;
import com.webapp.aisha.models.Offer;
import com.webapp.aisha.models.Result;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfferData {

    public void getOffers(Activity activity, String url
            , RequestListener<Result<ArrayList<Offer>>> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().getOffers(url)
                    .enqueue(new Callback<Result<ArrayList<Offer>>>() {
                        @Override
                        public void onResponse(Call<Result<ArrayList<Offer>>> call
                                , Response<Result<ArrayList<Offer>>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body(), response.body().getStatus().getMessage());
                            } else {
                                ToolUtils.showError(activity, response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<ArrayList<Offer>>> call, Throwable t) {
                            requestListener.onFail(t.getLocalizedMessage());
                        }
                    });
        } else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }

    public void getOffer(Activity activity, int id, RequestListener<Offer> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().getOffer(id)
                    .enqueue(new Callback<Result<Offer>>() {
                        @Override
                        public void onResponse(Call<Result<Offer>> call, Response<Result<Offer>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body().getData()
                                        , response.body().getStatus().getMessage());
                            } else {
                                ToolUtils.showError(activity, response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<Offer>> call, Throwable t) {
                            requestListener.onFail(t.getLocalizedMessage());
                        }
                    });
        } else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }

    public void storeOffer(Activity activity, Map<String, String> map, MultipartBody.Part image
            , RequestListener<Offer> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().storeOffer(map, image)
                    .enqueue(new Callback<Result<Offer>>() {
                        @Override
                        public void onResponse(Call<Result<Offer>> call, Response<Result<Offer>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body().getData(), response.body().getStatus().getMessage());
                            } else {
                                ToolUtils.showError(activity, response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<Offer>> call, Throwable t) {
                            requestListener.onFail(t.getLocalizedMessage());
                        }
                    });
        } else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }

    public void updateOffer(Activity activity, int id, int status, RequestListener<Offer> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().updateOffer(id, status)
                    .enqueue(new Callback<Result<Offer>>() {
                        @Override
                        public void onResponse(Call<Result<Offer>> call, Response<Result<Offer>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body().getData()
                                        , response.body().getStatus().getMessage());
                            } else {
                                ToolUtils.showError(activity, response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<Offer>> call, Throwable t) {
                            requestListener.onFail(t.getLocalizedMessage());
                        }
                    });
        } else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }
}
