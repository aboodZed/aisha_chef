package com.webapp.aisha.feture.order.viewOrder;

import android.app.Activity;
import android.widget.TextView;

import com.webapp.aisha.R;
import com.webapp.aisha.feture.order.OrderView;
import com.webapp.aisha.models.Config;
import com.webapp.aisha.models.Order;
import com.webapp.aisha.services.api.ApiShared;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.formatter.DecimalFormatterManager;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.ArrayList;

public class ViewOrderPresenter {

    private Activity activity;
    private NavigationView view;
    private OrderView orderView;

    public ViewOrderPresenter(Activity activity, NavigationView view, OrderView orderView) {
        this.activity = activity;
        this.view = view;
        this.orderView = orderView;
    }

    public void setOrderReady(Order order) {
        orderView.showDialog(activity.getString(R.string.order_ready));
        ApiShared.getInstance().getOrderData().setReady(activity, order.getId(),
                new RequestListener<Order>() {
                    @Override
                    public void onSuccess(Order order, String msg) {
                        orderView.hideDialog();
                        orderView.setData(order);
                    }

                    @Override
                    public void onFail(String msg) {
                        ToolUtils.showLongToast(msg, activity);
                        orderView.hideDialog();
                    }
                });
    }

    public void setOrderOnTheWay(Order order) {
        orderView.showDialog(activity.getString(R.string.order_on_way));
        ApiShared.getInstance().getOrderData().setOnWay(activity, order.getId(),
                new RequestListener<Order>() {
                    @Override
                    public void onSuccess(Order order, String msg) {
                        orderView.hideDialog();
                        orderView.setData(order);
                    }

                    @Override
                    public void onFail(String msg) {
                        ToolUtils.showLongToast(msg, activity);
                        orderView.hideDialog();
                    }
                });
    }
}
