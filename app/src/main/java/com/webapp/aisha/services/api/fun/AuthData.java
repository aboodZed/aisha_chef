package com.webapp.aisha.services.api.fun;

import android.app.Activity;
import android.util.Log;

import com.webapp.aisha.R;
import com.webapp.aisha.models.ResetCode;
import com.webapp.aisha.models.Result;
import com.webapp.aisha.models.User;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthData {

    public void register(Activity activity, Map<String, String> map, MultipartBody.Part[] parts
            , RequestListener<User> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().signUp(map, parts[0], parts[1], parts[2])
                    .enqueue(new Callback<Result<User>>() {
                        @Override
                        public void onResponse(Call<Result<User>> call, Response<Result<User>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body().getData(), response.body().getStatus().getMessage());
                            } else {
                                ToolUtils.showError(activity, response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<User>> call, Throwable t) {
                            Log.e("fgfgfggfg",t.toString());
                            requestListener.onFail(t.getLocalizedMessage());
                        }
                    });
        } else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }

    public void login(Activity activity, Map<String, String> map, RequestListener<User> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().login(map)
                    .enqueue(new Callback<Result<User>>() {
                        @Override
                        public void onResponse(Call<Result<User>> call, Response<Result<User>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body().getData(), response.body().getStatus().getMessage());
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

    public void forgetPassword(Activity activity, Map<String, String> map, RequestListener<ResetCode> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().forgetPassword(map)
                    .enqueue(new Callback<Result<ResetCode>>() {
                        @Override
                        public void onResponse(Call<Result<ResetCode>> call, Response<Result<ResetCode>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body().getData(), response.body().getStatus().getMessage());
                            } else {
                                ToolUtils.showError(activity, response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<ResetCode>> call, Throwable t) {
                            requestListener.onFail(t.getLocalizedMessage());
                        }
                    });
        } else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }

    public void resetPassword(Activity activity, Map<String, String> map, RequestListener<ResetCode> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().resetPassword(map).
                    enqueue(new Callback<Result<ResetCode>>() {
                        @Override
                        public void onResponse(Call<Result<ResetCode>> call, Response<Result<ResetCode>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body().getData(), response.body().getStatus().getMessage());
                            } else {
                                ToolUtils.showError(activity, response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<ResetCode>> call, Throwable t) {
                            requestListener.onFail(t.getLocalizedMessage());
                        }
                    });
        } else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }

    public void changePassword(Activity activity, Map<String, String> map, RequestListener<User> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().changePassword(map)
                    .enqueue(new Callback<Result<User>>() {
                        @Override
                        public void onResponse(Call<Result<User>> call, Response<Result<User>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body().getData(), response.body().getStatus().getMessage());
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

    public void updateProfile(Activity activity, Map<String, String> map, MultipartBody.Part[] parts
            , RequestListener<User> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().updateProfile(map, parts[0], parts[1], parts[2])
                    .enqueue(new Callback<Result<User>>() {
                        @Override
                        public void onResponse(Call<Result<User>> call, Response<Result<User>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body().getData(), response.body().getStatus().getMessage());
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

    public void updateStatus(Activity activity, int is_online, RequestListener<User> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().changeStatus(is_online)
                    .enqueue(new Callback<Result<User>>() {
                        @Override
                        public void onResponse(Call<Result<User>> call, Response<Result<User>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body().getData(), response.body().getStatus().getMessage());
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

    public void setDeliveryTime(Activity activity, Map<String, String> map, RequestListener<User> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().setDeliveryTime(map)
                    .enqueue(new Callback<Result<User>>() {
                        @Override
                        public void onResponse(Call<Result<User>> call, Response<Result<User>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body().getData(), response.body().getStatus().getMessage());
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

    public void setWorkingDays(Activity activity, String days, RequestListener<User> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().setWorkingDays(days)
                    .enqueue(new Callback<Result<User>>() {
                        @Override
                        public void onResponse(Call<Result<User>> call, Response<Result<User>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body().getData(), response.body().getStatus().getMessage());
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

    public void getProfile(Activity activity, RequestListener<User> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().getProfile()
                    .enqueue(new Callback<Result<User>>() {
                        @Override
                        public void onResponse(Call<Result<User>> call, Response<Result<User>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body().getData(), response.body().getStatus().getMessage());
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
}
