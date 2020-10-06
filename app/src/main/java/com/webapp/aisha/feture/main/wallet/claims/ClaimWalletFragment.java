package com.webapp.aisha.feture.main.wallet.claims;

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
import com.webapp.aisha.feture.main.wallet.adapter.ClaimWalletAdapter;
import com.webapp.aisha.models.Claim;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClaimWalletFragment extends Fragment implements WalletView<ArrayList<Claim>> {

    @BindView(R.id.tv_claim_wallet) RecyclerView rvWallet;
    @BindView(R.id.fl_load_more) FrameLayout flLoadMore;

    private ClaimWalletAdapter adapter;
    private NavigationView view;
    private ClaimWalletPresenter presenter;

    public static ClaimWalletFragment newInstance(NavigationView navigationView) {
        return new ClaimWalletFragment(navigationView);
    }

    public ClaimWalletFragment(NavigationView view) {
        this.view = view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_claim_wallet, container, false);
        ButterKnife.bind(this, view);
        initRecycleView();
        presenter = new ClaimWalletPresenter(getActivity(),this);
        return view;
    }

    private void initRecycleView() {
        adapter = new ClaimWalletAdapter(getActivity());
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
    public void setData(ArrayList<Claim> data) {
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