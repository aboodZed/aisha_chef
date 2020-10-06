package com.webapp.aisha.feture.profile.privacy;

import android.app.Activity;

import com.webapp.aisha.R;
import com.webapp.aisha.services.api.ApiShared;
import com.webapp.aisha.models.PageData;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.ArrayList;

class PrivacyPresenter {

    private Activity activity;
    private PrivacyView privacyView;

    public PrivacyPresenter(Activity activity, PrivacyView privacyView) {
        this.activity = activity;
        this.privacyView = privacyView;
        getData();
    }

    private void getData() {
        privacyView.showDialog(activity.getString(R.string.get_data));
        ApiShared.getInstance().getGetData().getPages(activity,
                new RequestListener<ArrayList<PageData>>() {
                    @Override
                    public void onSuccess(ArrayList<PageData> pageData, String msg) {
                        privacyView.setData(pageData);
                        privacyView.hideDialog();
                    }

                    @Override
                    public void onFail(String msg) {
                        ToolUtils.showLongToast(msg, activity);
                        privacyView.hideDialog();
                    }
                });
    }
}
