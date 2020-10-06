package com.webapp.aisha.feture.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.R;
import com.webapp.aisha.feture.main.home.adapter.MainCategoriesAdapter;
import com.webapp.aisha.feture.offers.OfferActivity;
import com.webapp.aisha.models.MainCategory;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.NavigateUtils;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends Fragment implements HomeView {

    @BindView(R.id.rv_categories) RecyclerView rvCategories;
    @BindView(R.id.fl_load) FrameLayout flLoad;
    @BindView(R.id.cl_an_offer) ConstraintLayout clAnOffer;
    @BindView(R.id.cl_today_dish) ConstraintLayout clTodayDish;
    @BindView(R.id.cl_reservations) ConstraintLayout clReservations;

    private HomePresenter presenter;
    private MainCategoriesAdapter adapter;
    private NavigationView view;

    public static HomeFragment newInstance(NavigationView navigationView) {
        return new HomeFragment(navigationView);
    }

    public HomeFragment(NavigationView view) {
        this.view = view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, v);
        //presenter
        presenter = new HomePresenter(getActivity(), view, this);
        presenter.getData();
        return v;
    }

    @OnClick(R.id.cl_an_offer)
    public void offers() {
        NavigateUtils.fromActivityToActivityWithPage(getActivity(), OfferActivity.class
                , false, AppContent.OFFER_PAGE, AppContent.offers);
    }

    @OnClick(R.id.cl_today_dish)
    public void todayDish() {
        NavigateUtils.openCategory(getActivity(), AppContent.meals, 0, AppContent.today_dish_type);
    }

    @OnClick(R.id.cl_reservations)
    public void reservations() {
        presenter.reservations(getFragmentManager());
    }

    @Override
    public void initRecycleView(ArrayList<MainCategory> mainCategories) {
        adapter = new MainCategoriesAdapter(mainCategories, getActivity());
        rvCategories.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        rvCategories.setItemAnimator(new DefaultItemAnimator());
        rvCategories.setAdapter(adapter);
    }

    @Override
    public void showDialog(String s) {
        flLoad.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDialog() {
        flLoad.setVisibility(View.GONE);
    }
}