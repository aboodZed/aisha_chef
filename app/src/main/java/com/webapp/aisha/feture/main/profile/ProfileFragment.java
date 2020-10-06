package com.webapp.aisha.feture.main.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.webapp.aisha.R;
import com.webapp.aisha.feture.profile.ProfileActivity;
import com.webapp.aisha.feture.registration.RegistrationActivity;
import com.webapp.aisha.models.User;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.NavigateUtils;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.dialogs.LanguageDialog;
import com.webapp.aisha.utils.dialogs.WaitDialogFragment;
import com.webapp.aisha.utils.language.AppLanguageUtil;
import com.webapp.aisha.utils.listener.DialogView;
import com.webapp.aisha.utils.listener.RequestListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements DialogView {

    @BindView(R.id.iv_avatar) CircleImageView ivAvatar;
    @BindView(R.id.pb_avatar) ProgressBar pbAvatar;
    @BindView(R.id.iv_edit_profile) ImageView ivEditProfile;
    @BindView(R.id.tv_name) TextView tvName;
    @BindView(R.id.tv_rating) TextView tvRating;
    @BindView(R.id.ll_delivery_rates) LinearLayout llDeliveryRates;
    @BindView(R.id.ll_rating) LinearLayout llRating;
    @BindView(R.id.ll_reNew_subscription) LinearLayout llReNewSubscription;
    @BindView(R.id.ll_privacy) LinearLayout llPrivacy;
    @BindView(R.id.ll_language) LinearLayout llLanguage;
    @BindView(R.id.ll_contact_us) LinearLayout llContactUs;
    @BindView(R.id.ll_logout) LinearLayout llLogout;

    private ProfilePresenter presenter;
    private static NavigationView view;

    public static ProfileFragment newInstance(NavigationView navigationView) {
        view = navigationView;
        return new ProfileFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, v);
        //presenter
        presenter = new ProfilePresenter(getActivity(), view);
        setData();
        return v;
    }

    private void setData() {
        User user = AppController.getInstance().getAppSettingsPreferences().getUser();
        ToolUtils.loadImageNormal(getContext(), pbAvatar, user.getAvatar_url(), ivAvatar);
        if (AppController.getInstance().getAppSettingsPreferences().getAppLanguage().equals(AppLanguageUtil.AR)) {
            tvName.setText(user.getNames().get(0).getName());
        } else {
            tvName.setText(user.getNames().get(1).getName());
        }
        tvRating.setText(String.valueOf(user.getRating()));
    }

    @OnClick(R.id.iv_edit_profile)
    public void editProfile() {
        NavigateUtils.fromActivityToActivityWithPage(getActivity(), ProfileActivity.class,
                true, AppContent.PROFILE_PAGE, AppContent.edit_profile);
    }

    @OnClick(R.id.ll_delivery_rates)
    public void deliveryRate() {
        NavigateUtils.fromActivityToActivityWithPage(getActivity(), ProfileActivity.class,
                false, AppContent.PROFILE_PAGE, AppContent.delivery_rate_one);
    }

    @OnClick(R.id.ll_rating)
    public void rating() {
        NavigateUtils.fromActivityToActivityWithPage(getActivity(), ProfileActivity.class,
                false, AppContent.PROFILE_PAGE, AppContent.rating);
    }

    @OnClick(R.id.ll_reNew_subscription)
    public void reNewSubscription() {
        showDialog(getString(R.string.check_subscribe));
        presenter.checkSubscribe(new RequestListener<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean, String msg) {
                hideDialog();
                if (aBoolean) {
                    NavigateUtils.openPay(getActivity(), AppContent.subscribe, 0);
                } else {
                    ToolUtils.showShortToast(msg, getActivity());
                }
            }

            @Override
            public void onFail(String msg) {
                hideDialog();
            }
        });
    }

    @OnClick(R.id.ll_privacy)
    public void privacy() {
        NavigateUtils.fromActivityToActivityWithPage(getActivity(), ProfileActivity.class,
                false, AppContent.PROFILE_PAGE, AppContent.privacy_policy);
    }

    @OnClick(R.id.ll_language)
    public void language() {
        LanguageDialog languageDialog = new LanguageDialog();
        languageDialog.show(getFragmentManager(), "");
    }

    @OnClick(R.id.ll_contact_us)
    public void contact() {
        NavigateUtils.fromActivityToActivityWithPage(getActivity(), ProfileActivity.class,
                false, AppContent.PROFILE_PAGE, AppContent.contact_us);
    }

    @OnClick(R.id.ll_logout)
    public void logout() {
        AppController.getInstance().getAppSettingsPreferences().setLogin(false);
        NavigateUtils.fromActivityToActivity(getActivity(), RegistrationActivity.class, true);
    }

    @Override
    public void showDialog(String s) {
        WaitDialogFragment.newInstance(s).show(getFragmentManager(),"");
    }

    @Override
    public void hideDialog() {
        WaitDialogFragment.newInstance("").dismiss();
    }
}
