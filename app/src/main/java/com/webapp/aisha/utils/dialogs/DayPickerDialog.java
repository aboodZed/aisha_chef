package com.webapp.aisha.utils.dialogs;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.R;
import com.webapp.aisha.models.DayTime;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.dialogs.adapter.DaysAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DayPickerDialog extends DialogFragment {

    @BindView(R.id.iv_delete) ImageView ivDelete;
    @BindView(R.id.rv_schedule) RecyclerView rvSchedule;

    private DayListener dayListener;
    private ArrayList<DayTime> dayTimes;

    public static DayPickerDialog newInstance(ArrayList<DayTime> arrayList, DayListener dayListener) {
        return new DayPickerDialog(dayListener, arrayList);
    }

    public DayPickerDialog(DayListener dayListener, ArrayList<DayTime> dayTimes) {
        this.dayListener = dayListener;
        this.dayTimes = dayTimes;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_day_picker_dialog, container, false);
        ButterKnife.bind(this, v);
        setData();
        return v;
    }

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.8);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.transparent);
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        setCancelable(false);
        super.onResume();
        getDialog().setOnKeyListener((dialog, keyCode, event) -> {
            if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                this.dismiss();
                return true;
            } else return false;
        });
    }

    private void setData() {
        ArrayList<String> list = new ArrayList<>();
        for (String schedule : AppController.getInstance().getAppSettingsPreferences().getUser().getWorking_days()) {
            list.add(schedule);

            for (DayTime dayTime : dayTimes) {
                if (schedule.equalsIgnoreCase(dayTime.getDay())) {
                    list.remove(schedule);
                }
            }
        }

        DaysAdapter daysAdapter = new DaysAdapter(getActivity(), list, dayListener, this);
        rvSchedule.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSchedule.setItemAnimator(new DefaultItemAnimator());
        rvSchedule.setAdapter(daysAdapter);
    }

    @OnClick(R.id.iv_delete)
    public void close() {
        this.dismiss();
    }

    public interface DayListener {
        void selectDay(String schedule);
    }
}