package com.webapp.aisha.feture.main.notification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.R;
import com.webapp.aisha.feture.main.notification.adapter.NotificationAdapter;
import com.webapp.aisha.models.Notification;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationFragment extends Fragment implements NotificationView {

    @BindView(R.id.rv_notification) RecyclerView rvNotification;
    @BindView(R.id.fl_load_more) FrameLayout flLoadMore;

    private NotificationPresenter presenter;
    private NotificationAdapter adapter;
    private NavigationView view;

    public static NotificationFragment newInstance(NavigationView navigationView) {
        return new NotificationFragment(navigationView);
    }

    public NotificationFragment(NavigationView view) {
        this.view = view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.bind(this, v);
        //presenter
        initRecycleView();
        presenter = new NotificationPresenter(getActivity(), view, this);
        return v;
    }

    private void initRecycleView() {
        adapter = new NotificationAdapter(getActivity());
        rvNotification.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvNotification.setItemAnimator(new DefaultItemAnimator());
        rvNotification.setAdapter(adapter);
        rvNotification.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                presenter.onScrollStateChanged(recyclerView);
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    @Override
    public void setData(ArrayList<Notification> data) {
        adapter.addItems(data);
    }

    @Override
    public void showDialog(String s) {
        flLoadMore.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDialog() {
        flLoadMore.setVisibility(View.GONE);
    }
}