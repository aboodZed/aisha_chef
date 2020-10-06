package com.webapp.aisha.feture.profile.deliveryRateTwo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.webapp.aisha.R;

import butterknife.ButterKnife;

public class EditDeliveryRateFragment extends Fragment {


    public static EditDeliveryRateFragment newInstance() {
        return  new EditDeliveryRateFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_delivery_rate, container, false);
        ButterKnife.bind(this, v);
        return v;
    }
}