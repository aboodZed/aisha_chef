package com.webapp.aisha.feture.profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.webapp.aisha.R;
import com.webapp.aisha.feture.main.MainActivity;
import com.webapp.aisha.feture.profile.contact.ContactFragment;
import com.webapp.aisha.feture.profile.deliveryRateOne.SetDeliveryRateFragment;
import com.webapp.aisha.feture.profile.deliveryRateTwo.EditDeliveryRateFragment;
import com.webapp.aisha.feture.profile.editProfile.EditProfileFragment;
import com.webapp.aisha.feture.profile.privacy.PrivacyFragment;
import com.webapp.aisha.feture.profile.rating.RatingFragment;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.NavigateUtils;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.webapp.aisha.utils.AppContent.PROFILE_PAGE;

public class ProfileActivity extends AppCompatActivity implements NavigationView {

    @BindView(R.id.iv_back) ImageView ivBack;
    @BindView(R.id.iv_notification) ImageView ivNotification;
    @BindView(R.id.fl_profile_container) FrameLayout flProfileContainer;

    private ContactFragment contactFragment;
    private SetDeliveryRateFragment setDeliveryRateFragment;
    private EditDeliveryRateFragment editDeliveryRateFragment;
    private EditProfileFragment editProfileFragment;
    private PrivacyFragment privacyFragment;
    private RatingFragment ratingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        initFragment();
    }

    private void initFragment() {
        if (getIntent().getExtras() != null) {
            navigate(getIntent().getExtras().getInt(PROFILE_PAGE));
        } else {
            onBackPressed();
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
            case AppContent.delivery_rate_one:
                setDeliveryRateFragment = SetDeliveryRateFragment.newInstance(this);
                NavigateUtils.replaceFragment(getSupportFragmentManager(), setDeliveryRateFragment, R.id.fl_profile_container);
                break;
            case AppContent.delivery_rate_two:
                editDeliveryRateFragment = EditDeliveryRateFragment.newInstance();
                NavigateUtils.replaceFragment(getSupportFragmentManager(), editDeliveryRateFragment, R.id.fl_profile_container);
                break;
            case AppContent.rating:
                ratingFragment = RatingFragment.newInstance();
                NavigateUtils.replaceFragment(getSupportFragmentManager(), ratingFragment, R.id.fl_profile_container);
                break;
            case AppContent.edit_profile:
                editProfileFragment = EditProfileFragment.newInstance();
                NavigateUtils.replaceFragment(getSupportFragmentManager(), editProfileFragment, R.id.fl_profile_container);
                break;
            case AppContent.privacy_policy:
                privacyFragment = PrivacyFragment.newInstance();
                NavigateUtils.replaceFragment(getSupportFragmentManager(), privacyFragment, R.id.fl_profile_container);
                break;
            case AppContent.contact_us:
                contactFragment = ContactFragment.newInstance();
                NavigateUtils.replaceFragment(getSupportFragmentManager(), contactFragment, R.id.fl_profile_container);
                break;
            case AppContent.notification:
                NavigateUtils.fromActivityToActivityWithPage(this, MainActivity.class
                        , true, AppContent.MAIN_PAGE, AppContent.notification);
                break;
            case AppContent.schedule:
                NavigateUtils.fromActivityToActivityWithPage(this, MainActivity.class
                        , true, AppContent.MAIN_PAGE, AppContent.schedule);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        NavigateUtils.fromActivityToActivityWithPage(this, MainActivity.class,
                true, AppContent.MAIN_PAGE, AppContent.profile);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_profile_container);
        if (fragment instanceof EditProfileFragment) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_profile_container);
        if (fragment instanceof ContactFragment) {
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}