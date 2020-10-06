package com.webapp.aisha.feture.main.schedule.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.R;
import com.webapp.aisha.models.DayTime;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DayTimeAdapter extends RecyclerView.Adapter<DayTimeAdapter.DayTimeHolder> {

    private Activity activity;
    private ArrayList<DayTime> dayTimes;

    public DayTimeAdapter(Activity activity, ArrayList<DayTime> dayTimes) {
        this.activity = activity;
        this.dayTimes = dayTimes;
    }

    @NonNull
    @Override
    public DayTimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DayTimeHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_daytime, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DayTimeHolder holder, int position) {
        holder.setData(dayTimes.get(position));
    }

    @Override
    public int getItemCount() {
        return dayTimes.size();
    }

    public ArrayList<DayTime> getDayTimes() {
        return dayTimes;
    }

    public void adItem(DayTime s) {
        dayTimes.add(s);
        notifyItemInserted(getItemCount() - 1);
    }

    public void removeItem(DayTime s) {
        int i = dayTimes.indexOf(s);
        dayTimes.remove(s);
        notifyItemRemoved(i);
    }

    class DayTimeHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_daytime) TextView tvDaytime;
        @BindView(R.id.iv_delete) ImageView ivDelete;

        private DayTime dayTime;

        public DayTimeHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setData(DayTime dayTime) {
            this.dayTime = dayTime;
            tvDaytime.setText(dayTime.getDay());
        }

        @OnClick(R.id.iv_delete)
        public void delete() {
            removeItem(dayTime);
        }
    }
}
