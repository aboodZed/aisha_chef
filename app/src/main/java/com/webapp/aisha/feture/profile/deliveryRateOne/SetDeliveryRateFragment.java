package com.webapp.aisha.feture.profile.deliveryRateOne;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.R;
import com.webapp.aisha.feture.profile.adapter.AreaDeliveryAdapter;
import com.webapp.aisha.models.City;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.dialogs.WaitDialogFragment;
import com.webapp.aisha.utils.listener.DialogView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetDeliveryRateFragment extends Fragment implements DialogView {

    @BindView(R.id.rv_delivery_time) RecyclerView rvDeliveryTime;
    @BindView(R.id.btn_save) Button btnSave;

    private AreaDeliveryAdapter adapter;
    private SetDeliveryRatePresenter presenter;
    private NavigationView view;

    public static SetDeliveryRateFragment newInstance(NavigationView navigationView) {
        return new SetDeliveryRateFragment(navigationView);
    }

    public SetDeliveryRateFragment(NavigationView view) {
        this.view = view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_set_delivery_rate, container, false);
        ButterKnife.bind(this, v);
        presenter = new SetDeliveryRatePresenter(getActivity(), this);
        if (AppController.getInstance().getAppSettingsPreferences()
                .getUser().getWorking_days().isEmpty()) {
            ToolUtils.showLongToast(getString(R.string.add_delivery_time), getActivity());
            view.navigate(AppContent.schedule);
        } else {
            initRecycleView(AppController.getInstance().getAppSettingsPreferences().getUser().getCities_can_delivery());
        }
        return v;
    }

    private void initRecycleView(ArrayList<City> cities) {
        adapter = new AreaDeliveryAdapter(getActivity(), cities, getFragmentManager());
        rvDeliveryTime.setLayoutManager(new LinearLayoutManager(getContext()));
        rvDeliveryTime.setItemAnimator(new DefaultItemAnimator());
        rvDeliveryTime.setAdapter(adapter);
    }

    @OnClick(R.id.btn_save)
    public void save() {
        presenter.ValidateInput(adapter.getCities());
    }

    @Override
    public void showDialog(String s) {
        WaitDialogFragment.newInstance(s).show(getFragmentManager(), "");
    }

    @Override
    public void hideDialog() {
        WaitDialogFragment.newInstance("").dismiss();
    }
}