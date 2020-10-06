package com.webapp.aisha.feture.meals.adapter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.R;
import com.webapp.aisha.models.MealExtra;
import com.webapp.aisha.models.MealFor;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExtrasAdapter extends RecyclerView.Adapter<ExtrasAdapter.ExtrasHolder> {

    private ArrayList<MealExtra> mealExtras;
    private Activity activity;
    private boolean edit;

    public ExtrasAdapter(ArrayList<MealExtra> mealExtras,
                         Activity activity, boolean edit) {
        this.mealExtras = mealExtras;
        this.activity = activity;
        this.edit = edit;
    }

    @NonNull
    @Override
    public ExtrasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExtrasHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_add_extra, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExtrasHolder holder, int position) {
        holder.setData(mealExtras.get(position));
    }

    @Override
    public int getItemCount() {
        return mealExtras.size();
    }

    public void addItem(MealExtra mealExtra) {
        mealExtras.add(mealExtra);
        notifyItemInserted(getItemCount() - 1);
    }

    public void removeItem(MealExtra mealExtra) {
        int p = mealExtras.indexOf(mealExtra);
        mealExtras.remove(mealExtra);
        notifyItemRemoved(p);
    }

    public ArrayList<MealExtra> getMealExtras() {
        return mealExtras;
    }

    class ExtrasHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.et_extra_title) EditText etExtraTitle;
        @BindView(R.id.et_extra_title_in_arabic) EditText etExtraTitleInArabic;
        @BindView(R.id.et_extra_price) EditText etExtraPrice;
        @BindView(R.id.iv_delete) ImageView ivDelete;

        private MealExtra mealExtra;

        public ExtrasHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setData(MealExtra mealExtra) {
            this.mealExtra = mealExtra;
            if (edit) {
                ivDelete.setVisibility(View.GONE);
            }
            if (mealExtra.getId() != 0) {
                etExtraTitle.setText(mealExtra.getTitle());
                etExtraTitleInArabic.setText(mealExtra.getTitle());
                etExtraPrice.setText(String.valueOf(mealExtra.getPrice()));
                if (edit) {
                    etExtraTitle.setFocusable(false);
                    etExtraTitleInArabic.setFocusable(false);
                    etExtraPrice.setFocusable(false);
                }
            } else {
                mealExtra.setTitle("");
                mealExtra.setTitle_in_arabic("");
                mealExtra.setPrice(0);
            }
            etExtraTitle.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    mealExtra.setTitle(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            etExtraTitleInArabic.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    mealExtra.setTitle_in_arabic(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            etExtraPrice.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (!charSequence.toString().equals("")) {
                        int price = Integer.parseInt(charSequence.toString());
                        if (price > 0) {
                            mealExtra.setPrice(price);
                        } else {
                            etExtraPrice.setError(activity.getString(R.string.price_above_zero));
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
        }

        @OnClick(R.id.iv_delete)
        public void delete() {
            removeItem(mealExtra);
        }
    }
}
