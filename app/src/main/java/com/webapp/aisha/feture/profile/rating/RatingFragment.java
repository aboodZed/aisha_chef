package com.webapp.aisha.feture.profile.rating;

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

import com.webapp.aisha.R;
import com.webapp.aisha.feture.profile.adapter.RatingAdapter;
import com.webapp.aisha.models.Rating;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RatingFragment extends Fragment implements RatingView {

    @BindView(R.id.rv_reviews) RecyclerView rvReviews;
    @BindView(R.id.fl_load_more) FrameLayout flLoadMore;

    private RatingAdapter adapter;
    private RatingPresenter presenter;

    public static RatingFragment newInstance() {
        return new RatingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rating, container, false);
        ButterKnife.bind(this, v);
        initRecycleView();
        presenter = new RatingPresenter(getActivity(), this);
        return v;
    }

    private void initRecycleView() {
        adapter = new RatingAdapter(getActivity());
        rvReviews.setLayoutManager(new LinearLayoutManager(getContext()));
        rvReviews.setItemAnimator(new DefaultItemAnimator());
        rvReviews.setAdapter(adapter);
        rvReviews.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                presenter.onScrollStateChanged(recyclerView);
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    @Override
    public void setData(ArrayList<Rating> data) {
        adapter.addItems(data);
    }

    @Override
    public void showDialog(String s) {
        flLoadMore.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDialog() {
        flLoadMore.setVisibility(View.GONE);
    }
}