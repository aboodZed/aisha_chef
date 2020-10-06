package com.webapp.aisha.feture.main.notification;

import android.app.Activity;

import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.services.api.ApiShared;
import com.webapp.aisha.models.Notification;
import com.webapp.aisha.models.Result;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.ArrayList;

public class NotificationPresenter {

    private Activity activity;
    private NavigationView view;
    private NotificationView notificationView;
    private String next_page_url = "agents/notifications";
    private boolean loadingMoreItems;

    public NotificationPresenter(Activity activity, NavigationView view, NotificationView notificationView) {
        this.activity = activity;
        this.view = view;
        this.notificationView = notificationView;
        getData();
    }

    private void getData() {
        notificationView.showDialog("");
        ApiShared.getInstance().getNotificationData().getNotification(activity, next_page_url,
                new RequestListener<Result<ArrayList<Notification>>>() {
                    @Override
                    public void onSuccess(Result<ArrayList<Notification>> arrayListResult, String msg) {
                        next_page_url = arrayListResult.getPagination().getNext_page_url();
                        notificationView.setData(arrayListResult.getData());
                        notificationView.hideDialog();
                        loadingMoreItems = false;
                    }

                    @Override
                    public void onFail(String msg) {
                        ToolUtils.showLongToast(msg, activity);
                        notificationView.hideDialog();
                        loadingMoreItems = false;
                    }
                });
    }

    public void onScrollStateChanged(RecyclerView recyclerView) {
        if (!recyclerView.canScrollVertically(1) && next_page_url != null && !loadingMoreItems) {
            loadingMoreItems = true;
            getData();
        }
    }
}
