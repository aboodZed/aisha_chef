package com.webapp.aisha.feture.offers.view;

import android.app.Activity;
import android.widget.Switch;

import com.webapp.aisha.R;
import com.webapp.aisha.services.api.ApiShared;
import com.webapp.aisha.models.Offer;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.listener.RequestListener;

public class ViewOfferPresenter {

    private Activity activity;
    private NavigationView view;
    private ViewOfferView viewOfferView;

    public ViewOfferPresenter(Activity activity, NavigationView view, ViewOfferView viewOfferView) {
        this.activity = activity;
        this.view = view;
        this.viewOfferView = viewOfferView;
    }

    public void getOffer(int id) {
        viewOfferView.showDialog(activity.getString(R.string.get_data));
        ApiShared.getInstance().getOfferData().getOffer(activity, id
                , new RequestListener<Offer>() {
                    @Override
                    public void onSuccess(Offer offer, String msg) {
                        viewOfferView.setData(offer);
                        viewOfferView.hideDialog();
                    }

                    @Override
                    public void onFail(String msg) {
                        ToolUtils.showLongToast(msg, activity);
                        viewOfferView.hideDialog();
                    }
                });
    }

    public void updateOffer(int id, Switch sAvailable) {
        int i = 0;
        if (sAvailable.isChecked()) {
            i = 1;
        }
        viewOfferView.showDialog(activity.getString(R.string.update_offer_status));
        ApiShared.getInstance().getOfferData().updateOffer(activity, id, i
                , new RequestListener<Offer>() {
                    @Override
                    public void onSuccess(Offer offer, String msg) {
                        viewOfferView.setData(offer);
                        viewOfferView.hideDialog();
                    }

                    @Override
                    public void onFail(String msg) {
                        if (sAvailable.isChecked()){
                            sAvailable.setChecked(false);
                        }else {
                            sAvailable.setChecked(true);
                        }
                        ToolUtils.showLongToast(msg, activity);
                        viewOfferView.hideDialog();
                    }
                });
    }
}
