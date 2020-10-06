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
import com.webapp.aisha.models.MealFor;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForAdapter extends RecyclerView.Adapter<ForAdapter.ForHolder> {

    private ArrayList<MealFor> mealFors;
    private Activity activity;
    private boolean edit;

    public ForAdapter(ArrayList<MealFor> mealFors
            , Activity activity, boolean edit) {
        this.mealFors = mealFors;
        this.activity = activity;
        this.edit = edit;
    }

    @NonNull
    @Override
    public ForHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ForHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_add_for, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ForHolder holder, int position) {
        holder.setData(mealFors.get(position));
    }

    @Override
    public int getItemCount() {
        return mealFors.size();
    }

    public void addItem(MealFor mealFor) {
        mealFors.add(mealFor);
        notifyItemInserted(getItemCount() - 1);
    }

    public void removeItem(MealFor mealFor) {
        int p = mealFors.indexOf(mealFor);
        mealFors.remove(mealFor);
        notifyItemRemoved(p);
    }

    public ArrayList<MealFor> getMealFors() {
        return mealFors;
    }

    class ForHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.et_number_of_people) EditText etNumberOfPeople;
        @BindView(R.id.et_price) EditText etPrice;
        @BindView(R.id.iv_delete) ImageView ivDelete;

        private MealFor mealFor;

        public ForHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setData(MealFor mealFor) {
            this.mealFor = mealFor;
            if (edit) {
                ivDelete.setVisibility(View.GONE);
            }
            if (mealFor.getId() != 0) {
                etNumberOfPeople.setText(mealFor.getPeople_number());
                etPrice.setText(String.valueOf(mealFor.getPrice()));
                if (edit) {
                    etNumberOfPeople.setFocusable(false);
                    etPrice.setFocusable(false);
                }
            } else {
                mealFor.setPeople_number("");
            }
            etNumberOfPeople.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    mealFor.setPeople_number(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });

            etPrice.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (!charSequence.toString().equals("")) {
                        int price = Integer.parseInt(charSequence.toString());
                        if (price != 0) {
                            mealFor.setPrice(price);
                        } else {
                            etPrice.setError(activity.getString(R.string.price_above_zero));
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
            removeItem(mealFor);
        }
    }
}
