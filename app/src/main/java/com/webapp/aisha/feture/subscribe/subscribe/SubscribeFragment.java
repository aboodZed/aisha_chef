package com.webapp.aisha.feture.subscribe.subscribe;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.webapp.aisha.R;
import com.webapp.aisha.models.Subscribe;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.dialogs.ItemSubscribe;
import com.webapp.aisha.utils.dialogs.WaitDialogFragment;
import com.webapp.aisha.utils.view_adapter.KKViewPager;
import com.webapp.aisha.utils.view_adapter.PageAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SubscribeFragment extends Fragment implements SubscribeView, ItemSubscribe.Listener {

    @BindView(R.id.vp_subscribe) KKViewPager vpSubscribe;
    @BindView(R.id.ll_bottom) LinearLayout llBottom;
    @BindView(R.id.rb_bank_transfer) RadioButton rbBankTransfer;
    @BindView(R.id.rb_credit_card) RadioButton rbCreditCard;

    private SubscribePresenter presenter;
    private NavigationView view;
    private PageAdapter pageAdapter;

    public static SubscribeFragment newInstance(NavigationView navigationView) {
        return new SubscribeFragment(navigationView);
    }

    public SubscribeFragment(NavigationView view) {
        this.view = view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_subscribe, container, false);
        ButterKnife.bind(this, v);
        presenter = new SubscribePresenter(getActivity(), view, this);
        return v;
    }

    @OnClick(R.id.rb_bank_transfer)
    public void bank() {
        view.navigate(AppContent.payment);
    }

    @OnClick(R.id.rb_credit_card)
    public void creditCard() {
        presenter.creditCard();
    }

    @Override
    public void setDataInPager(ArrayList<Subscribe> subscribes) {
        pageAdapter = new PageAdapter(getChildFragmentManager());
        for (Subscribe subscribe : subscribes) {
            ItemSubscribe itemSubscribe = ItemSubscribe.newInstance(subscribe, this);
            pageAdapter.addFragment(itemSubscribe, "");
        }
        vpSubscribe.setAdapter(pageAdapter);
        vpSubscribe.setAnimationEnabled(true);
        vpSubscribe.setFadeEnabled(true);
        vpSubscribe.setFadeFactor(0.5f);
    }

    @Override
    public void showDialog(String s) {
        WaitDialogFragment.newInstance(s).show(getFragmentManager(), "");
    }

    @Override
    public void hideDialog() {
        WaitDialogFragment.newInstance("").dismiss();
    }

    @Override
    public void selected(Subscribe subscribe) {
        AppController.getInstance().getAppSettingsPreferences().setSubscribe(subscribe);
        llBottom.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResult(requestCode, resultCode, data);
    }
}
