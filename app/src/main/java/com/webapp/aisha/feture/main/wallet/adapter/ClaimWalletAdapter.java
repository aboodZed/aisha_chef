package com.webapp.aisha.feture.main.wallet.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.R;
import com.webapp.aisha.models.Claim;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.formatter.DecimalFormatterManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClaimWalletAdapter extends RecyclerView.Adapter<ClaimWalletAdapter.ClaimWalletHolder> {

    private ArrayList<Claim> claims = new ArrayList<>();
    private Activity activity;

    public ClaimWalletAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public ClaimWalletHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClaimWalletHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_claim_wallet, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ClaimWalletHolder holder, int position) {
        holder.setData(claims.get(position));
    }

    @Override
    public int getItemCount() {
        return claims.size();
    }

    public void addItems(ArrayList<Claim> list) {
        claims.addAll(list);
        notifyDataSetChanged();
    }

    class ClaimWalletHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_time) TextView tvTime;
        @BindView(R.id.tv_amount) TextView tvAmount;
        @BindView(R.id.tv_claim_status) TextView tvClaimStatus;

        public ClaimWalletHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setData(Claim claim) {
            tvTime.setText(ToolUtils.getTime(claim.getCreated_at())
                    + "  " + ToolUtils.getDate(claim.getCreated_at()));
            tvAmount.setText(activity.getString(R.string.currency) + DecimalFormatterManager.getFormatterInstance()
                    .format(claim.getAmount()));
            if (claim.getStatus().equals(AppContent.accept)) {
                tvClaimStatus.setTextColor(activity.getColor(R.color.green));
                tvClaimStatus.setText(activity.getString(R.string.accepted));
            } else if (claim.getStatus().equals(AppContent.pending)) {
                tvClaimStatus.setTextColor(activity.getColor(R.color.black));
                tvClaimStatus.setText(activity.getString(R.string.pending));
            } else {
                tvClaimStatus.setTextColor(activity.getColor(R.color.red));
                tvClaimStatus.setText(activity.getString(R.string.rejected));
            }
        }
    }
}
