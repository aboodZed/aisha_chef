package com.webapp.aisha.services.firebase.fun;

import android.content.Context;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.webapp.aisha.utils.listener.RequestListener;

public class TokenData {

    public void generateFCMToken(Context context, RequestListener<String> requestListener) {
        FirebaseApp.initializeApp(context);
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                requestListener.onFail(task.getException().getLocalizedMessage());
                Log.e("Main", "getInstanceId failed", task.getException());
                return;
            } else {
                String token = task.getResult().getToken();
                Log.e("fcmtoken", "" + token);
                requestListener.onSuccess(token, "");
            }
        });
    }
}
