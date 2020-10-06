package com.webapp.aisha.feture.subscribe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.webapp.aisha.R;
import com.webapp.aisha.feture.main.MainActivity;
import com.webapp.aisha.feture.subscribe.payment.PaymentFragment;
import com.webapp.aisha.feture.subscribe.subscribe.SubscribeFragment;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.NavigateUtils;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.listener.RequestListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SubscribeActivity extends AppCompatActivity implements NavigationView {

    @BindView(R.id.iv_back) ImageView ivBack;
    @BindView(R.id.fl_subscribe_container) FrameLayout flSubscribeContainer;

    private SubscribeFragment subscribeFragment;
    private PaymentFragment paymentFragment;
    private SubscribePresenter presenter;
    private double value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);
        ButterKnife.bind(this);
        presenter = new SubscribePresenter(this, this);
        initFragment();
    }

    private void initFragment() {
        if (getIntent().getExtras() != null) {
            value = getIntent().getExtras().getDouble(AppContent.PAY_VALUE, 0);
            navigate(getIntent().getExtras().getInt(AppContent.SUBSCRIBE_PAGE, AppContent.subscribe));
        } else {
            back();
        }
    }

    @Override
    public void navigate(int page) {
        switch (page) {
            case AppContent.subscribe:
                subscribeFragment = SubscribeFragment.newInstance(this);
                NavigateUtils.replaceFragment(getSupportFragmentManager(), subscribeFragment, R.id.fl_subscribe_container);
                break;
            case AppContent.payment:
                paymentFragment = PaymentFragment.newInstance(this, value);
                NavigateUtils.replaceFragment(getSupportFragmentManager(), paymentFragment, R.id.fl_subscribe_container);
                break;
            case AppContent.home:
                NavigateUtils.fromActivityToActivity(this, MainActivity.class, true);
                break;
            case AppContent.schedule:
                NavigateUtils.fromActivityToActivityWithPage(this, MainActivity.class
                        , true, AppContent.MAIN_PAGE, AppContent.schedule);
                break;
            case AppContent.wallet:
                NavigateUtils.fromActivityToActivityWithPage(this, MainActivity.class
                        , true, AppContent.MAIN_PAGE, AppContent.wallet);
                break;
        }
    }

    @OnClick(R.id.iv_back)
    public void back() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_subscribe_container);
        if (fragment == paymentFragment) {
            navigate(AppContent.subscribe);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_subscribe_container);
        if (fragment instanceof PaymentFragment) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }else if(fragment instanceof SubscribeFragment) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}