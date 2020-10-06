package com.webapp.aisha.feture.main.wallet.claims;

import android.app.Activity;

import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.feture.main.wallet.WalletView;
import com.webapp.aisha.services.api.ApiShared;
import com.webapp.aisha.models.Claim;
import com.webapp.aisha.models.Result;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.ArrayList;

class ClaimWalletPresenter {

    private Activity activity;
    private WalletView<ArrayList<Claim>> walletView;
    private String next_page_url = "agents/withdraws";
    private boolean loadingMoreItems;

    public ClaimWalletPresenter(Activity activity, WalletView<ArrayList<Claim>> walletView) {
        this.activity = activity;
        this.walletView = walletView;
        getData();
    }

    private void getData() {
        walletView.showDialog("");
        ApiShared.getInstance().getWalletData().getClaims(activity, next_page_url
                , new RequestListener<Result<ArrayList<Claim>>>() {
            @Override
            public void onSuccess(Result<ArrayList<Claim>> claims, String msg) {
                next_page_url = claims.getPagination().getNext_page_url();
                walletView.setData(claims.getData());
                walletView.hideDialog();
                loadingMoreItems = false;
            }

            @Override
            public void onFail(String msg) {
                ToolUtils.showLongToast(msg, activity);
                walletView.hideDialog();
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
