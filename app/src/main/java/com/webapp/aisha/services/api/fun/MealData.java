package com.webapp.aisha.services.api.fun;

import android.app.Activity;

import com.webapp.aisha.R;
import com.webapp.aisha.models.Meal;
import com.webapp.aisha.models.Result;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealData {

    public void getMeals(Activity activity, String url
            , RequestListener<Result<ArrayList<Meal>>> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().getMeals(url)
                    .enqueue(new Callback<Result<ArrayList<Meal>>>() {
                        @Override
                        public void onResponse(Call<Result<ArrayList<Meal>>> call, Response<Result<ArrayList<Meal>>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body(), response.body().getStatus().getMessage());
                            } else {
                                ToolUtils.showError(activity, response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<ArrayList<Meal>>> call, Throwable t) {
                            requestListener.onFail(t.getLocalizedMessage());
                        }
                    });
        } else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }

    public void getMeal(Activity activity, int id, RequestListener<Meal> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().getMeal(id)
                    .enqueue(new Callback<Result<Meal>>() {
                        @Override
                        public void onResponse(Call<Result<Meal>> call, Response<Result<Meal>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body().getData(), response.body().getStatus().getMessage());
                            } else {
                                ToolUtils.showError(activity, response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<Meal>> call, Throwable t) {
                            requestListener.onFail(t.getLocalizedMessage());
                        }
                    });
        } else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }

    public void storeMeal(Activity activity,Map<String, String> map, RequestListener<Meal> requestListener){
        if (ToolUtils.checkTheInternet()){
            AppController.getInstance().getApi().storeMeal(map)
                    .enqueue(new Callback<Result<Meal>>() {
                        @Override
                        public void onResponse(Call<Result<Meal>> call, Response<Result<Meal>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body().getData(), response.body().getStatus().getMessage());
                            } else {
                                ToolUtils.showError(activity, response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<Meal>> call, Throwable t) {
                            requestListener.onFail(t.getLocalizedMessage());
                        }
                    });
        }else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }

    public void updateMeal(Activity activity, int id, Map<String, String> map, RequestListener<Meal> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().updateMeal(id, map)
                    .enqueue(new Callback<Result<Meal>>() {
                        @Override
                        public void onResponse(Call<Result<Meal>> call, Response<Result<Meal>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body().getData(), response.body().getStatus().getMessage());
                            } else {
                                ToolUtils.showError(activity, response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<Meal>> call, Throwable t) {
                            requestListener.onFail(t.getLocalizedMessage());
                        }
                    });
        } else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }
}
