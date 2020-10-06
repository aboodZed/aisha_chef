package com.webapp.aisha.feture.main.wallet.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.R;
import com.webapp.aisha.models.BankTransfer;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.formatter.DecimalFormatterManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReceivableWalletAdapter extends RecyclerView.Adapter<ReceivableWalletAdapter.ReceivableWalletHolder> {

    private ArrayList<BankTransfer> wallets = new ArrayList<>();
    private Activity activity;

    public ReceivableWalletAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public ReceivableWalletHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReceivableWalletHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_receivable_wallet, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReceivableWalletHolder holder, int position) {
        holder.setData(wallets.get(position));
    }

    @Override
    public int getItemCount() {
        return wallets.size();
    }

    public void addItems(ArrayList<BankTransfer> data) {
        wallets.addAll(data);
        notifyDataSetChanged();
    }

    class ReceivableWalletHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_time) TextView tvTime;
        @BindView(R.id.tv_amount) TextView tvAmount;
        @BindView(R.id.tv_transfer_status) TextView tvTransferStatus;

        public ReceivableWalletHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setData(BankTransfer bankTransfer) {
            tvTime.setText(ToolUtils.getTime(bankTransfer.getCreated_at())
                    + "  " + ToolUtils.getDate(bankTransfer.getCreated_at()));
            tvAmount.setText(activity.getString(R.string.currency) + DecimalFormatterManager.getFormatterInstance()
                    .format(bankTransfer.getAmount()));

            if (bankTransfer.getStatus().equals(AppContent.accept)) {
                tvTransferStatus.setTextColor(activity.getColor(R.color.green));
                tvTransferStatus.setText(activity.getString(R.string.accepted));
            } else if (bankTransfer.getStatus().equals(AppContent.pending)) {
                tvTransferStatus.setTextColor(activity.getColor(R.color.black));
                tvTransferStatus.setText(activity.getString(R.string.pending));
            } else {
                tvTransferStatus.setTextColor(activity.getColor(R.color.red));
                tvTransferStatus.setText(activity.getString(R.string.rejected));
            }
        }
    }
}
