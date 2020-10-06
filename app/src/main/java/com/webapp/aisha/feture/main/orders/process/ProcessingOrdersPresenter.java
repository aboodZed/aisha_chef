package com.webapp.aisha.feture.main.orders.process;

import android.app.Activity;

import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.feture.main.orders.OrdersView;
import com.webapp.aisha.services.api.ApiShared;
import com.webapp.aisha.models.Order;
import com.webapp.aisha.models.Result;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.ArrayList;

public class ProcessingOrdersPresenter {

    private Activity activity;
    private NavigationView view;
    private OrdersView ordersView;
    private String next_page_url = "agents/orders?status=processing";
    private boolean loadingMoreItems;

    public ProcessingOrdersPresenter(Activity activity, NavigationView view, OrdersView ordersView) {
        this.activity = activity;
        this.view = view;
        this.ordersView = ordersView;
        getData();
    }


    private void getData() {
        ordersView.showDialog("");
        ApiShared.getInstance().getOrderData().getOrders(activity, next_page_url
                , new RequestListener<Result<ArrayList<Order>>>() {
            @Override
            public void onSuccess(Result<ArrayList<Order>> orders, String msg) {
                next_page_url = orders.getPagination().getNext_page_url();
                ordersView.setData(orders.getData());
                ordersView.hideDialog();
                loadingMoreItems = false;
            }

            @Override
            public void onFail(String msg) {
                ToolUtils.showLongToast(msg, activity);
                ordersView.hideDialog();
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
