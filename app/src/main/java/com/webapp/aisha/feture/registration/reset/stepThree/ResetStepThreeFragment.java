package com.webapp.aisha.feture.registration.reset.stepThree;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.webapp.aisha.R;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.dialogs.WaitDialogFragment;
import com.webapp.aisha.utils.listener.DialogView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetStepThreeFragment extends Fragment implements DialogView {

    @BindView(R.id.iv_back) ImageView ivBack;
    @BindView(R.id.et_password) EditText etPassword;
    @BindView(R.id.et_conform_password) EditText etConformPassword;
    @BindView(R.id.btn_refresh) Button btnRefresh;

    private NavigationView view;
    private ResetStepThreePresenter presenter;

    public static ResetStepThreeFragment newInstance(NavigationView navigationView) {
        return new ResetStepThreeFragment(navigationView);
    }

    public ResetStepThreeFragment(NavigationView view) {
        this.view = view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reset_step_three, container, false);
        ButterKnife.bind(this, v);
        presenter = new ResetStepThreePresenter(getActivity(), view,this);
        return v;
    }

    @OnClick(R.id.iv_back)
    public void back() {
        getActivity().onBackPressed();
    }

    @OnClick({R.id.btn_refresh})
    public void refresh() {
        presenter.validateInput(etPassword, etConformPassword);
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
