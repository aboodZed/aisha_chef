package com.webapp.aisha.payment;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.oppwa.mobile.connect.checkout.dialog.CheckoutActivity;
import com.oppwa.mobile.connect.checkout.meta.CheckoutSettings;
import com.webapp.aisha.R;
import com.webapp.aisha.payment.receiver.CheckoutBroadcastReceiver;
import com.webapp.aisha.utils.AppContent;

/**
 * Represents an activity for making payments via {@link CheckoutActivity}.
 */
public class CheckoutUIActivity extends BasePaymentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLayoutRes(R.layout.activity_checkout_u_i);
        super.onCreate(savedInstanceState);

        if (getIntent().getExtras() != null) {
            String amount = getIntent().getExtras().getString(AppContent.AMOUNT);
            int id = getIntent().getExtras().getInt(AppContent.subscribe_id);
            //Map<String, Object> map = (Map<String, Object>) getIntent().getSerializableExtra("map");
            Log.e(getClass().getName() + " amountString", "" + id);
            totalAmount = amount;
            ((TextView) findViewById(R.id.amount_text_view)).setText(amount + " " + AppContent.Config.CURRENCY);
            findViewById(R.id.button_proceed_to_checkout)
                    .setOnClickListener(v -> requestCheckoutId(id));
        }
    }

    @Override
    public void onCheckoutIdReceived(String checkoutId) {
        super.onCheckoutIdReceived(checkoutId);

        if (checkoutId != null) {
            openCheckoutUI(checkoutId);
        }
    }

    private void openCheckoutUI(String checkoutId) {
        CheckoutSettings checkoutSettings = createCheckoutSettings(checkoutId, getString(R.string.checkout_ui_callback_scheme));

        /* Set componentName if you want to receive callbacks from the checkout */
        ComponentName componentName = new ComponentName(getPackageName(), CheckoutBroadcastReceiver.class.getName());

        /* Set up the Intent and start the checkout activity. */
        Intent intent = checkoutSettings.createCheckoutActivityIntent(this, componentName);

        startActivityForResult(intent, CheckoutActivity.REQUEST_CODE_CHECKOUT);
    }
}
