package com.webapp.aisha.services.firebase.fmc;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.webapp.aisha.feture.main.MainActivity;
import com.webapp.aisha.feture.main.orders.OrdersFragment;
import com.webapp.aisha.feture.offers.OfferActivity;
import com.webapp.aisha.feture.order.chat.ChatFragment;
import com.webapp.aisha.feture.profile.ProfileActivity;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.NavigateUtils;
import com.webapp.aisha.utils.NotificationUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class MyFirebaseNotificationService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e("remoteMessage", remoteMessage.getData().toString());
        spiltMessage(this, remoteMessage.getData());
    }

    public static void spiltMessage(Context context, Map<String, String> map) {
        try {
            JSONObject mapJSON = new JSONObject(map);
            String clickAction = mapJSON.getString("click_action");
            int clickId;

            switch (clickAction) {
                case AppContent.STATUS_NEW_ORDER:
                    clickId = mapJSON.getInt("click_id");
                    NavigateUtils.openOrder(context, AppContent.view_order, clickId, false);
                    NotificationUtil.sendNotification(context, mapJSON);
                    break;
                case AppContent.STATUS_ORDER_CHANGED:
                    OrdersFragment.page = 0;
                    NavigateUtils.fromActivityToActivityWithPage(context, MainActivity.class, true,
                            AppContent.MAIN_PAGE, AppContent.orders);
                    NotificationUtil.sendNotification(context, mapJSON);
                    break;
                case AppContent.STATUS_MESSAGE:
                    if (!ChatFragment.isChatOpen) {
                        clickId = mapJSON.getInt("order_id");
                        NavigateUtils.openOrder(context, AppContent.chat, clickId, false);
                    }
                    break;
                case AppContent.STATUS_CONTACT_US:
                    NavigateUtils.fromActivityToActivityWithPage(context, ProfileActivity.class
                            , true, AppContent.PROFILE_PAGE, AppContent.contact_us);
                    NotificationUtil.sendNotification(context, mapJSON);
                    break;
                case AppContent.STATUS_DASHBOARD:
                    NotificationUtil.sendNotification(context, mapJSON);
                    break;
                case AppContent.STATUS_OFFER_ACCEPT:
                case AppContent.STATUS_OFFER_REJECT:
                    NavigateUtils.fromActivityToActivityWithPage(context, OfferActivity.class
                            , true, AppContent.OFFER_PAGE, AppContent.offers);
                    NotificationUtil.sendNotification(context, mapJSON);
                    break;
                case AppContent.STATUS_WITHDRAW_ACCEPT:
                case AppContent.STATUS_WITHDRAW_REJECT:
                case AppContent.STATUS_BANK_TRANSFER_ACCEPT:
                case AppContent.STATUS_BANK_TRANSFER_REJECT:
                    NavigateUtils.fromActivityToActivityWithPage(context, MainActivity.class
                            , true, AppContent.MAIN_PAGE, AppContent.wallet);
                    NotificationUtil.sendNotification(context, mapJSON);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("error", "" + e.getMessage());
        }
    }
}
