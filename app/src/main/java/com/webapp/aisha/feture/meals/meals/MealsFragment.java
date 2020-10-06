package com.webapp.aisha.feture.meals.meals;

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
import com.webapp.aisha.feture.meals.adapter.CategoryItemsAdapter;
import com.webapp.aisha.models.Meal;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.ToolUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MealsFragment extends Fragment implements MealsView {

    @BindView(R.id.rv_items) RecyclerView rvItems;
    @BindView(R.id.fl_bottom) FrameLayout flBottom;
    @BindView(R.id.fab_add) FloatingActionButton fabAdd;

    private MealsPresenter presenter;
    private CategoryItemsAdapter adapter;
    private NavigationView view;
    private static final String ID = "id";
    private int id;
    private ViewMealListener listener;

    public static MealsFragment newInstance(NavigationView navigationView, int id, ViewMealListener listener) {
        MealsFragment mealsFragment = new MealsFragment(navigationView, listener);
        Bundle bundle = new Bundle();
        bundle.putInt(ID, id);
        mealsFragment.setArguments(bundle);
        return mealsFragment;
    }

    public MealsFragment(NavigationView view, ViewMealListener listener) {
        this.view = view;
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_meals, container, false);
        ButterKnife.bind(this, v);
        initRecycleView();
        id = getArguments().getInt(ID);
        presenter = new MealsPresenter(getActivity(), view, this, id);
        return v;
    }

    private void initRecycleView() {
        adapter = new CategoryItemsAdapter(getActivity(), listener);
        rvItems.setLayoutManager(new LinearLayoutManager(getContext()));
        rvItems.setItemAnimator(new DefaultItemAnimator());
        rvItems.setAdapter(adapter);
        rvItems.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                presenter.onScrollStateChange(recyclerView);
            }
        });
    }

    @OnClick(R.id.fab_add)
    public void add() {
        if (AppController.getInstance().getAppSettingsPreferences()
                .getUser().getCities_can_delivery().isEmpty()) {
            ToolUtils.showLongToast(getString(R.string.add_delivery_time),getActivity());
            view.navigate(AppContent.delivery_rate_one);
        } else {
            view.navigate(AppContent.add_meal);
        }
    }

    @Override
    public void setMeals(ArrayList<Meal> meals) {
        adapter.addItems(meals);
    }

    @Override
    public void showDialog(String s) {
        flBottom.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDialog() {
        flBottom.setVisibility(View.GONE);
    }

    public interface ViewMealListener {
        void viewMeal(int id);
    }
}