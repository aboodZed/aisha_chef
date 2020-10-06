package com.webapp.aisha.feture.main.wallet;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.webapp.aisha.R;
import com.webapp.aisha.services.api.ApiShared;
import com.webapp.aisha.models.Claim;
import com.webapp.aisha.models.Config;
import com.webapp.aisha.models.User;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.NavigateUtils;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.ArrayList;

public class WalletPresenter {

    private Activity activity;
    private NavigationView view;
    private WalletView<User> userWalletView;
    private double balance;

    public WalletPresenter(Activity activity, NavigationView view, WalletView<User> userWalletView) {
        this.activity = activity;
        this.view = view;
        this.userWalletView = userWalletView;
        getData();
    }

    private void getData() {
        ApiShared.getInstance().getAuthData().getProfile(activity,
                new RequestListener<User>() {
                    @Override
                    public void onSuccess(User user, String msg) {
                        AppController.getInstance().getAppSettingsPreferences().setUser(user);
                        balance = user.getCurrent_balance();
                        userWalletView.setData(user);
                    }

                    @Override
                    public void onFail(String msg) {
                        ToolUtils.showLongToast(msg, activity);
                    }
                });
    }

    public void getConfig(Button claim, Button bankTransfer) {
        ApiShared.getInstance().getGetData().getConfig(activity
                , new RequestListener<ArrayList<Config>>() {
                    @Override
                    public void onSuccess(ArrayList<Config> configs, String msg) {
                        for (Config config : configs) {
                            if (config.getKey().equals(AppContent.min_withdraw_amount)) {
                                double value = Double.parseDouble(config.getValue().toString());
                                if (balance > value) {
                                    claim.setVisibility(View.VISIBLE);
                                }
                            } else if (config.getKey().equals(AppContent.credit_limit)) {
                                double value = Double.parseDouble(config.getValue().toString());
                                if (balance < value) {
                                    bankTransfer.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFail(String msg) {
                        ToolUtils.showLongToast(msg, activity);
                    }
                });
    }

    public void sendClaim() {
        userWalletView.showDialog(activity.getString(R.string.claim));
        ApiShared.getInstance().getWalletData().SendClaimOrder(activity, balance,
                new RequestListener<Claim>() {
                    @Override
                    public void onSuccess(Claim claim, String msg) {
                        userWalletView.hideDialog();
                        ToolUtils.showLongToast(activity.getString(R.string.waiting_accept_claim), activity);
                        view.navigate(AppContent.wallet);
                    }

                    @Override
                    public void onFail(String msg) {
                        userWalletView.hideDialog();
                    }
                });
    }

    public void bankTransfer() {
        NavigateUtils.openPay(activity, AppContent.payment, balance);

    }
}
