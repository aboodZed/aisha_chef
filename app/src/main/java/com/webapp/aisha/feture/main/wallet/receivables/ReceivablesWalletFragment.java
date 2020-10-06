package com.webapp.aisha.feture.main.wallet.receivables;

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
import com.webapp.aisha.feture.main.wallet.WalletView;
import com.webapp.aisha.feture.main.wallet.adapter.ReceivableWalletAdapter;
import com.webapp.aisha.models.BankTransfer;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReceivablesWalletFragment extends Fragment implements WalletView<ArrayList<BankTransfer>> {

    @BindView(R.id.tv_receivable_wallet) RecyclerView rvWallet;
    @BindView(R.id.fl_load_more) FrameLayout flLoadMore;

    private ReceivableWalletAdapter adapter;
    private NavigationView view;
    private ReceivablesWalletPresenter presenter;

    public static ReceivablesWalletFragment newInstance(NavigationView navigationView) {
        return new ReceivablesWalletFragment(navigationView);
    }

    public ReceivablesWalletFragment(NavigationView view) {
        this.view = view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receivables_wallet, container, false);
        ButterKnife.bind(this, view);
        initRecycleView();
        presenter = new ReceivablesWalletPresenter(getActivity(), this);
        return view;
    }

    private void initRecycleView() {
        adapter = new ReceivableWalletAdapter(getActivity());
        rvWallet.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvWallet.setItemAnimator(new DefaultItemAnimator());
        rvWallet.setAdapter(adapter);
        rvWallet.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                presenter.onScrollStateChanged(recyclerView);
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    @Override
    public void setData(ArrayList<BankTransfer> data) {
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