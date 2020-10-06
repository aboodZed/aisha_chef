package com.webapp.aisha.feture.subscribe.payment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.R;
import com.webapp.aisha.feture.subscribe.adapter.BankAdapter;
import com.webapp.aisha.models.Bank;
import com.webapp.aisha.models.NameInLanguage;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.dialogs.WaitDialogFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.webapp.aisha.utils.language.AppLanguageUtil.AR;

public class PaymentFragment extends Fragment implements PaymentView {

    @BindView(R.id.rv_banks) RecyclerView rvBanks;
    @BindView(R.id.et_to_bank_name) EditText etToBankName;
    @BindView(R.id.et_to_account_number) EditText etToAccountNumber;
    @BindView(R.id.et_to_account_name) EditText etToAccountName;
    @BindView(R.id.et_to_iban) EditText etToIban;
    @BindView(R.id.et_from_account_name) EditText etFromAccountName;
    @BindView(R.id.et_from_bank_name) EditText etFromBankName;
    @BindView(R.id.et_from_ibna) EditText etFromIbna;
    @BindView(R.id.iv_camera_bank_transfer) ImageView ivCameraBankTransfer;
    @BindView(R.id.iv_upload_bank_transfer) ImageView ivUploadBankTransfer;
    @BindView(R.id.iv_selected_bank_transfer) ImageView ivSelectedBankTransfer;
    @BindView(R.id.btn_confirm) Button btnConfirm;

    private PaymentPresenter presenter;
    private NavigationView view;
    private static final String VALUE = "value";
    private double value = 0;
    private BankAdapter bankAdapter;

    public static PaymentFragment newInstance(NavigationView navigationView, double value) {
        PaymentFragment paymentFragment = new PaymentFragment(navigationView);
        Bundle bundle = new Bundle();
        bundle.putDouble(VALUE, value);
        paymentFragment.setArguments(bundle);
        return paymentFragment;
    }

    public PaymentFragment(NavigationView view) {
        this.view = view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_payment, container, false);
        ButterKnife.bind(this, v);
        if (getArguments() != null) {
            value = getArguments().getDouble(VALUE, 0);
            presenter = new PaymentPresenter(getActivity(), view, this, value);
        }
        return v;
    }

    @OnClick(R.id.iv_camera_bank_transfer)
    public void camera() {
        presenter.setCameraImage();
    }

    @OnClick(R.id.iv_upload_bank_transfer)
    public void upload() {
        presenter.setGalleryImage();
    }

    @OnClick(R.id.btn_confirm)
    public void confirm() {
        presenter.ValidateInput(etToBankName, etToAccountNumber, etToAccountName, etToIban
                , etFromAccountName, etFromBankName, etFromIbna);
    }

    @Override
    public void setData(ArrayList<Bank> data) {
        bankAdapter = new BankAdapter(getActivity(), data);
        rvBanks.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rvBanks.setItemAnimator(new DefaultItemAnimator());
        rvBanks.setAdapter(bankAdapter);
        bankAdapter.setBankListener(bank -> {
            etToBankName.setText(bank.getBank_name());
            etToAccountName.setText(bank.getAccount_name());
            etToAccountNumber.setText(bank.getAccount_number());
            etToIban.setText(bank.getIban());
            for (NameInLanguage nameInLanguage : AppController.getInstance()
                    .getAppSettingsPreferences().getUser().getBank_names()) {
                if (nameInLanguage.getLanguage_code().equals(AppController
                        .getInstance().getAppSettingsPreferences().getAppLanguage())) {
                    etFromBankName.setText(nameInLanguage.getName());
                }
            }
            etFromIbna.setText(AppController.getInstance().getAppSettingsPreferences().getUser().getIban());

        });
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
    public void onSuccess(Bitmap bitmap, String msg) {
        ivSelectedBankTransfer.setImageBitmap(bitmap);
        presenter.setMultiPart(bitmap);
    }

    @Override
    public void onFail(String msg) {
        ToolUtils.showLongToast(msg, getActivity());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        presenter.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        presenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
