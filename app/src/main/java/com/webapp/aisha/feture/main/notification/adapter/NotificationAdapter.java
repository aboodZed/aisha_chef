package com.webapp.aisha.feture.main.notification.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.R;
import com.webapp.aisha.models.Notification;
import com.webapp.aisha.services.api.ApiShared;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.NavigateUtils;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder> {

    private ArrayList<Notification> notifications = new ArrayList<>();
    private Activity activity;

    public NotificationAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        holder.setData(notifications.get(position), position);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public void addItems(ArrayList<Notification> data) {
        notifications.addAll(data);
        notifyDataSetChanged();
    }

    class NotificationHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cl_background) ConstraintLayout clBackground;
        @BindView(R.id.tv_notification_name) TextView tvNotificationName;
        @BindView(R.id.iv_read_it) ImageView ivReadIt;
        @BindView(R.id.tv_notification_date) TextView tvNotificationDate;
        @BindView(R.id.tv_notification_details) TextView tvNotificationDetails;

        private Notification notification;
        private int position;

        public NotificationHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.cl_background)
        public void setRead() {
            navigate();
        }

        private void navigate() {
            if (notification.getClick_action().equals(AppContent.STATUS_NEW_ORDER)) {
                NavigateUtils.openOrder(activity, AppContent.view_order, notification.getClick_id(), true);
            }
        }

        private void setData(Notification notification, int position) {
            this.notification = notification;
            this.position = position;

            tvNotificationName.setText(notification.getTitle());
            tvNotificationDetails.setText(notification.getContent());
            tvNotificationDate.setText(ToolUtils.getTime(notification.getCreate_at())
                    + "  " + ToolUtils.getDate(notification.getCreate_at()));
            if (!notification.isIs_read()) {
                ivReadIt.setVisibility(View.VISIBLE);
            }
        }
    }
}
