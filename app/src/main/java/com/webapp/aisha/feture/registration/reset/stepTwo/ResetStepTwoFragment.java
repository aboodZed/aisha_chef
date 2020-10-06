package com.webapp.aisha.feture.registration.reset.stepTwo;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.poovam.pinedittextfield.SquarePinField;
import com.webapp.aisha.R;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetStepTwoFragment extends Fragment {
    @BindView(R.id.iv_back) ImageView ivBack;
    @BindView(R.id.spf_pin) SquarePinField spfPin;
    @BindView(R.id.tv_resend_text) TextView tvResendText;
    @BindView(R.id.btn_resend) TextView btnResend;

    private NavigationView view;
    private ResetStepTwoPresenter presenter;
    private CountDownTimer countDownTimer;

    public static ResetStepTwoFragment newInstance(NavigationView navigationView) {
        return new ResetStepTwoFragment(navigationView);
    }

    public ResetStepTwoFragment(NavigationView view) {
        this.view = view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reset_step_two, container, false);
        ButterKnife.bind(this, v);
        presenter = new ResetStepTwoPresenter(getActivity(),view);
        setData();
        return v;
    }

    private void setData() {
        spfPin.setOnTextCompleteListener(s -> {
            presenter.setCode(s);
            countDownTimer.cancel();
            spfPin.setText("");
            return false;
        });
        countDownTimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                tvResendText.setText(getString(R.string.resend_string) + " " + (millisUntilFinished / 1000) + " " + getString(R.string.sec));
            }

            public void onFinish() {
                btnResend.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    @OnClick(R.id.iv_back)
    public void back() {
        getActivity().onBackPressed();
    }

    @OnClick({R.id.tv_resend_text})
    public void reSend(){
        spfPin.setText("");
        getActivity().onBackPressed();
    }
}
