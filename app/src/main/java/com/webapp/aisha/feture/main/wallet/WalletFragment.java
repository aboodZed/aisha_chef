package com.webapp.aisha.feture.main.wallet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.webapp.aisha.R;
import com.webapp.aisha.feture.main.wallet.claims.ClaimWalletFragment;
import com.webapp.aisha.feture.main.wallet.process.ProcessWalletFragment;
import com.webapp.aisha.feture.main.wallet.receivables.ReceivablesWalletFragment;
import com.webapp.aisha.models.User;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.NavigateUtils;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.dialogs.WaitDialogFragment;
import com.webapp.aisha.utils.formatter.DecimalFormatterManager;
import com.webapp.aisha.utils.view_adapter.PageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WalletFragment extends Fragment implements WalletView<User> {

    @BindView(R.id.tv_balance) TextView tvBalance;
    @BindView(R.id.btn_claim) Button btnClaim;
    @BindView(R.id.btn_bank_transfer) Button btnBankTransfer;
    @BindView(R.id.tl_tabs) TabLayout tlTabs;
    @BindView(R.id.vp_wallet) ViewPager vpWallet;

    private WalletPresenter presenter;
    private NavigationView view;
    public static int page = 0;

    public static WalletFragment newInstance(NavigationView navigationView) {
        return new WalletFragment(navigationView);
    }

    public WalletFragment(NavigationView view) {
        this.view = view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wallet, container, false);
        ButterKnife.bind(this, v);
        initViewPager();
        //presenter
        presenter = new WalletPresenter(getActivity(), view, this);
        return v;
    }

    @OnClick(R.id.btn_claim)
    public void claim() {
        presenter.sendClaim();
    }

    @OnClick(R.id.btn_bank_transfer)
    public void bankTransfer() {
        presenter.bankTransfer();
    }

    private void initViewPager() {
        PageAdapter pageAdapter = new PageAdapter(getChildFragmentManager());
        pageAdapter.addFragment(ProcessWalletFragment.newInstance(view), getString(R.string.finished));
        pageAdapter.addFragment(ReceivablesWalletFragment.newInstance(view), getString(R.string.receivable));
        pageAdapter.addFragment(ClaimWalletFragment.newInstance(view), getString(R.string.claim));
        vpWallet.setAdapter(pageAdapter);
        tlTabs.setupWithViewPager(vpWallet);
        vpWallet.setCurrentItem(page);
        vpWallet.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                page = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void showDialog(String s) {
        WaitDialogFragment.newInstance(s).show(getFragmentManager(), s);
    }

    @Override
    public void hideDialog() {
        WaitDialogFragment.newInstance("").dismiss();
    }

    @Override
    public void setData(User data) {
        if (getContext() != null)
            tvBalance.setText(getString(R.string.currency) + DecimalFormatterManager
                    .getFormatterInstance().format(data.getCurrent_balance()));
        presenter.getConfig(btnClaim, btnBankTransfer);
    }
}