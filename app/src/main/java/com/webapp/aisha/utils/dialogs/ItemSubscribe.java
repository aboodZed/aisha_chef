package com.webapp.aisha.utils.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.webapp.aisha.R;
import com.webapp.aisha.models.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ItemSubscribe extends Fragment {

    @BindView(R.id.tv_price) TextView tvPrice;
    @BindView(R.id.tv_period) TextView tvPeriod;
    @BindView(R.id.tv_policy) TextView tvPolicy;
    @BindView(R.id.btn_select) Button btnSelect;

    private static final String SUBSCRIBE = "subscribe";
    private Listener listener;
    private Subscribe subscribe;

    public static ItemSubscribe newInstance(Subscribe subscribe, Listener listener) {
        ItemSubscribe subscribeFragment = new ItemSubscribe(listener);
        Bundle bundle = new Bundle();
        bundle.putSerializable(SUBSCRIBE, subscribe);
        subscribeFragment.setArguments(bundle);
        return subscribeFragment;
    }

    public ItemSubscribe(Listener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_subscribe, container, false);
        ButterKnife.bind(this, view);
        subscribe = (Subscribe) getArguments().getSerializable(SUBSCRIBE);
        setData(subscribe);
        return view;
    }

    private void setData(Subscribe data) {
        tvPrice.setText(getString(R.string.currency) + data.getPrice());
        tvPeriod.setText("For " + data.getDuration() + " month");
        tvPolicy.setText(data.getDescription());
    }

    @OnClick(R.id.btn_select)
    public void select() {
        listener.selected(subscribe);
    }

    public interface Listener {
        void selected(Subscribe subscribe);
    }
}
