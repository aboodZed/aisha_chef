package com.webapp.aisha.feture.main.wallet.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.R;
import com.webapp.aisha.models.Wallet;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.NavigateUtils;
import com.webapp.aisha.utils.formatter.DecimalFormatterManager;
import com.webapp.aisha.utils.language.AppLanguageUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProcessWalletAdapter extends RecyclerView.Adapter<ProcessWalletAdapter.ProcessWalletHolder> {

    private ArrayList<Wallet> wallets = new ArrayList<>();
    private Activity activity;

    public ProcessWalletAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public ProcessWalletHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProcessWalletHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_process_wallet, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProcessWalletHolder holder, int position) {
        holder.setData(wallets.get(position));
    }

    @Override
    public int getItemCount() {
        return wallets.size();
    }

    public void addItems(ArrayList<Wallet> data) {
        wallets.addAll(data);
        notifyDataSetChanged();
    }

    class ProcessWalletHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cl_background) ConstraintLayout cl_Background;
        @BindView(R.id.tv_id) TextView tvId;
        @BindView(R.id.tv_price) TextView tvPrice;
        @BindView(R.id.tv_payment_type) TextView tvPaymentType;
        @BindView(R.id.tv_my_proportion) TextView tvMyProportion;
        @BindView(R.id.tv_app_proportion) TextView tvAppProportion;

        private Wallet wallet;

        public ProcessWalletHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.cl_background)
        public void openOrder() {
            NavigateUtils.openOrder(activity, AppContent.view_order, wallet.getId(),true);
        }

        private void setData(Wallet wallet) {
            this.wallet = wallet;
            tvId.setText("Order#" + wallet.getId());
            tvPrice.setText(activity.getString(R.string.currency) + DecimalFormatterManager.getFormatterInstance().format(wallet.getTotal()));
            tvAppProportion.setText(activity.getString(R.string.currency) + DecimalFormatterManager.getFormatterInstance().format(wallet.getSys_commission()));
            tvMyProportion.setText(activity.getString(R.string.currency) + DecimalFormatterManager.getFormatterInstance().format(wallet.getAgent_total()));
            if (AppController.getInstance().getAppSettingsPreferences().getAppLanguage().equals(AppLanguageUtil.AR)) {
                tvPaymentType.setText(wallet.getPayment_method().getLabel());
            } else {
                tvPaymentType.setText(wallet.getPayment_method().getCode());
            }
        }
    }
}
