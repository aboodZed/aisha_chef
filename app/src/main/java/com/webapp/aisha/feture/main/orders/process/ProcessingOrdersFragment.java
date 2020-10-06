package com.webapp.aisha.feture.main.orders.process;

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
import com.webapp.aisha.feture.main.orders.OrdersFragment;
import com.webapp.aisha.feture.main.orders.OrdersView;
import com.webapp.aisha.feture.main.orders.adapter.OrdersAdapter;
import com.webapp.aisha.models.Order;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProcessingOrdersFragment extends Fragment implements OrdersView {

    @BindView(R.id.rv_orders) RecyclerView rvOrders;
    @BindView(R.id.fl_load_more) FrameLayout flLoadMore;

    private OrdersAdapter adapter;
    private ProcessingOrdersPresenter presenter;
    private NavigationView view;

    public static ProcessingOrdersFragment newInstance(NavigationView navigationView) {
        return new ProcessingOrdersFragment(navigationView);
    }

    public ProcessingOrdersFragment(NavigationView view) {
        this.view = view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_processing_orders, container, false);
        ButterKnife.bind(this, v);
        presenter = new ProcessingOrdersPresenter(getActivity(), view, this);
        initRecycleView();
        return v;
    }

    private void initRecycleView() {
        adapter = new OrdersAdapter(getActivity());
        rvOrders.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvOrders.setItemAnimator(new DefaultItemAnimator());
        rvOrders.setAdapter(adapter);
        rvOrders.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                presenter.onScrollStateChanged(recyclerView);
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    @Override
    public void setData(ArrayList<Order> data) {
        adapter.adItems(data);
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