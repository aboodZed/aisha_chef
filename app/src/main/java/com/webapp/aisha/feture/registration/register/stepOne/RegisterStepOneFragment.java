package com.webapp.aisha.feture.registration.register.stepOne;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.webapp.aisha.R;
import com.webapp.aisha.models.City;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.dialogs.WaitDialogFragment;
import com.webapp.aisha.utils.view_adapter.SpinnerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterStepOneFragment extends Fragment implements RegisterStepOneView {

    @BindView(R.id.iv_back) ImageView ivBack;
    @BindView(R.id.et_full_name) EditText etFullName;
    @BindView(R.id.et_full_name_arabic) EditText etFullNameArabic;
    @BindView(R.id.et_email) EditText etEmail;
    @BindView(R.id.et_phone_number) EditText etPhoneNumber;
    @BindView(R.id.et_password) EditText etPassword;
    @BindView(R.id.et_conform_password) EditText etConformPassword;
    @BindView(R.id.s_area) Spinner sArea;
    @BindView(R.id.checkBox) CheckBox checkBox;
    @BindView(R.id.tv_accept_terms_conditions) TextView tvAcceptTermsConditions;
    @BindView(R.id.btn_next) Button btnNext;
    @BindView(R.id.btn_already_have_account) Button btnAlreadyHaveAccount;

    private SpinnerAdapter spinnerAdapter;
    private NavigationView view;
    private RegisterStepOnePresenter presenter;

    public static RegisterStepOneFragment newInstance(NavigationView navigationView) {
        return new RegisterStepOneFragment(navigationView);
    }

    public RegisterStepOneFragment(NavigationView view) {
        this.view = view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register_step_one, container, false);
        ButterKnife.bind(this, v);
        presenter = new RegisterStepOnePresenter(getActivity(), this, view);
        return v;
    }

    public void initSpinner(ArrayList<City> list) {
        spinnerAdapter = new SpinnerAdapter(getContext(), list);
        sArea.setAdapter(spinnerAdapter);
    }

    @OnClick(R.id.iv_back)
    public void back() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.tv_accept_terms_conditions)
    public void terms() {
        view.navigate(AppContent.privacy_policy);
    }

    @OnClick(R.id.btn_next)
    public void next() {
        presenter.validateInput(
                etFullName,
                etFullNameArabic,
                etEmail,
                etPhoneNumber,
                etPassword,
                etConformPassword,
                (City) spinnerAdapter.getItem(sArea.getSelectedItemPosition()),
                checkBox);
    }

    @OnClick(R.id.btn_already_have_account)
    public void login() {
        view.navigate(AppContent.login);
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
