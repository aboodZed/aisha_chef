package com.webapp.aisha.services.firebase.fun;

import com.webapp.aisha.models.Message;
import com.webapp.aisha.services.firebase.FireBaseReferences;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.Objects;

import static com.webapp.aisha.utils.language.AppLanguageUtil.AR;

public class MessageData {

    public void createNewMessage(int id, Message message, RequestListener<Message> requestListener) {
        message.setSender_id(String.valueOf(AppController.getInstance().getAppSettingsPreferences().getUser().getId()));
        message.setSender_avatar_url(AppController.getInstance().getAppSettingsPreferences().getUser().getAvatar_url());
        message.setTime(System.currentTimeMillis() / 1000);
        if (AppController.getInstance().getAppSettingsPreferences().getAppLanguage().equals(AR)) {
            message.setSender_name(AppController.getInstance().getAppSettingsPreferences().getUser().getNames().get(0).getName());
        } else {
            message.setSender_name(AppController.getInstance().getAppSettingsPreferences().getUser().getNames().get(1).getName());
        }

        FireBaseReferences.getInstance().getChatRef().child(String.valueOf(id)).child(Objects.requireNonNull(FireBaseReferences.getInstance()
                .getChatRef().push().getKey())).setValue(message).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                requestListener.onSuccess(message, "");
            } else {
                requestListener.onFail(task.getException().getLocalizedMessage());
            }
        });
    }
}