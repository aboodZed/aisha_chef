package com.webapp.aisha.feture.offers.offers;

import android.app.Activity;

import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.R;
import com.webapp.aisha.services.api.ApiShared;
import com.webapp.aisha.models.Offer;
import com.webapp.aisha.models.Result;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.ArrayList;

public class OffersPresenter {

    private Activity activity;
    private NavigationView view;
    private OfferView offerView;
    private String next_page_url = "agents/offers";
    private boolean loadingMoreItems;

    public OffersPresenter(Activity activity, NavigationView view, OfferView offerView) {
        this.activity = activity;
        this.view = view;
        this.offerView = offerView;
    }

    public void getData() {
        offerView.showDialog(activity.getString(R.string.get_data));
        ApiShared.getInstance().getOfferData().getOffers(activity, next_page_url
                , new RequestListener<Result<ArrayList<Offer>>>() {
            @Override
            public void onSuccess(Result<ArrayList<Offer>> offers, String msg) {
                loadingMoreItems = false;
                next_page_url = offers.getPagination().getNext_page_url();
                offerView.initRecycleView(offers);
                offerView.hideDialog();
            }

            @Override
            public void onFail(String msg) {
                loadingMoreItems = false;
                offerView.hideDialog();
                ToolUtils.showLongToast(msg, activity);
            }
        });
    }

    public void recycleScrollListener(RecyclerView recyclerView){
        if (!recyclerView.canScrollVertically(1) && next_page_url != null && !loadingMoreItems) {
            loadingMoreItems = true;
            getData();
        }
    }
}

