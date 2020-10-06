package com.webapp.aisha.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.webapp.aisha.R;
import com.webapp.aisha.feture.main.MainActivity;

import org.json.JSONObject;

public class NotificationUtil {

    public static void sendNotification(Context context, JSONObject message) {
        try {
            //create notification
            Notification.Builder builder = new Notification.Builder(context);
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            builder.setContentTitle(context.getString(R.string.app_name))
                    .setStyle(new Notification.BigTextStyle().bigText(message.getString("click_action")))
                    .setAutoCancel(true)
                    .setSmallIcon(R.mipmap.ic_launcher);
            final NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

           //create channel
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String channelID = "orderChannel"; // In case you don't add a channel android oreo will not create the notification and it will give you a log error
                NotificationChannel channel = new NotificationChannel(channelID, context.getString(R.string.app_name),
                        NotificationManager.IMPORTANCE_DEFAULT);
                channel.setLightColor(Color.GREEN);
                notificationManager.createNotificationChannel(channel);
                builder.setChannelId(channelID);
            }

            //notification build
            Notification notification = builder.build();
            notification.defaults = Notification.FLAG_ONLY_ALERT_ONCE;
            notification.defaults = Notification.FLAG_INSISTENT | Notification.FLAG_AUTO_CANCEL;
            notification.defaults = Notification.FLAG_AUTO_CANCEL;
            notification.sound = alarmSound;

            //create intent
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("massage", message.toString());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            int iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);
            notification.contentIntent = PendingIntent.getActivity(context.getApplicationContext()
                    , iUniqueId, intent, PendingIntent.FLAG_ONE_SHOT);

            //show notification
            notificationManager.notify(iUniqueId, notification);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("notification", e.getLocalizedMessage());
        }
    }
}