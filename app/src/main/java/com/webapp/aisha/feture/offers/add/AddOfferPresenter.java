package com.webapp.aisha.feture.offers.add;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.EditText;

import com.webapp.aisha.R;
import com.webapp.aisha.feture.main.MainActivity;
import com.webapp.aisha.services.api.ApiShared;
import com.webapp.aisha.models.Offer;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.NavigateUtils;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.PermissionUtil;
import com.webapp.aisha.utils.PhotoTakerManager;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;

import static android.app.Activity.RESULT_OK;

public class AddOfferPresenter {

    private Activity activity;
    private NavigationView view;
    private AddOfferView addOfferView;
    private PhotoTakerManager photoTakerManager;
    private Map<String, String> map = new HashMap<>();
    private MultipartBody.Part part;

    public AddOfferPresenter(Activity activity, NavigationView view, AddOfferView addOfferView) {
        this.activity = activity;
        this.view = view;
        this.addOfferView = addOfferView;
        photoTakerManager = new PhotoTakerManager(addOfferView);
    }

    public void setCameraImage(int requestNum) {
        if (!PermissionUtil.isPermissionGranted(Manifest.permission.CAMERA, activity)) {
            PermissionUtil.requestPermission(activity, Manifest.permission.CAMERA
                    , AppContent.REQUEST_PERMISSIONS_R_W_STORAGE_CAMERA);
        } else {
            activity.startActivityForResult(photoTakerManager.getPhotoCameraIntent(activity), requestNum);
        }
    }

    public void setGalleryImage(int requestNum) {
        if (!PermissionUtil.isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE, activity)) {
            PermissionUtil.requestPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE
                    , AppContent.REQUEST_PERMISSIONS_R_W_STORAGE_CAMERA);
        } else {
            activity.startActivityForResult(photoTakerManager.getPhotoGalleryIntent(activity), requestNum);
        }
    }

    public void setMultiPart(Bitmap bitmap, String s) {
        part = ToolUtils.bitmapToMultipartBodyPart(activity, bitmap, s);
    }

    public void validateInput(EditText etTitle, EditText etTitleInArabic, EditText etPrice
            , EditText etPreparingWithin, EditText etDetails, EditText etDetailsInArabic) {

        String title = etTitle.getText().toString().trim();
        String titleInArabic = etTitleInArabic.getText().toString().trim();
        String price = etPrice.getText().toString().trim();
        String preparing = etPreparingWithin.getText().toString().trim();
        String details = etDetails.getText().toString().trim();
        String detailsInArabic = etDetailsInArabic.getText().toString().trim();

        if (TextUtils.isEmpty(title)) {
            etTitle.setError(activity.getString(R.string.required_field));
            return;
        }

        if (TextUtils.isEmpty(titleInArabic)) {
            etTitleInArabic.setError(activity.getString(R.string.required_field));
            return;
        }

        if (TextUtils.isEmpty(price)) {
            etPrice.setError(activity.getString(R.string.required_field));
            return;
        }

        if (Integer.parseInt(price) <= 0) {
            etPrice.setError(activity.getString(R.string.price_above_zero));
            return;
        }

        if (TextUtils.isEmpty(preparing)) {
            etPreparingWithin.setError(activity.getString(R.string.required_field));
            return;
        }

        if (TextUtils.isEmpty(details)) {
            etDetails.setError(activity.getString(R.string.required_field));
            return;
        }

        if (TextUtils.isEmpty(detailsInArabic)) {
            etDetailsInArabic.setError(activity.getString(R.string.required_field));
            return;
        }
        int re = AppController.getInstance().getAppSettingsPreferences().getReservation();

        map.put("names[en]", title);
        map.put("names[ar]", titleInArabic);
        map.put("price", price);
        map.put("duration", preparing);
        map.put("descriptions[en]", details);
        map.put("descriptions[ar]", detailsInArabic);
        map.put("need_pre_order", String.valueOf(re));
        map.put("pre_order_days", String.valueOf(re - 1));

        publish();
    }

    private void publish() {
        addOfferView.showDialog(activity.getString(R.string.publish));
        ApiShared.getInstance().getOfferData().storeOffer(activity, map, part
                , new RequestListener<Offer>() {
                    @Override
                    public void onSuccess(Offer offer, String msg) {
                        addOfferView.hideDialog();
                        view.navigate(AppContent.offers);
                    }

                    @Override
                    public void onFail(String msg) {
                        ToolUtils.showLongToast(msg, activity);
                        addOfferView.hideDialog();
                    }
                });
    }

    public void onActivityForResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case AppContent.REQUEST_CAMERA_OFFER:
                    photoTakerManager.processCameraPhoto(activity);
                    break;
                case AppContent.REQUEST_GALLERY_OFFER:
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
