package com.webapp.aisha.feture.subscribe.payment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.Gson;
import com.webapp.aisha.R;
import com.webapp.aisha.services.api.ApiShared;
import com.webapp.aisha.models.Bank;
import com.webapp.aisha.models.BankTransfer;
import com.webapp.aisha.models.Config;
import com.webapp.aisha.models.User;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.PermissionUtil;
import com.webapp.aisha.utils.PhotoTakerManager;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;

import static android.app.Activity.RESULT_OK;

public class PaymentPresenter {

    private Activity activity;
    private NavigationView view;
    private PaymentView paymentView;
    private PhotoTakerManager photoTakerManager;
    private MultipartBody.Part part;
    private Map<String, String> map = new HashMap<>();
    private double value;

    public PaymentPresenter(Activity activity, NavigationView view
            , PaymentView paymentView, double value) {
        this.activity = activity;
        this.view = view;
        this.paymentView = paymentView;
        this.value = value;
        photoTakerManager = new PhotoTakerManager(paymentView);
        getData();
    }

    private void getData() {
        paymentView.showDialog(activity.getString(R.string.get_data));
        ApiShared.getInstance().getGetData().getConfig(activity, new RequestListener<ArrayList<Config>>() {
            @Override
            public void onSuccess(ArrayList<Config> configs, String msg) {
                for (Config v : configs) {
                    if (v.getKey().equals("bank_accounts")) {
                        Gson gson = new Gson();
                        String json = gson.toJson(v.getValue());
                        ArrayList banks = gson.fromJson(json, ArrayList.class);
                        ArrayList<Bank> banks1 = new ArrayList<>();
                        for (Object bank : banks) {
                            String j = gson.toJson(bank);
                            Bank bank1 = gson.fromJson(j, Bank.class);
                            banks1.add(bank1);
                        }
                        paymentView.setData(banks1);
                    }
                }
                paymentView.hideDialog();
            }

            @Override
            public void onFail(String msg) {
                paymentView.hideDialog();
            }
        });
    }

    public void setMultiPart(Bitmap bitmap) {
        part = ToolUtils.bitmapToMultipartBodyPart(activity, bitmap, "receipt_img");
    }


    public void setCameraImage() {
        if (!PermissionUtil.isPermissionGranted(Manifest.permission.CAMERA, activity)) {
            PermissionUtil.requestPermission(activity, Manifest.permission.CAMERA
                    , AppContent.REQUEST_PERMISSIONS_R_W_STORAGE_CAMERA);
        } else {
            activity.startActivityForResult(photoTakerManager.getPhotoCameraIntent(activity)
                    , AppContent.REQUEST_CAMERA_PAYMENT);
        }

    }

    public void setGalleryImage() {
        if (!PermissionUtil.isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE, activity)) {
            PermissionUtil.requestPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE
                    , AppContent.REQUEST_PERMISSIONS_R_W_STORAGE_CAMERA);
        } else {
            activity.startActivityForResult(photoTakerManager.getPhotoGalleryIntent(activity)
                    , AppContent.REQUEST_GALLERY_PAYMENT);
        }

    }


    public void ValidateInput(EditText etToBankName, EditText etToAccountNumber, EditText etToAccountName
            , EditText etToIban, EditText etFromAccountName, EditText etFromBankName, EditText etFromIbna) {

        String toBankName = etToBankName.getText().toString().trim();
        String toAccountNumber = etToAccountNumber.getText().toString().trim();
        String toAccountName = etToAccountName.getText().toString().trim();
        String toIban = etToIban.getText().toString().trim();
        String fromAccountName = etFromAccountName.getText().toString().trim();
        String fromBankName = etFromBankName.getText().toString().trim();
        String fromIbna = etFromIbna.getText().toString().trim();

        if (TextUtils.isEmpty(fromAccountName)) {
            etFromAccountName.setError(activity.getString(R.string.required_field));
            return;
        }
        if (TextUtils.isEmpty(fromBankName)) {
            etFromBankName.setError(activity.getString(R.string.required_field));
            return;
        }
        if (TextUtils.isEmpty(fromIbna)) {
            etFromIbna.setError(activity.getString(R.string.required_field));
            return;
        }
        if (part == null) {
            ToolUtils.showLongToast(activity.getString(R.string.required_photo), activity);
            return;
        }

        map.put("full_name", fromAccountName);
        map.put("account_number", toAccountNumber);
        map.put("bank_name", fromBankName);
        map.put("iban", fromIbna);

        if (value == 0) {
            pay();
        } else {
            map.put("amount", String.valueOf(value));
            payForAmount();
        }
    }

    private void pay() {
        paymentView.showDialog(activity.getString(R.string.bank_transfer));
        ApiShared.getInstance().getPackageData().bankTransfer(activity
                , AppController.getInstance().getAppSettingsPreferences().getSubscribe().getId()
                , map, part
                , new RequestListener<User>() {
                    @Override
                    public void onSuccess(User user, String msg) {
                        AppController.getInstance().getAppSettingsPreferences().setUser(user);
                        AppController.getInstance().getAppSettingsPreferences().setLogin(true);
                        paymentView.hideDialog();
                        view.navigate(AppContent.schedule);
                    }

                    @Override
                    public void onFail(String msg) {
                        paymentView.hideDialog();
                    }
                });
    }

    private void payForAmount() {
        paymentView.showDialog(activity.getString(R.string.bank_transfer));
        ApiShared.getInstance().getWalletData().sendBakTransfer(activity, map, part
                , new RequestListener<BankTransfer>() {
                    @Override
                    public void onSuccess(BankTransfer bankTransfer, String msg) {
                        paymentView.hideDialog();
                        ToolUtils.showLongToast(activity.getString(R.string.waiting_accept_bank_transfer), activity);
                        view.navigate(AppContent.wallet);
                    }

                    @Override
                    public void onFail(String msg) {
                        paymentView.hideDialog();
                    }
                });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case AppContent.REQUEST_CAMERA_PAYMENT:
                    photoTakerManager.processCameraPhoto(activity);
                    break;
                case AppContent.REQUEST_GALLERY_PAYMENT:
                    photoTakerManager.processGalleryPhoto(activity, data);
                    break;
            }
        } else {
            ToolUtils.showLongToast(activity.getString(R.string.permission_denial), activity);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case AppContent.REQUEST_PERMISSIONS_R_W_STORAGE_CAMERA:
                    ToolUtils.showLongToast(activity.getString(R.string.permission_granted), activity);
                    break;
            }
        } else {
            ToolUtils.showLongToast(activity.getString(R.string.permission_denial), activity);
        }
    }
}
