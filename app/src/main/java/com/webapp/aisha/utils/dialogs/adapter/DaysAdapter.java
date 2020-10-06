package com.webapp.aisha.utils.dialogs.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.R;
import com.webapp.aisha.utils.dialogs.DayPickerDialog;
import com.webapp.aisha.utils.dialogs.DayPickerDialog.DayListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.DaysHolder> {

    private Activity activity;
    private ArrayList<String> list;
    private DayListener dayListener;
    private DayPickerDialog dayPickerDialog;

    public DaysAdapter(Activity activity, ArrayList<String> list
            , DayListener dayListener, DayPickerDialog dayPickerDialog) {
        this.activity = activity;
        this.list = list;
        this.dayPickerDialog = dayPickerDialog;
        this.dayListener = dayListener;
    }

    @NonNull
    @Override
    public DaysHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DaysHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_schedule, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DaysHolder holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class DaysHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cb_day) CheckBox cbDay;

        private String schedule;

        public DaysHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setData(String schedule) {
            this.schedule = schedule;
            cbDay.setText(schedule);
        }

        @OnClick(R.id.cb_day)
        public void selectDay() {
            dayListener.selectDay(schedule);
            dayPickerDialog.dismiss();
        }
    }
}
