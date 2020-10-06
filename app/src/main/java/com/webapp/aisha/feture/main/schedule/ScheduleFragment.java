package com.webapp.aisha.feture.main.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.R;
import com.webapp.aisha.feture.main.schedule.adapter.ScheduleAdapter;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.dialogs.WaitDialogFragment;
import com.webapp.aisha.utils.listener.DialogView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScheduleFragment extends Fragment implements DialogView {

    @BindView(R.id.rv_schedule) RecyclerView rvSchedule;
    @BindView(R.id.btn_save) Button btnSave;

    private SchedulePresenter presenter;
    private ScheduleAdapter adapter;
    private NavigationView view;

    public static ScheduleFragment newInstance(NavigationView navigationView) {
        return new ScheduleFragment(navigationView);
    }

    public ScheduleFragment(NavigationView view) {
        this.view = view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_schedule, container, false);
        ButterKnife.bind(this, v);
        //presenter
        presenter = new SchedulePresenter(getActivity(), view, this);
        initRecycleView(AppController.getInstance().getAppSettingsPreferences().getUser().getWorking_days());
        return v;
    }

    @OnClick(R.id.btn_save)
    public void save() {
        presenter.updateDays(adapter.getSelected());
    }

    private void initRecycleView(ArrayList<String> schedules) {
        adapter = new ScheduleAdapter(schedules, getActivity());
        rvSchedule.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvSchedule.setItemAnimator(new DefaultItemAnimator());
        rvSchedule.setAdapter(adapter);
    }

    @Override
    public void showDialog(String s) {
        WaitDialogFragment.newInstance(s).show(getFragmentManager(), "");
    }

    @Override
    public void hideDialog() {
        WaitDialogFragment.newInstance("").dismiss();
    }
}