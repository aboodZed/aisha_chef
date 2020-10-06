package com.webapp.aisha.feture.main.schedule.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.R;
import com.webapp.aisha.utils.AppContent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleHolder> {

    ArrayList<String> schedules = new ArrayList<>();
    ArrayList<String> selected;
    Activity activity;

    public ScheduleAdapter(ArrayList<String> selected, Activity activity) {
        this.selected = selected;
        this.activity = activity;
        schedules.add(AppContent.sat);
        schedules.add(AppContent.sun);
        schedules.add(AppContent.mon);
        schedules.add(AppContent.tue);
        schedules.add(AppContent.wed);
        schedules.add(AppContent.thu);
        schedules.add(AppContent.fri);
    }

    @NonNull
    @Override
    public ScheduleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScheduleHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_schedule, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleHolder holder, int position) {
        holder.setData(schedules.get(position));
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public ArrayList<String> getSelected() {
        return selected;
    }

    class ScheduleHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cb_day) CheckBox cbDay;

        private String schedule;

        public ScheduleHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(String schedule) {
            this.schedule = schedule;
            switch (schedule) {
                case AppContent.sat:
                    cbDay.setText(activity.getString(R.string.sat));
                    break;
                case AppContent.sun:
                    cbDay.setText(activity.getString(R.string.sun));
                    break;
                case AppContent.mon:
                    cbDay.setText(activity.getString(R.string.mon));
                    break;
                case AppContent.tue:
                    cbDay.setText(activity.getString(R.string.tue));
                    break;
                case AppContent.wed:
                    cbDay.setText(activity.getString(R.string.wed));
                    break;
                case AppContent.thu:
                    cbDay.setText(activity.getString(R.string.thu));
                    break;
                case AppContent.fri:
                    cbDay.setText(activity.getString(R.string.fri));
                    break;
            }
            for (String schedule1 : selected) {
                if (schedule1.equals(schedule)) {
                    cbDay.setChecked(true);
                }
            }
        }

        @OnClick(R.id.cb_day)
        public void selectDay() {
            if (cbDay.isChecked()) {
                selected.add(schedule);
            } else {
                selected.remove(schedule);
            }
        }
    }
}
