package com.webapp.aisha.feture.meals.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.R;
import com.webapp.aisha.feture.meals.meals.MealsFragment.ViewMealListener;
import com.webapp.aisha.models.Meal;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.ToolUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryItemsAdapter extends RecyclerView.Adapter<CategoryItemsAdapter.CategoryItemsHolder> {

    private ArrayList<Meal> meals = new ArrayList<>();
    private Activity activity;
    private ViewMealListener listener;

    public CategoryItemsAdapter(Activity activity, ViewMealListener listener) {
        this.activity = activity;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryItemsHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_item_main_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryItemsHolder holder, int position) {
        holder.setData(meals.get(position));
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void addItems(ArrayList<Meal> list) {
        meals.addAll(list);
        notifyDataSetChanged();
    }

    class CategoryItemsHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_photo) ImageView ivPhoto;
        @BindView(R.id.pb_photo) ProgressBar pbPhoto;
        @BindView(R.id.tv_title) TextView tvTitle;
        @BindView(R.id.tv_price) TextView tvPrice;
        @BindView(R.id.tv_within) TextView tvWithin;
        @BindView(R.id.tv_available) TextView tvAvailable;
        @BindView(R.id.iv_edit) ImageView ivEdit;

        private int id;

        public CategoryItemsHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setData(Meal meal) {
            id = meal.getId();
            ToolUtils.loadImageNormal(activity, pbPhoto, meal.getMealImages().get(0).getThumbnail(), ivPhoto);
            tvTitle.setText(meal.getName());
            tvPrice.setText(String.valueOf(meal.getPrice()));
            tvWithin.setText(activity.getString(R.string.within) + meal.getDuration() + activity.getString(R.string.hrs));
            if (meal.isIs_active()) {
                tvAvailable.setText(activity.getString(R.string.available));
                tvAvailable.setTextColor(activity.getColor(R.color.green));
            } else {
                tvAvailable.setText(activity.getString(R.string.not_available));
                tvAvailable.setTextColor(activity.getColor(R.color.red));
            }
        }

        @OnClick(R.id.iv_edit)
        public void edit() {
            listener.viewMeal(id);
        }
    }
}
