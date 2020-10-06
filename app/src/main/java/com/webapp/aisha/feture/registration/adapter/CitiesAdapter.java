package com.webapp.aisha.feture.registration.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.R;
import com.webapp.aisha.models.City;
import com.webapp.aisha.models.MainCategory;
import com.webapp.aisha.utils.AppController;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.CityHolder> {

    private ArrayList<City> cities;
    private ArrayList<City> selected;

    public CitiesAdapter(ArrayList<City> cities, ArrayList<City> selected) {
        this.cities = cities;
        this.selected = selected;
    }

    @NonNull
    @Override
    public CityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CityHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_check_box, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CityHolder holder, int position) {
        holder.setData(cities.get(position));
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    private void addSelect(City city) {
        boolean added = false;
        for (int i = 0; i < selected.size(); i++) {
            if (selected.get(i).getId() == city.getId()) {
                added = true;
            }
        }
        if (!added) {
            selected.add(city);
        }
    }

    private void removeSelect(City city) {
        for (int i = 0; i < selected.size(); i++) {
            if (selected.get(i).getId() == city.getId()) {
                selected.remove(i);
            }
        }
    }

    public ArrayList<City> getSelected() {
        return selected;
    }

    class CityHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cb_checked) CheckBox cbChecked;

        private City city;

        public CityHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setData(City city) {
            this.city = city;
            cbChecked.setText(city.getName());
            setChecked(city);
        }

        private void setChecked(City city) {
            for (City c : selected) {
                if (city.getId() == c.getId()) {
                    cbChecked.setChecked(true);
                }
            }
            City userCity = AppController.getInstance().getAppSettingsPreferences().getUser().getCity();
            if (city.getId() == userCity.getId()) {
                cbChecked.setChecked(true);
                if (!selected.contains(city))
                    addSelect(city);
            }
        }

        @OnClick(R.id.cb_checked)
        public void check() {
            if (cbChecked.isChecked()) {
                addSelect(city);
            } else {
                removeSelect(city);
            }
        }
    }
}
