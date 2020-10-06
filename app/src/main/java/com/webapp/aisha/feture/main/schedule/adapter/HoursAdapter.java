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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HoursAdapter extends RecyclerView.Adapter<HoursAdapter.HourHolder> {

    private Activity activity;
    private ArrayList<String> hours;

    public HoursAdapter(Activity activity, ArrayList<String> hours) {
        this.activity = activity;
        this.hours = hours;
    }

    @NonNull
    @Override
    public HourHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HourHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_daytime, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HourHolder holder, int position) {
        holder.setData(hours.get(position));
    }

    @Override
    public int getItemCount() {
        return hours.size();
    }

    public ArrayList<String> getHours() {
        return hours;
    }

    public void addItem(String s) {
        hours.add(s);
        notifyItemInserted(getItemCount() - 1);
    }

    public void removeItem(String s) {
        int i = hours.indexOf(s);
        hours.remove(s);
        notifyItemRemoved(i);
    }

    class HourHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_daytime) TextView tvDaytime;
        @BindView(R.id.iv_delete) ImageView ivDelete;

        private String hour;

        public HourHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setData(String s) {
            this.hour = s;
            tvDaytime.setText(s);
        }

        @OnClick(R.id.iv_delete)
        public void delete() {
            removeItem(hour);
        }
    }
}
