package com.webapp.aisha.feture.meals.meals;

import android.app.Activity;

import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.R;
import com.webapp.aisha.services.api.ApiShared;
import com.webapp.aisha.models.Meal;
import com.webapp.aisha.models.Result;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.ArrayList;

public class MealsPresenter {

    private Activity activity;
    private NavigationView view;
    private MealsView mealsView;
    private boolean loadingMoreItems;
    private String next_page_url;

    public MealsPresenter(Activity activity, NavigationView view, MealsView mealsView, int id) {
        this.activity = activity;
        this.view = view;
        this.mealsView = mealsView;
        if (id == 0){
            next_page_url = "agents/meals/tody_dishes";
        }else {
            next_page_url = "agents/meals?category_id=" + id;
        }
        getData();
    }

    public void getData() {
        mealsView.showDialog(activity.getString(R.string.get_data));
        ApiShared.getInstance().getMealData().getMeals(activity, next_page_url
                , new RequestListener<Result<ArrayList<Meal>>>() {
                    @Override
                    public void onSuccess(Result<ArrayList<Meal>> result, String msg) {
                        loadingMoreItems = false;
                        next_page_url = result.getPagination().getNext_page_url();
                        mealsView.setMeals(result.getData());
                        mealsView.hideDialog();
                    }

                    @Override
                    public void onFail(String msg) {
                        loadingMoreItems = false;
                        mealsView.hideDialog();
                        ToolUtils.showLongToast(msg, activity);
                    }
                });
    }

    public void onScrollStateChange(RecyclerView recyclerView) {
        if (!recyclerView.canScrollVertically(1) && next_page_url != null && !loadingMoreItems) {
            loadingMoreItems = true;
            getData();
        }
    }
}
