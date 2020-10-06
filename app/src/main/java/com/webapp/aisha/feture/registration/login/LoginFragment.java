package com.webapp.aisha.feture.registration.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.webapp.aisha.R;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.dialogs.WaitDialogFragment;
import com.webapp.aisha.utils.language.AppLanguageUtil;
import com.webapp.aisha.utils.listener.DialogView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends Fragment implements DialogView {

    @BindView(R.id.iv_bg) ImageView ivBg;
    @BindView(R.id.et_phone) EditText etPhone;
    @BindView(R.id.et_password) EditText etPassword;
    @BindView(R.id.btn_forget) TextView btnForget;
    @BindView(R.id.btn_login) Button btnLogin;
    @BindView(R.id.btn_sign_up) Button btnSignUp;

    private NavigationView navigate;
    private LoginPresenter presenter;

    public static LoginFragment newInstance(NavigationView navigationView) {
        return new LoginFragment(navigationView);
    }

    public LoginFragment(NavigationView navigate) {
        this.navigate = navigate;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        presenter = new LoginPresenter(getActivity(), navigate, this);
        return view;
    }

    @OnClick(R.id.btn_forget)
    public void forget() {
        navigate.navigate(AppContent.reset_step_1);
    }

    @OnClick(R.id.btn_login)
    public void login() {
        presenter.validateInput(etPhone, etPassword);
    }

    @OnClick(R.id.btn_sign_up)
    public void register() {
        navigate.navigate(AppContent.register_step_1);
    }

    @Override
    public void showDialog(String s) {
        WaitDialogFragment.newInstance(s).show(getFragmentManager(), "");
    }

    @Override
    public void hideDialog() {
        WaitDialogFragment.newInstance("").dismiss();
    }
}
