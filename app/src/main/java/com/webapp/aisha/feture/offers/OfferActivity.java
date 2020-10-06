package com.webapp.aisha.feture.offers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.webapp.aisha.R;
import com.webapp.aisha.feture.main.MainActivity;
import com.webapp.aisha.feture.offers.add.AddOfferFragment;
import com.webapp.aisha.feture.offers.offers.OffersFragment;
import com.webapp.aisha.feture.offers.offers.OffersFragment.ViewOfferListener;
import com.webapp.aisha.feture.offers.view.ViewOfferFragment;
import com.webapp.aisha.models.Offer;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.NavigateUtils;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OfferActivity extends AppCompatActivity implements NavigationView, ViewOfferListener {

    @BindView(R.id.iv_back) ImageView ivBack;
    @BindView(R.id.iv_notification) ImageView ivNotification;
    @BindView(R.id.fl_offers_container) FrameLayout flOffersContainer;

    private OfferPresenter presenter;
    private OffersFragment offersFragment;
    private AddOfferFragment addOfferFragment;
    private ViewOfferFragment viewOfferFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        ButterKnife.bind(this);
        presenter = new OfferPresenter(this, this);
        initFragment();
    }

    private void initFragment() {
        if (getIntent().getExtras() != null) {
            navigate(getIntent().getExtras().getInt(AppContent.OFFER_PAGE));
        } else {
            navigate(AppContent.offers);
        }
    }

    @OnClick(R.id.iv_back)
    public void back() {
        onBackPressed();
    }

    @OnClick(R.id.iv_notification)
    public void notification() {
        navigate(AppContent.notification);
    }

    @Override
    public void navigate(int page) {
        switch (page) {
            case AppContent.offers:
                offersFragment = OffersFragment.newInstance(this, this);
                NavigateUtils.replaceFragment(getSupportFragmentManager(), offersFragment, R.id.fl_offers_container);
                break;
            case AppContent.view_offer:
                NavigateUtils.replaceFragment(getSupportFragmentManager(), viewOfferFragment, R.id.fl_offers_container);
                break;
            case AppContent.add_offer:
                addOfferFragment = AddOfferFragment.newInstance(this);
                NavigateUtils.replaceFragment(getSupportFragmentManager(), addOfferFragment, R.id.fl_offers_container);
                break;
            case AppContent.notification:
                NavigateUtils.fromActivityToActivityWithPage(this, MainActivity.class
                        , true, AppContent.MAIN_PAGE, AppContent.notification);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentById(R.id.fl_offers_container) == viewOfferFragment
                || getSupportFragmentManager().findFragmentById(R.id.fl_offers_container) == addOfferFragment) {
            navigate(AppContent.offers);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_offers_container);
        if (fragment instanceof AddOfferFragment) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void viewOffer(Offer offer) {
        viewOfferFragment = ViewOfferFragment.newInstance(this, offer.getId());
        navigate(AppContent.view_offer);
    }
}