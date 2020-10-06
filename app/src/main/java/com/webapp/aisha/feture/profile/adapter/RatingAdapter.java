package com.webapp.aisha.feture.profile.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.R;
import com.webapp.aisha.models.Rating;
import com.webapp.aisha.utils.ToolUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.RatingHolder> {

    private Activity activity;
    private ArrayList<Rating> ratings = new ArrayList<>();

    public RatingAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RatingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RatingHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RatingHolder holder, int position) {
        holder.setData(ratings.get(position));
    }

    @Override
    public int getItemCount() {
        return ratings.size();
    }

    public void addItems(ArrayList<Rating> list){
        ratings.addAll(list);
        notifyDataSetChanged();
    }

    class RatingHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_avatar) ImageView ivAvatar;
        @BindView(R.id.tv_name) TextView tvName;
        @BindView(R.id.tv_datetime) TextView tvDatetime;
        @BindView(R.id.tv_rating) TextView tvRating;
        @BindView(R.id.tv_details) TextView tvDetails;

        public RatingHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setData(Rating rating) {
            tvName.setText(rating.getUser().getName());
            tvDatetime.setText(ToolUtils.getTime(rating.getCreated_at())
                    + "  " + ToolUtils.getDate(rating.getCreated_at()));
            tvRating.setText(String.valueOf(rating.getRating()));
            tvDetails.setText(rating.getComment());
            ToolUtils.loadImageNormal(activity,null,rating.getUser().getAvatar_url(),ivAvatar);
        }
    }
}
