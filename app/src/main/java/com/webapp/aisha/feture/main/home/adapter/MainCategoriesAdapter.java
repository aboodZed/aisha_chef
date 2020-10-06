package com.webapp.aisha.feture.main.home.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.R;
import com.webapp.aisha.models.MainCategory;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.NavigateUtils;
import com.webapp.aisha.utils.ToolUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainCategoriesAdapter extends RecyclerView.Adapter<MainCategoriesAdapter.MainCategoryHolder> {

    private ArrayList<MainCategory> mainCategories;
    private Activity activity;

    public MainCategoriesAdapter(ArrayList<MainCategory> mainCategories, Activity activity) {
        this.mainCategories = mainCategories;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MainCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainCategoryHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainCategoryHolder holder, int position) {
        holder.setData(mainCategories.get(position));
    }

    @Override
    public int getItemCount() {
        return mainCategories.size();
    }

    class MainCategoryHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_category) LinearLayout llCategory;
        @BindView(R.id.iv_category_img) CircleImageView ivCategoryImg;
        @BindView(R.id.tv_category_name) TextView tvCategoryName;

        private MainCategory mainCategory;

        public MainCategoryHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(MainCategory s) {
            mainCategory = s;
            ToolUtils.loadImageNormal(activity, null, s.getIcon_url(), ivCategoryImg);
            tvCategoryName.setText(s.getName());
        }

        @OnClick(R.id.ll_category)
        public void meals() {
            NavigateUtils.openCategory(activity, AppContent.meals, mainCategory.getId(), AppContent.edit_meal_type);
        }
    }
}
