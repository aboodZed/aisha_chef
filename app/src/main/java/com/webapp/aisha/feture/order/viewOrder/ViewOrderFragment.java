package com.webapp.aisha.feture.order.viewOrder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.R;
import com.webapp.aisha.feture.order.OrderView;
import com.webapp.aisha.feture.order.adapter.ItemsOrderAdapter;
import com.webapp.aisha.models.Order;
import com.webapp.aisha.models.OrderHistory;
import com.webapp.aisha.models.OrderTotal;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.formatter.DecimalFormatterManager;
import com.webapp.aisha.utils.language.AppLanguageUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ViewOrderFragment extends Fragment {

    @BindView(R.id.tv_order_place) TextView tvOrderPlace;
    @BindView(R.id.tv_order_place_time) TextView tvOrderPlaceTime;
    @BindView(R.id.tv_order_preparing) TextView tvOrderPreparing;
    @BindView(R.id.tv_order_preparing_time) TextView tvOrderPreparingTime;
    @BindView(R.id.tv_order_ready) TextView tvOrderReady;
    @BindView(R.id.tv_order_ready_time) TextView tvOrderReadyTime;
    @BindView(R.id.tv_order_on_way) TextView tvOrderOnWay;
    @BindView(R.id.tv_order_on_way_time) TextView tvOrderOnWayTime;
    @BindView(R.id.tv_order_delivered) TextView tvOrderDelivered;
    @BindView(R.id.tv_order_delivered_time) TextView tvOrderDeliveredTime;
    @BindView(R.id.tv_order_id) TextView tvOrderId;
    @BindView(R.id.tv_datetime_create) TextView tvDatetimeCreate;
    @BindView(R.id.tv_datetime_delivery) TextView tvDatetimeDelivery;
    @BindView(R.id.tv_receiver) TextView tvReceiver;
    @BindView(R.id.tv_receiver_address) TextView tvReceiverAddress;
    @BindView(R.id.rv_order_item) RecyclerView rvOrderItem;
    @BindView(R.id.tv_sub_total) TextView tvSubTotal;
    @BindView(R.id.tv_delivery) TextView tvDelivery;
    @BindView(R.id.tv_taxes) TextView tvTaxes;
    @BindView(R.id.tv_app_proportion) TextView tvAppProportion;
    @BindView(R.id.tv_total) TextView tvTotal;
    @BindView(R.id.tv_payment_method) TextView tvPaymentMethod;
    @BindView(R.id.iv_client_avatar) CircleImageView ivClientAvatar;
    @BindView(R.id.tv_client_name) TextView tvClientName;
    @BindView(R.id.tv_client_address) TextView tvClientAddress;
    @BindView(R.id.iv_open_chat) ImageView ivOpenChat;
    @BindView(R.id.btn_ready) Button btnReady;
    @BindView(R.id.btn_on_way) Button btnOnWay;

    private ViewOrderPresenter presenter;
    private NavigationView view;
    private OrderView orderView;
    private ItemsOrderAdapter adapter;
    private static final String ORDER = "order";
    private Order order;

    public static ViewOrderFragment newInstance(NavigationView navigationView, OrderView orderView, Order order) {
        ViewOrderFragment fragment = new ViewOrderFragment(navigationView, orderView);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ORDER, order);
        fragment.setArguments(bundle);
        return fragment;
    }

    public ViewOrderFragment(NavigationView view, OrderView orderView) {
        this.view = view;
        this.orderView = orderView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_order, container, false);
        ButterKnife.bind(this, v);
        if (getArguments() != null) {
            order = (Order) getArguments().getSerializable(ORDER);
            presenter = new ViewOrderPresenter(getActivity(), view, orderView);
            setData();
            initRecycleView();
        }
        return v;
    }

    public void setData() {
        double subtotal = 0;
        tvOrderId.setText("Order#" + order.getId());
        tvDatetimeCreate.setText(ToolUtils.getTime(order.getCreated_at())
                + "  " + ToolUtils.getDate(order.getCreated_at()));
        tvDatetimeDelivery.setText(order.getTime_hour().substring(0, order
                .getTime_hour().length() - 2) + "  " + order.getTime_day());
        if (AppController.getInstance().getAppSettingsPreferences()
                .getAppLanguage().equals(AppLanguageUtil.AR)) {
            tvReceiver.setText(order.getUser().getName());
            tvClientName.setText(order.getUser().getName());
            tvPaymentMethod.setText(order.getPayment_method().getLabel());
        } else {
            tvReceiver.setText(order.getUser().getName());
            tvClientName.setText(order.getUser().getName());
            tvPaymentMethod.setText(order.getPayment_method().getCode());
        }
        tvReceiverAddress.setText(order.getAddress().getName());
        for (OrderTotal orderTotal : order.getTotals()) {
            switch (orderTotal.getCode()) {
                case AppContent.SUB_TOTAL:
                    tvSubTotal.setText(DecimalFormatterManager.getFormatterInstance()
                            .format(orderTotal.getValue()) + getString(R.string.currency));
                    subtotal = orderTotal.getValue();
                    break;
                case AppContent.TAX:
                    tvTaxes.setText(DecimalFormatterManager.getFormatterInstance()
                            .format(orderTotal.getValue()) + getString(R.string.currency));
                    break;
                case AppContent.DELIVERY:
                    tvDelivery.setText(DecimalFormatterManager.getFormatterInstance()
                            .format(orderTotal.getValue()) + getString(R.string.currency));
                    break;
                case AppContent.TOTAL:
                    tvTotal.setText(DecimalFormatterManager.getFormatterInstance()
                            .format(orderTotal.getValue()) + getString(R.string.currency));
                    break;
                case AppContent.SYS_COMMISSION:
                    tvAppProportion.setText(DecimalFormatterManager.getFormatterInstance()
                            .format(orderTotal.getValue()) + getString(R.string.currency));
                    break;
            }
        }
        tvClientAddress.setText(order.getUser().getCity().getName());

        for (OrderHistory orderHistory : order.getHistories()) {
            switch (orderHistory.getStatus().getCode()) {
                case AppContent.STATUS_PLACE:
                    tvOrderPlace.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_order_placed_c, 0, 0);
                    tvOrderPlace.setTextColor(getActivity().getColor(R.color.colorPrimaryDark));
                    tvOrderPlaceTime.setText(ToolUtils.getTime(orderHistory.getCreate_at()));
                    break;
                case AppContent.STATUS_PREPARING:
                    tvOrderPreparing.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_order_preparing_c, 0, 0);
                    tvOrderPreparing.setTextColor(getActivity().getColor(R.color.colorPrimaryDark));
                    tvOrderPreparingTime.setText(ToolUtils.getTime(orderHistory.getCreate_at()));
                    btnOnWay.setVisibility(View.INVISIBLE);
                    break;
                case AppContent.STATUS_READY:
                    tvOrderReady.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_order_ready_c, 0, 0);
                    tvOrderReady.setTextColor(getActivity().getColor(R.color.colorPrimaryDark));
                    tvOrderReadyTime.setText(ToolUtils.getTime(orderHistory.getCreate_at()));
                    btnReady.setVisibility(View.INVISIBLE);
                    btnOnWay.setVisibility(View.VISIBLE);
                    break;
                case AppContent.STATUS_ON_WAY:
                    tvOrderOnWay.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_order_on_way_c, 0, 0);
                    tvOrderOnWay.setTextColor(getActivity().getColor(R.color.colorPrimaryDark));
                    tvOrderOnWayTime.setText(ToolUtils.getTime(orderHistory.getCreate_at()));
                    btnOnWay.setVisibility(View.INVISIBLE);
                    break;
                case AppContent.STATUS_DELIVERED:
                    tvOrderDelivered.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_order_delivered_c, 0, 0);
                    tvOrderDelivered.setTextColor(getActivity().getColor(R.color.colorPrimaryDark));
                    tvOrderDeliveredTime.setText(ToolUtils.getTime(orderHistory.getCreate_at()));
                    break;
            }
        }
        ToolUtils.loadImageNormal(getContext(), null, order.getUser().getAvatar_url(), ivClientAvatar);
    }

    private void initRecycleView() {
        if (order.getOffer().getId() != 0) {
            order.getMeals().add(order.getOffer());
        }
        adapter = new ItemsOrderAdapter(order.getMeals(), getActivity());
        rvOrderItem.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvOrderItem.setItemAnimator(new DefaultItemAnimator());
        rvOrderItem.setAdapter(adapter);
    }

    @OnClick(R.id.iv_open_chat)
    public void chat() {
        adapter.clear();
        view.navigate(AppContent.chat);
    }

    @OnClick(R.id.btn_ready)
    public void ready() {
        presenter.setOrderReady(order);
    }

    @OnClick(R.id.btn_on_way)
    public void onTheWay() {
        presenter.setOrderOnTheWay(order);
    }
}