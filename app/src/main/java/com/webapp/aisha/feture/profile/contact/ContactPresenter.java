package com.webapp.aisha.feture.profile.contact;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.core.app.ActivityCompat;

import com.webapp.aisha.R;
import com.webapp.aisha.services.api.ApiShared;
import com.webapp.aisha.models.Config;
import com.webapp.aisha.models.Contact;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.PermissionUtil;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.webapp.aisha.utils.AppContent.REQUEST_PHONE_CALL_CODE;

public class ContactPresenter {

    private Activity activity;
    private ContactView contactView;

    public ContactPresenter(Activity activity, ContactView contactView) {
        this.activity = activity;
        this.contactView = contactView;
        getData();
    }

    private void getData() {
        contactView.showDialog(activity.getString(R.string.get_data));
        ApiShared.getInstance().getGetData().getConfig(activity,
                new RequestListener<ArrayList<Config>>() {
                    @Override
                    public void onSuccess(ArrayList<Config> configs, String msg) {
                        contactView.setData(configs);
                        contactView.hideDialog();
                    }

                    @Override
                    public void onFail(String msg) {
                        ToolUtils.showLongToast(msg, activity);
                        contactView.hideDialog();
                        activity.onBackPressed();
                    }
                });
    }

    public void sendMessage(EditText etSubject, EditText etMessage) {

        String subject = etSubject.getText().toString().trim();
        String message = etMessage.getText().toString().trim();

        if (TextUtils.isEmpty(subject)) {
            etSubject.setError(activity.getString(R.string.required_field));
            return;
        }

        if (TextUtils.isEmpty(message)) {
            etMessage.setError(activity.getString(R.string.required_field));
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("app", "agent");
        map.put("title", subject);
        map.put("message", message);

        send(map, etSubject, etMessage);
    }

    private void send(Map<String, String> map
            , EditText etSubject
            , EditText etMessage) {

        contactView.showDialog(activity.getString(R.string.send));
        ApiShared.getInstance().getNotificationData().contact(activity, map,
                new RequestListener<Contact>() {
                    @Override
                    public void onSuccess(Contact contact, String msg) {
                        ToolUtils.showLongToast(activity.getString(R.string.send_success), activity);
                        etSubject.setText("");
                        etMessage.setText("");
                        contactView.hideDialog();
                    }

                    @Override
                    public void onFail(String msg) {
                        ToolUtils.showLongToast(msg, activity);
                        contactView.hideDialog();
                    }
                });
    }

    public void call(String mobile) {
        if (!PermissionUtil.isPermissionGranted(Manifest.permission.CALL_PHONE, activity)) {
            PermissionUtil.requestPermission(activity, Manifest.permission.CALL_PHONE, REQUEST_PHONE_CALL_CODE);
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + mobile));
            activity.startActivity(intent);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case AppContent.REQUEST_PHONE_CALL_CODE:
                    ToolUtils.showLongToast(activity.getString(R.string.permission_granted), activity);
                    break;
            }
        } else {
            ToolUtils.showLongToast(activity.getString(R.string.permission_denial), activity);
        }
    }
}
