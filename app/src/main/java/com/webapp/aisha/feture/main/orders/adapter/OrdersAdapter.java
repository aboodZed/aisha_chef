package com.webapp.aisha.feture.main.orders.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.R;
import com.webapp.aisha.feture.main.orders.OrdersFragment;
import com.webapp.aisha.models.Order;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.NavigateUtils;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.formatter.DecimalFormatterManager;
import com.webapp.aisha.utils.language.AppLanguageUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.FinishedOrdersHolder> {

    private ArrayList<Order> orders = new ArrayList<>();
    private Activity activity;

    public OrdersAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public FinishedOrdersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FinishedOrdersHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_finish_orders, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FinishedOrdersHolder holder, int position) {
        holder.setData(orders.get(position));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void adItems(ArrayList<Order> list) {
        orders.addAll(list);
        notifyDataSetChanged();
    }

    class FinishedOrdersHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cl_background) ConstraintLayout clBackground;
        @BindView(R.id.tv_order_id) TextView tvOrderId;
        @BindView(R.id.tv_date) TextView tvDate;
        @BindView(R.id.tv_datetime_delivery) TextView tvDatetimeDelivery;
        @BindView(R.id.tv_status) TextView tvStatus;
        @BindView(R.id.tv_payment) TextView tvPayment;
        @BindView(R.id.tv_qnt) TextView tvQnt;
        @BindView(R.id.tv_price) TextView tvPrice;
        @BindView(R.id.tv_customer_name) TextView tvCustomerName;
        @BindView(R.id.tv_customer_address) TextView tvCustomerAddress;

        private Order order;

        public FinishedOrdersHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setData(Order order) {
            this.order = order;
            tvOrderId.setText("Order#" + order.getId());
            tvDate.setText(ToolUtils.getTime(order.getCreated_at())
                    + "  " + ToolUtils.getDate(order.getCreated_at()));
            tvDatetimeDelivery.setText(order.getTime_hour().substring(0, order
                    .getTime_hour().length() - 2) + "  " + order.getTime_day());
            if (AppController.getInstance().getAppSettingsPreferences().getAppLanguage().equals(AppLanguageUtil.AR)) {
                tvStatus.setText(order.getStatus().getLabel());
                tvPayment.setText(order.getPayment_method().getLabel());
            } else {
                tvStatus.setText(order.getStatus().getCode());
                tvPayment.setText(order.getPayment_method().getCode());
            }
            tvCustomerName.setText(order.getUser().getName());
            tvQnt.setText(String.valueOf(order.getQuantity()));
            tvPrice.setText(DecimalFormatterManager.getFormatterInstance().format(order.getTotal()));
            tvCustomerAddress.setText(order.getUser().getCity().getName());
        }

        @OnClick(R.id.cl_background)
        public void openOrder() {
            NavigateUtils.openOrder(activity, AppContent.view_order, order.getId(), false);
        }
    }
}
