package com.webapp.aisha.feture.subscribe.subscribe;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.webapp.aisha.R;
import com.webapp.aisha.models.Pay;
import com.webapp.aisha.models.Subscribe;
import com.webapp.aisha.models.User;
import com.webapp.aisha.payment.CheckoutUIActivity;
import com.webapp.aisha.services.api.ApiShared;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.formatter.DecimalFormatterManager;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.ArrayList;

import static com.webapp.aisha.utils.AppContent.AMOUNT;
import static com.webapp.aisha.utils.AppContent.login;

public class SubscribePresenter {

    private static final int LAUNCH_PAYMENT_ACTIVITY = 11;
    private Activity activity;
    private NavigationView view;
    private SubscribeView subscribeView;
    private String checkoutId = null, paymentBrand;

    public SubscribePresenter(Activity activity, NavigationView view, SubscribeView subscribeView) {
        this.activity = activity;
        this.view = view;
        this.subscribeView = subscribeView;
        getData();
    }

    private void getData() {
        subscribeView.showDialog(activity.getString(R.string.get_data));
        ApiShared.getInstance().getPackageData().getSubscribe(activity, new RequestListener<ArrayList<Subscribe>>() {
            @Override
            public void onSuccess(ArrayList<Subscribe> packageUsers, String msg) {
                subscribeView.setDataInPager(packageUsers);
                subscribeView.hideDialog();
            }

            @Override
            public void onFail(String msg) {
                ToolUtils.showLongToast(msg, activity);
                subscribeView.hideDialog();
            }
        });
    }

    public void creditCard() {
        Intent intent = new Intent(activity, CheckoutUIActivity.class);
        intent.putExtra(AMOUNT, DecimalFormatterManager.getFormatterInstance().format(
                AppController.getInstance().getAppSettingsPreferences().getSubscribe().getPrice()
        ));
        intent.putExtra(AppContent.subscribe_id,
                AppController.getInstance().getAppSettingsPreferences().getSubscribe().getId()
        );
        activity.startActivityForResult(intent, LAUNCH_PAYMENT_ACTIVITY);
//        ApiShared.getInstance().getPackageData().pay(activity, AppController.getInstance()
//                .getAppSettingsPreferences().getSubscribe().getId(), new RequestListener<Pay>() {
//            @Override
//            public void onSuccess(Pay s, String msg) {
//                subscribe(s.getCheckout_id());
//            }
//
//            @Override
//            public void onFail(String msg) {
//                subscribeView.hideDialog();
//                ToolUtils.showLongToast(msg, activity);
//            }
//        });
    }

    private void subscribe(String checkout_id) {
        String p = "agents/packages/" + AppController.getInstance().getAppSettingsPreferences()
                .getSubscribe().getId() + "/subscribe/credit_card?checkout_id=" + checkout_id;

        subscribeView.showDialog(activity.getString(R.string.subscribe));
        ApiShared.getInstance().getPackageData().subscribe(activity, p, new RequestListener<User>() {
            @Override
            public void onSuccess(User user, String msg) {
                AppController.getInstance().getAppSettingsPreferences().setUser(user);
                AppController.getInstance().getAppSettingsPreferences().setLogin(true);
                subscribeView.hideDialog();
                view.navigate(AppContent.schedule);
            }

            @Override
            public void onFail(String msg) {
                subscribeView.hideDialog();
                ToolUtils.showLongToast(msg, activity);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case LAUNCH_PAYMENT_ACTIVITY:
                    checkoutId = data.getStringExtra(AppContent.CHECKOUT_ID);
                    paymentBrand = data.getStringExtra(AppContent.PAYMENT_BRAND);
                    Log.e("checkout_id", "   =>" + checkoutId);
                    subscribe(checkoutId);
                    break;
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            ToolUtils.showShortToast(activity.getString(R.string.error_message), activity);
        }
    }
}
