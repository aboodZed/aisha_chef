package com.webapp.aisha.feture.registration.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.R;
import com.webapp.aisha.feture.profile.editProfile.CategoryListener;
import com.webapp.aisha.models.MainCategory;
import com.webapp.aisha.utils.ToolUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterAdapter extends RecyclerView.Adapter<RegisterAdapter.RegisterHolder> {

    private Activity activity;
    private ArrayList<MainCategory> checkCategories;
    private ArrayList<MainCategory> selected;
    private boolean isMain;
    private CategoryListener categoryListener;

    public RegisterAdapter(Activity activity,
                           ArrayList<MainCategory> checkCategories
            , ArrayList<MainCategory> selected, boolean isMain) {
        this.activity = activity;
        this.checkCategories = checkCategories;
        this.selected = selected;
        this.isMain = isMain;
    }

    public void setCategoryListener(CategoryListener categoryListener) {
        this.categoryListener = categoryListener;
    }

    @NonNull
    @Override
    public RegisterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RegisterHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_check_box, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RegisterHolder holder, int position) {
        holder.setData(checkCategories.get(position));
        holder.setChecked(checkCategories.get(position));
    }

    @Override
    public int getItemCount() {
        return checkCategories.size();
    }


    public ArrayList<MainCategory> getSelected() {
        return selected;
    }

    private void addSelect(MainCategory mainCategory) {
        boolean added = false;
        for (int i = 0; i < selected.size(); i++) {
            if (selected.get(i).getId() == mainCategory.getId()) {
                added = true;
            }
        }
        if (!added) {
            selected.add(mainCategory);
        }
    }

    private void removeSelect(MainCategory mainCategory) {
        for (int i = 0; i < selected.size(); i++) {
            if (selected.get(i).getId() == mainCategory.getId()) {
                selected.remove(i);
            }
        }
    }

    class RegisterHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cb_checked) CheckBox cbChecked;

        private MainCategory checkCategory;

        public RegisterHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(MainCategory data) {
            this.checkCategory = data;
            cbChecked.setText(data.getName());
            setChecked(data);
        }

        private void setChecked(MainCategory data) {
            for (MainCategory mainCategory : selected) {
                if (mainCategory.getId() == data.getId()) {
                    cbChecked.setChecked(true);
                }
            }
        }

        @OnClick(R.id.cb_checked)
        public void check() {
            if (cbChecked.isChecked()) {
                if (isMain && selected.size() == 4) {
                    cbChecked.setChecked(false);
                    ToolUtils.showShortToast(activity.getString(R.string.four_main_categories), activity);
                }else if (categoryListener.isSelected(checkCategory)){
                    cbChecked.setChecked(false);
                }else {
                    addSelect(checkCategory);
                }
            } else {
                removeSelect(checkCategory);
            }
        }
    }
}
