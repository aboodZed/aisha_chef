package com.webapp.aisha.feture.main.wallet.receivables;

import android.app.Activity;

import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.feture.main.wallet.WalletView;
import com.webapp.aisha.services.api.ApiShared;
import com.webapp.aisha.models.BankTransfer;
import com.webapp.aisha.models.Result;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.ArrayList;

public class ReceivablesWalletPresenter {

    private Activity activity;
    private WalletView<ArrayList<BankTransfer>> walletView;
    private String next_page_url = "agents/bank_transfers";
    private boolean loadingMoreItems;

    public ReceivablesWalletPresenter(Activity activity, WalletView<ArrayList<BankTransfer>> walletView) {
        this.activity = activity;
        this.walletView = walletView;
        getData();
    }

    private void getData() {
        walletView.showDialog("");
        ApiShared.getInstance().getWalletData().getBankTransfers(activity, next_page_url
                , new RequestListener<Result<ArrayList<BankTransfer>>>() {
                    @Override
                    public void onSuccess(Result<ArrayList<BankTransfer>> arrayListResult, String msg) {
                        next_page_url = arrayListResult.getPagination().getNext_page_url();
                        walletView.setData(arrayListResult.getData());
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
