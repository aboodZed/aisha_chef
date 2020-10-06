package com.webapp.aisha.feture.profile.rating;

import android.app.Activity;

import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.services.api.ApiShared;
import com.webapp.aisha.models.Rating;
import com.webapp.aisha.models.Result;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.ArrayList;

class RatingPresenter {

    private Activity activity;
    private RatingView ratingView;
    private String next_page_url = "agents/ratings";
    private boolean loadingMoreItems;

    public RatingPresenter(Activity activity, RatingView ratingView) {
        this.activity = activity;
        this.ratingView = ratingView;
        getData();
    }

    private void getData() {
        ratingView.showDialog("");
        ApiShared.getInstance().getPackageData().getRating(activity, next_page_url
                , new RequestListener<Result<ArrayList<Rating>>>() {
                    @Override
                    public void onSuccess(Result<ArrayList<Rating>> list, String msg) {
                        ratingView.setData(list.getData());
                        next_page_url = list.getPagination().getNext_page_url();
                        ratingView.hideDialog();
                        loadingMoreItems = false;
                    }

                    @Override
                    public void onFail(String msg) {
                        ToolUtils.showLongToast(msg, activity);
                        ratingView.hideDialog();
                        loadingMoreItems = false;
                    }
                });
    }

    public void onScrollStateChanged(RecyclerView recyclerView) {
        if (!recyclerView.canScrollVertically(1) && next_page_url != null && !loadingMoreItems) {
            loadingMoreItems = true;
            getData();
        }
    }
}
