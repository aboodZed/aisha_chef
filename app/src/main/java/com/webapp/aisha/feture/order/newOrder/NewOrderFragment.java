package com.webapp.aisha.feture.order.newOrder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.R;
import com.webapp.aisha.feture.order.OrderView;
import com.webapp.aisha.feture.order.adapter.ItemsOrderAdapter;
import com.webapp.aisha.models.Order;
import com.webapp.aisha.models.OrderTotal;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.dialogs.WaitDialogFragment;
import com.webapp.aisha.utils.formatter.DecimalFormatterManager;
import com.webapp.aisha.utils.language.AppLanguageUtil;
import com.webapp.aisha.utils.listener.DialogView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewOrderFragment extends Fragment implements DialogView {

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
    @BindView(R.id.btn_accept) Button btnAccept;

    private NewOrderPresenter presenter;
    private NavigationView view;
    private OrderView orderView;
    private ItemsOrderAdapter adapter;
    private static final String ORDER = "order";
    private Order order;

    public static NewOrderFragment newInstance(NavigationView navigationView, OrderView orderView, Order order) {
        NewOrderFragment fragment = new NewOrderFragment(navigationView, orderView);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ORDER, order);
        fragment.setArguments(bundle);
        return fragment;
    }

    public NewOrderFragment(NavigationView view, OrderView orderView) {
        this.view = view;
        this.orderView = orderView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_order, container, false);
        ButterKnife.bind(this, v);
        if (getArguments() != null) {
            order = (Order) getArguments().getSerializable(ORDER);
            presenter = new NewOrderPresenter(getActivity(), view, orderView);
            setData();
            initRecycleView();
        }
        return v;
    }

    private void setData() {
        tvOrderId.setText("Order#" + order.getId());
        tvDatetimeCreate.setText(ToolUtils.getTime(order.getCreated_at())
                + "  " + ToolUtils.getDate(order.getCreated_at()));
        tvDatetimeDelivery.setText(order.getTime_hour().substring(0, order
                .getTime_hour().length() - 2) + "  " + order.getTime_day());
        if (AppController.getInstance().getAppSettingsPreferences().getAppLanguage().equals(AppLanguageUtil.AR)) {
            tvReceiver.setText(order.getUser().getName());
            tvPaymentMethod.setText(order.getPayment_method().getLabel());
        } else {
            tvReceiver.setText(order.getUser().getName());
            tvPaymentMethod.setText(order.getPayment_method().getCode());
        }
        tvReceiverAddress.setText(order.getAddress().getName());
        for (OrderTotal orderTotal : order.getTotals()) {
            switch (orderTotal.getCode()) {
                case AppContent.SUB_TOTAL:
                    tvSubTotal.setText(DecimalFormatterManager.getFormatterInstance()
                            .format(orderTotal.getValue()) + getString(R.string.currency));
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

    @OnClick(R.id.btn_accept)
    public void accept() {
        presenter.setOrderReady(order);
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