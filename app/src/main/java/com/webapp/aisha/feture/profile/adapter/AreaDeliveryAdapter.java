package com.webapp.aisha.feture.profile.adapter;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.R;
import com.webapp.aisha.feture.main.schedule.adapter.HoursAdapter;
import com.webapp.aisha.models.City;
import com.webapp.aisha.models.DayTime;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.dialogs.DayPickerDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AreaDeliveryAdapter extends RecyclerView.Adapter<AreaDeliveryAdapter.AreaDeliveryHolder> {

    private Activity activity;
    private ArrayList<City> cities;
    private FragmentManager fragmentManager;

    public AreaDeliveryAdapter(Activity activity, ArrayList<City> cities
            , FragmentManager fragmentManager) {
        this.activity = activity;
        this.cities = cities;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public AreaDeliveryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AreaDeliveryHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_area_delivery, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AreaDeliveryHolder holder, int position) {
        holder.setData(cities.get(position));
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public void removeItem(City s) {
        int i = cities.indexOf(s);
        cities.remove(s);
        notifyItemRemoved(i);
    }

    class AreaDeliveryHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_area_name) TextView tvAreaName;
        @BindView(R.id.iv_delete) ImageView ivDelete;
        @BindView(R.id.iv_add_time) ImageView ivAddTime;
        @BindView(R.id.et_price) EditText etPrice;
        @BindView(R.id.rv_times) RecyclerView rvTimes;
        @BindView(R.id.iv_add_day) ImageView ivAddDay;
        @BindView(R.id.rv_days) RecyclerView rvDays;

        private City city;
        private DayTimeAdapter dayTimeAdapter;
        private HoursAdapter hoursAdapter;

        public AreaDeliveryHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setData(City city) {
            this.city = city;
            tvAreaName.setText(city.getName());
            etPrice.setText(String.valueOf(city.getPrice()));
            if (!city.getTimes().isEmpty()) {
                hoursAdapter = new HoursAdapter(activity, city.getTimes().get(0).getHours());
            } else {
                hoursAdapter = new HoursAdapter(activity, new ArrayList<>());
            }
            rvTimes.setLayoutManager(new GridLayoutManager(activity, 3));
            rvTimes.setItemAnimator(new DefaultItemAnimator());
            rvTimes.setAdapter(hoursAdapter);

            //setDays
            dayTimeAdapter = new DayTimeAdapter(activity, city.getTimes());
            rvDays.setLayoutManager(new GridLayoutManager(activity, 4));
            rvDays.setItemAnimator(new DefaultItemAnimator());
            rvDays.setAdapter(dayTimeAdapter);

            etPrice.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (!charSequence.toString().trim().equals("")) {
                        city.setPrice(Double.parseDouble(charSequence.toString().trim()));
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

        @OnClick(R.id.iv_delete)
        public void delete() {
            removeItem(city);
        }

        @OnClick(R.id.iv_add_time)
        public void addTime() {
            TimePickerDialog timePickerDialog = new TimePickerDialog(activity, (timePicker, i, i1) -> {
                int h = i, m = i1;
                String t = "am";
                if (i > 12) {
                    h = i - 12;
                    t = "pm";
                }
                hoursAdapter.addItem(ToolUtils.formatTime(h) + ":" + ToolUtils.formatTime(m) + " " + t);
            }, 24, 60, false);
            timePickerDialog.show();
        }

        @OnClick(R.id.iv_add_day)
        public void addDay() {
            DayPickerDialog dayPickerDialog = DayPickerDialog.newInstance(dayTimeAdapter.getDayTimes(),
                    schedule -> dayTimeAdapter.addItem(new DayTime(schedule, hoursAdapter.getHours())));
            dayPickerDialog.show(fragmentManager, "");
        }
    }
}