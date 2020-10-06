package com.webapp.aisha.feture.main.notification;

import com.webapp.aisha.models.Notification;
import com.webapp.aisha.utils.listener.DialogView;

import java.util.ArrayList;

interface NotificationView extends DialogView {
    void setData(ArrayList<Notification> data);
}
