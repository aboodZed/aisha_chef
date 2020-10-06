package com.webapp.aisha.feture.offers.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.R;
import com.webapp.aisha.feture.offers.offers.OffersFragment.ViewOfferListener;
import com.webapp.aisha.models.Offer;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.formatter.DecimalFormatterManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.OfferHolder> {

    private ArrayList<Offer> offers = new ArrayList<>();
    private Activity activity;
    private ViewOfferListener listener;

    public OfferAdapter(Activity activity, ViewOfferListener listener) {
        this.activity = activity;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OfferHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OfferHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_offer, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OfferHolder holder, int position) {
        holder.setData(offers.get(position));
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public void addItems(ArrayList<Offer> list) {
        if (list != null) {
            offers.addAll(list);
            notifyDataSetChanged();
        }
    }

    class OfferHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cl_background) ConstraintLayout clBackground;
        @BindView(R.id.iv_image) ImageView ivImage;
        @BindView(R.id.pb_image) ProgressBar pbImage;
        @BindView(R.id.tv_details) TextView tvDetails;
        @BindView(R.id.tv_price) TextView tvPrice;
        @BindView(R.id.tv_available) TextView tvAvailable;
        @BindView(R.id.tv_accept) TextView tvAccept;

        private Offer offer;

        public OfferHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.cl_background)
        public void offer() {
            listener.viewOffer(offer);
        }

        private void setData(Offer offer) {
            this.offer = offer;
            ToolUtils.loadImageNormal(activity, pbImage, offer.getImg_url(), ivImage);
            tvDetails.setText(offer.getDescription());
            tvPrice.setText(activity.getString(R.string.currency) + DecimalFormatterManager
                    .getFormatterInstance().format(offer.getPrice()));
            if (offer.isIs_active()){
                tvAvailable.setText(activity.getString(R.string.available));
                tvAvailable.setTextColor(activity.getColor(R.color.green));
            }else {
                tvAvailable.setText(activity.getString(R.string.not_available));
                tvAvailable.setTextColor(activity.getColor(R.color.red));
            }
            if (offer.getStatus().equals(AppContent.accept)) {
                tvAccept.setTextColor(activity.getColor(R.color.green));
                tvAccept.setText(activity.getString(R.string.accepted));
            } else if (offer.getStatus().equals(AppContent.pending)) {
                tvAccept.setTextColor(activity.getColor(R.color.black));
                tvAccept.setText(activity.getString(R.string.pending));
            } else {
                tvAccept.setTextColor(activity.getColor(R.color.red));
                tvAccept.setText(activity.getString(R.string.rejected));
            }
        }
    }
}
