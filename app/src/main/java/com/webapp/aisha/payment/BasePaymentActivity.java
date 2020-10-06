package com.webapp.aisha.payment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.WalletConstants;
import com.oppwa.mobile.connect.checkout.dialog.CheckoutActivity;
import com.oppwa.mobile.connect.checkout.dialog.GooglePayHelper;
import com.oppwa.mobile.connect.checkout.meta.CheckoutSettings;
import com.oppwa.mobile.connect.checkout.meta.CheckoutSkipCVVMode;
import com.oppwa.mobile.connect.exception.PaymentError;
import com.oppwa.mobile.connect.provider.Connect;
import com.oppwa.mobile.connect.provider.Transaction;
import com.oppwa.mobile.connect.provider.TransactionType;
import com.webapp.aisha.R;
import com.webapp.aisha.models.Pay;
import com.webapp.aisha.services.api.ApiShared;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.language.BaseActivity;
import com.webapp.aisha.utils.listener.RequestListener;

import static com.webapp.aisha.utils.AppContent.CHECKOUT_ID;
import static com.webapp.aisha.utils.AppContent.PAYMENT_BRAND;

/**
 * Represents a base activity for making the payments with mobile sdk.
 * This activity handles payment callbacks.
 */
@SuppressLint("Registered")
public class BasePaymentActivity extends BaseActivity {

    private static final String STATE_RESOURCE_PATH = "STATE_RESOURCE_PATH";

    protected String resourcePath, paymentBrand, totalAmount, checkoutId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            resourcePath = savedInstanceState.getString(STATE_RESOURCE_PATH);
        }
    }

    protected void requestCheckoutId(int id) {
        showProgressDialog(R.string.progress_message_checkout_id);
        ApiShared.getInstance().getPackageData().pay(this, id, new RequestListener<Pay>() {
            @Override
            public void onSuccess(Pay pay, String msg) {
                Log.e(getClass().getName() + " response: ", pay.toString());
                onCheckoutIdReceived(pay.getCheckout_id());

            }

            @Override
            public void onFail(String msg) {
                hideProgressDialog();
                ToolUtils.showLongToast(msg, BasePaymentActivity.this);
            }
        });
    }

    public void onCheckoutIdReceived(String checkoutId) {
        Log.e("checkoutId", checkoutId);
        this.checkoutId = checkoutId;
        hideProgressDialog();

        if (checkoutId == null) {
            showAlertDialog(R.string.error);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        /* Check if the intent contains the callback scheme. */
        if (resourcePath != null && hasCallbackScheme(intent)) {
            applyPayment();
        }
    }

    public void applyPayment() {
        Toast.makeText(this, getString(R.string.message_successful_payment), Toast.LENGTH_SHORT).show();
        Intent returnIntent = new Intent();
        returnIntent.putExtra(CHECKOUT_ID, checkoutId);
        returnIntent.putExtra(PAYMENT_BRAND, paymentBrand);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    /**
     * Returns <code>true</code> if the Intent contains one of the predefined schemes for the app.
     *
     * @param intent the incoming intent
     * @return <code>true</code> if the Intent contains one of the predefined schemes for the app;
     * <code>false</code> otherwise
     */
    protected boolean hasCallbackScheme(Intent intent) {
        String scheme = intent.getScheme();
        return getString(R.string.checkout_ui_callback_scheme).equals(scheme) ||
                getString(R.string.payment_button_callback_scheme).equals(scheme) ||
                getString(R.string.custom_ui_callback_scheme).equals(scheme);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(STATE_RESOURCE_PATH, resourcePath);
    }

    /**
     * Creates the new instance of {@link CheckoutSettings}
     * to instantiate the {@link CheckoutActivity}.
     *
     * @param checkoutId the received checkout id
     * @return the new instance of {@link CheckoutSettings}
     */
    protected CheckoutSettings createCheckoutSettings(String checkoutId, String callbackScheme) {
        return new CheckoutSettings(checkoutId, AppContent.Config.PAYMENT_BRANDS,
                Connect.ProviderMode.TEST)
                .setSkipCVVMode(CheckoutSkipCVVMode.FOR_STORED_CARDS)
                .setShopperResultUrl(callbackScheme + "://callback")
                .setGooglePayPaymentDataRequest(getGooglePayRequest());
    }

    private PaymentDataRequest getGooglePayRequest() {
        return GooglePayHelper.preparePaymentDataRequestBuilder(
                totalAmount,
                AppContent.Config.CURRENCY,
                AppContent.MERCHANT_ID,
                getPaymentMethodsForGooglePay(),
                getDefaultCardNetworksForGooglePay()
        ).build();
    }

    private Integer[] getPaymentMethodsForGooglePay() {
        return new Integer[]{
                WalletConstants.PAYMENT_METHOD_CARD,
                WalletConstants.PAYMENT_METHOD_TOKENIZED_CARD
        };
    }

    private Integer[] getDefaultCardNetworksForGooglePay() {
        return new Integer[]{
                WalletConstants.CARD_NETWORK_VISA,
                WalletConstants.CARD_NETWORK_MASTERCARD,
                WalletConstants.CARD_NETWORK_AMEX
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /* Override onActivityResult to get notified when the checkout process is done. */
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CheckoutActivity.REQUEST_CODE_CHECKOUT) {
            switch (resultCode) {
                case CheckoutActivity.RESULT_OK:
                    /* Transaction completed. */
                    Transaction transaction = data.getParcelableExtra(
                            CheckoutActivity.CHECKOUT_RESULT_TRANSACTION);

                    paymentBrand = transaction.getPaymentParams().getPaymentBrand();
                    resourcePath = data.getStringExtra(
                            CheckoutActivity.CHECKOUT_RESULT_RESOURCE_PATH);

                    /* Check the transaction type. */
                    if (transaction.getTransactionType() == TransactionType.SYNC) {
                        /* Check the status of synchronous transaction. */
                        applyPayment();
                    } else {
                        /* Asynchronous transaction is processed in the onNewIntent(). */
                        hideProgressDialog();
                    }

                    break;
                case CheckoutActivity.RESULT_CANCELED:
                    hideProgressDialog();

                    break;
                case CheckoutActivity.RESULT_ERROR:
                    hideProgressDialog();

                    PaymentError error = data.getParcelableExtra(
                            CheckoutActivity.CHECKOUT_RESULT_ERROR);

                    showAlertDialog(R.string.error_message);
            }
        }
    }
}