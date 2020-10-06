package com.webapp.aisha.services.api.fun;

import android.app.Activity;

import com.webapp.aisha.R;
import com.webapp.aisha.models.BankTransfer;
import com.webapp.aisha.models.Claim;
import com.webapp.aisha.models.Result;
import com.webapp.aisha.models.Wallet;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletData {

    public void getProcessing(Activity activity, String url, RequestListener<Result<ArrayList<Wallet>>> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().getProcessing(url)
                    .enqueue(new Callback<Result<ArrayList<Wallet>>>() {
                        @Override
                        public void onResponse(Call<Result<ArrayList<Wallet>>> call, Response<Result<ArrayList<Wallet>>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body(), response.body().getStatus().getMessage());
                            } else {
                                ToolUtils.showError(activity, response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<ArrayList<Wallet>>> call, Throwable t) {
                            requestListener.onFail(t.getLocalizedMessage());
                        }
                    });
        } else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }

    public void getClaims(Activity activity, String url, RequestListener<Result<ArrayList<Claim>>> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().getClaims(url)
                    .enqueue(new Callback<Result<ArrayList<Claim>>>() {
                        @Override
                        public void onResponse(Call<Result<ArrayList<Claim>>> call
                                , Response<Result<ArrayList<Claim>>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body(), response.body().getStatus().getMessage());
                            } else {
                                ToolUtils.showError(activity, response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<ArrayList<Claim>>> call, Throwable t) {
                            requestListener.onFail(t.getLocalizedMessage());
                        }
                    });
        } else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }

    public void getBankTransfers(Activity activity, String url, RequestListener<Result<ArrayList<BankTransfer>>> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().getBankTransfers(url)
                    .enqueue(new Callback<Result<ArrayList<BankTransfer>>>() {
                        @Override
                        public void onResponse(Call<Result<ArrayList<BankTransfer>>> call, Response<Result<ArrayList<BankTransfer>>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body()
                                        , response.body().getStatus().getMessage());
                            } else {
                                ToolUtils.showError(activity, response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<ArrayList<BankTransfer>>> call, Throwable t) {
                            requestListener.onFail(t.getLocalizedMessage());
                        }
                    });
        } else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }

    public void sendBakTransfer(Activity activity, Map<String, String> map, MultipartBody.Part part
            , RequestListener<BankTransfer> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().sendBankTransfer(map, part)
                    .enqueue(new Callback<Result<BankTransfer>>() {
                        @Override
                        public void onResponse(Call<Result<BankTransfer>> call, Response<Result<BankTransfer>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body().getData()
                                        , response.body().getStatus().getMessage());
                            } else {
                                ToolUtils.showError(activity, response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<BankTransfer>> call, Throwable t) {
                            requestListener.onFail(t.getLocalizedMessage());
                        }
                    });
        } else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }

    public void SendClaimOrder(Activity activity, double amount, RequestListener<Claim> requestListener) {
        if (ToolUtils.checkTheInternet()) {
            AppController.getInstance().getApi().sendClaim(amount)
                    .enqueue(new Callback<Result<Claim>>() {
                        @Override
                        public void onResponse(Call<Result<Claim>> call, Response<Result<Claim>> response) {
                            if (response.isSuccessful()) {
                                requestListener.onSuccess(response.body().getData()
                                        , response.body().getStatus().getMessage());
                            } else {
                                ToolUtils.showError(activity, response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<Claim>> call, Throwable t) {
                            requestListener.onFail(t.getLocalizedMessage());
                        }
                    });
        } else {
            requestListener.onFail(activity.getString(R.string.check_internet));
        }
    }
}
