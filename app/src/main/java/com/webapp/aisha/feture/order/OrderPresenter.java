package com.webapp.aisha.feture.order;

import android.app.Activity;
import android.util.Log;

import com.webapp.aisha.R;
import com.webapp.aisha.services.api.ApiShared;
import com.webapp.aisha.models.Order;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.listener.RequestListener;

public class OrderPresenter {

    private Activity activity;
    private NavigationView view;
    private OrderView ordersView;

    public OrderPresenter(Activity activity, NavigationView view, OrderView orderView) {
        this.activity = activity;
        this.view = view;
        this.ordersView = orderView;
    }

    public void getData(int id) {
        ordersView.showDialog(activity.getString(R.string.get_data));
        ApiShared.getInstance().getOrderData().getOrder(activity, id, new RequestListener<Order>() {
            @Override
            public void onSuccess(Order order, String msg) {
                ordersView.setData(order);
                ordersView.hideDialog();
            }

            @Override
            public void onFail(String msg) {
                ToolUtils.showLongToast(msg, activity);
                ordersView.hideDialog();
            }
        });
    }
}
