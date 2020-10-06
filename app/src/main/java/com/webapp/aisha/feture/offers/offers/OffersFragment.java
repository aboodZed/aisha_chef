package com.webapp.aisha.feture.offers.offers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.webapp.aisha.R;
import com.webapp.aisha.feture.offers.adapter.OfferAdapter;
import com.webapp.aisha.models.Offer;
import com.webapp.aisha.models.Result;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.ToolUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OffersFragment extends Fragment implements OfferView {

    @BindView(R.id.rv_offers) RecyclerView rvOffers;
    @BindView(R.id.fl_load_more) FrameLayout flLoadMore;
    @BindView(R.id.fab_add) FloatingActionButton fabAdd;

    private OffersPresenter presenter;
    private OfferAdapter adapter;
    private ViewOfferListener listener;
    private NavigationView view;

    public static OffersFragment newInstance(NavigationView navigationView, ViewOfferListener listener) {
        return new OffersFragment(listener, navigationView);
    }

    public OffersFragment(ViewOfferListener listener, NavigationView view) {
        this.listener = listener;
        this.view = view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_offers, container, false);
        ButterKnife.bind(this, v);
        presenter = new OffersPresenter(getActivity(), view, this);
        init();
        presenter.getData();
        return v;
    }

    private void init() {
        adapter = new OfferAdapter(getActivity(), listener);
        rvOffers.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvOffers.setItemAnimator(new DefaultItemAnimator());
        rvOffers.setAdapter(adapter);
        rvOffers.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                presenter.recycleScrollListener(recyclerView);
            }
        });
    }

    @Override
    public void initRecycleView(Result<ArrayList<Offer>> list) {
        adapter.addItems(list.getData());
    }

    @OnClick(R.id.fab_add)
    public void add() {
        if (AppController.getInstance().getAppSettingsPreferences()
                .getUser().getCities_can_delivery().isEmpty()) {
            ToolUtils.showLongToast(getString(R.string.add_delivery_time), getActivity());
            view.navigate(AppContent.delivery_rate_one);
        } else {
            view.navigate(AppContent.add_offer);
        }
    }

    @Override
    public void showDialog(String s) {
        flLoadMore.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDialog() {
        flLoadMore.setVisibility(View.GONE);
    }

    public interface ViewOfferListener {
        void viewOffer(Offer offer);
    }
}