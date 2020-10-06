package com.webapp.aisha.feture.registration;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.webapp.aisha.R;
import com.webapp.aisha.feture.main.MainActivity;
import com.webapp.aisha.feture.profile.privacy.PrivacyFragment;
import com.webapp.aisha.feture.registration.login.LoginFragment;
import com.webapp.aisha.feture.registration.register.stepOne.RegisterStepOneFragment;
import com.webapp.aisha.feture.registration.register.stepTwo.RegisterStepTwoFragment;
import com.webapp.aisha.feture.registration.reset.stepOne.ResetStepOneFragment;
import com.webapp.aisha.feture.registration.reset.stepThree.ResetStepThreeFragment;
import com.webapp.aisha.feture.registration.reset.stepTwo.ResetStepTwoFragment;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.NavigateUtils;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrationActivity extends AppCompatActivity implements RegistrationView, NavigationView {

    @BindView(R.id.auth_container) ConstraintLayout authContainer;

    private LoginFragment loginFragment;
    private RegisterStepOneFragment registerStepOneFragment;
    private RegisterStepTwoFragment registerStepTwoFragment;
    private ResetStepOneFragment resetStepOneFragment;
    private ResetStepTwoFragment resetStepTwoFragment;
    private ResetStepThreeFragment resetStepThreeFragment;
    private PrivacyFragment privacyFragment;

    private RegistrationPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //butter Knife
        ButterKnife.bind(this);
        //presenter
        presenter = new RegistrationPresenter(this, this, this);
        initFragment();
    }

    private void initFragment() {
        if (getIntent().getExtras() != null) {
            navigate(getIntent().getExtras().getInt(AppContent.REGISTRATION_PAGE, AppContent.login));
        } else {
            navigate(AppContent.login);
        }
    }

    @Override
    public void navigate(int page) {
        switch (page) {
            case AppContent.login:
                loginFragment = LoginFragment.newInstance(this);
                NavigateUtils.replaceFragment(getSupportFragmentManager(), loginFragment, R.id.auth_container);
                break;
            case AppContent.register_step_1:
                registerStepOneFragment = RegisterStepOneFragment.newInstance(this);
                NavigateUtils.replaceFragment(getSupportFragmentManager(), registerStepOneFragment, R.id.auth_container);
                break;
            case AppContent.register_step_2:
                registerStepTwoFragment = RegisterStepTwoFragment.newInstance(this);
                NavigateUtils.replaceFragment(getSupportFragmentManager(), registerStepTwoFragment, R.id.auth_container);
                break;
            case AppContent.reset_step_1:
                resetStepOneFragment = ResetStepOneFragment.newInstance(this);
                NavigateUtils.replaceFragment(getSupportFragmentManager(), resetStepOneFragment, R.id.auth_container);
                break;
            case AppContent.reset_step_2:
                resetStepTwoFragment = ResetStepTwoFragment.newInstance(this);
                NavigateUtils.replaceFragment(getSupportFragmentManager(), resetStepTwoFragment, R.id.auth_container);
                break;
            case AppContent.reset_step_3:
                resetStepThreeFragment = ResetStepThreeFragment.newInstance(this);
                NavigateUtils.replaceFragment(getSupportFragmentManager(), resetStepThreeFragment, R.id.auth_container);
                break;
            case AppContent.privacy_policy:
                privacyFragment = PrivacyFragment.newInstance();
                privacyFragment.show(getSupportFragmentManager(),"");
                //NavigateUtils.replaceFragment(getSupportFragmentManager(), privacyFragment, R.id.auth_container);
                break;
            case AppContent.subscribe:
                NavigateUtils.openPay(this, AppContent.subscribe, 0);
                break;
            case AppContent.MAIN:
                NavigateUtils.fromActivityToActivityWithPage(this, MainActivity.class, true, AppContent.MAIN_PAGE, AppContent.home);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentById(R.id.auth_container) == loginFragment) {
            super.onBackPressed();
        } else if (getSupportFragmentManager().findFragmentById(R.id.auth_container) == registerStepOneFragment
                || getSupportFragmentManager().findFragmentById(R.id.auth_container) == resetStepOneFragment) {
            navigate(AppContent.login);
        } else if (getSupportFragmentManager().findFragmentById(R.id.auth_container) == registerStepTwoFragment
                || getSupportFragmentManager().findFragmentById(R.id.auth_container) == privacyFragment) {
            navigate(AppContent.register_step_1);
        } else if (getSupportFragmentManager().findFragmentById(R.id.auth_container) == resetStepTwoFragment) {
            navigate(AppContent.reset_step_1);
        } else if (getSupportFragmentManager().findFragmentById(R.id.auth_container) == resetStepThreeFragment) {
            navigate(AppContent.reset_step_2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragmentInFrame = getSupportFragmentManager().findFragmentById(R.id.auth_container);
        if (fragmentInFrame instanceof RegisterStepTwoFragment) {
            fragmentInFrame.onActivityResult(requestCode, resultCode, data);
        }
    }
}
