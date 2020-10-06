package com.webapp.aisha.feture.order.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.R;
import com.webapp.aisha.models.MealExtra;
import com.webapp.aisha.models.OrderItem;
import com.webapp.aisha.utils.formatter.DecimalFormatterManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemsOrderAdapter extends RecyclerView.Adapter<ItemsOrderAdapter.ItemOrderHolder> {

    private ArrayList<OrderItem> orderItems = new ArrayList<>();
    private Activity activity;

    public ItemsOrderAdapter(ArrayList<OrderItem> orderItems, Activity activity) {
        this.orderItems = orderItems;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ItemOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemOrderHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemOrderHolder holder, int position) {
        holder.setData(orderItems.get(position));
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public void clear() {
        this.orderItems.clear();
        notifyDataSetChanged();
    }

    class ItemOrderHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_item_name) TextView tvItemName;
        @BindView(R.id.tv_item_describe) TextView tvItemDescribe;
        @BindView(R.id.tv_item_qnt) TextView tvItemQnt;
        @BindView(R.id.tv_item_price) TextView tvItemPrice;

        public ItemOrderHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setData(OrderItem orderItem) {
            tvItemQnt.setText(String.valueOf(orderItem.getQuantity()));
            if (orderItem.isMeal()) {
                tvItemName.setText(orderItem.getMeal().getName());
                for (MealExtra mealExtra : orderItem.getMealExtras()) {
                    tvItemDescribe.append(mealExtra.getTitle() + "\n");
                }
                tvItemPrice.setText(DecimalFormatterManager.getFormatterInstance()
                        .format(orderItem.getPrice()));
            } else if (orderItem.isOffer()) {
                tvItemName.setText(orderItem.getOffer().getName());
                tvItemDescribe.setText(orderItem.getOffer().getDescription());
                tvItemPrice.setText(DecimalFormatterManager.getFormatterInstance()
                        .format(orderItem.getOffer().getPrice()));
            }
        }
    }
}
