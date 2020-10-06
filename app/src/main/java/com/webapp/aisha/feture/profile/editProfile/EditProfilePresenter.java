package com.webapp.aisha.feture.profile.editProfile;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.webapp.aisha.R;
import com.webapp.aisha.services.firebase.FirebaseShared;
import com.webapp.aisha.utils.dialogs.PickAddressFragment;
import com.webapp.aisha.feture.registration.register.stepTwo.RegisterStepTwoView;
import com.webapp.aisha.services.api.ApiShared;
import com.webapp.aisha.models.City;
import com.webapp.aisha.models.MainCategory;
import com.webapp.aisha.models.User;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.PermissionUtil;
import com.webapp.aisha.utils.PhotoTakerManager;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.dialogs.ItemSelectImageDialog;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class EditProfilePresenter {

    private Activity activity;
    private RegisterStepTwoView registerStepTwoView;
    private PhotoTakerManager photoTakerManager;
    private Map<String, String> map = new HashMap<>();
    private MultipartBody.Part parts[] = new MultipartBody.Part[3];
    private boolean isLocationSelected;

    public EditProfilePresenter(Activity activity, RegisterStepTwoView registerStepTwoView) {
        this.activity = activity;
        this.registerStepTwoView = registerStepTwoView;
        photoTakerManager = new PhotoTakerManager(registerStepTwoView);
        getDataToo();
    }

    private void getDataToo() {
        registerStepTwoView.showDialog(activity.getString(R.string.get_data));
        ApiShared.getInstance().getGetData().getCities(activity, new RequestListener<ArrayList<City>>() {
            @Override
            public void onSuccess(ArrayList<City> cities, String msg) {
                registerStepTwoView.initCities(cities);
                getData();
            }

            @Override
            public void onFail(String msg) {
                ToolUtils.showLongToast(msg, activity);
                registerStepTwoView.hideDialog();
                activity.onBackPressed();
            }
        });
    }

    private void getData() {
        ApiShared.getInstance().getGetData().getCategory(activity, new RequestListener<ArrayList<MainCategory>>() {
            @Override
            public void onSuccess(ArrayList<MainCategory> mainCategories, String msg) {
                registerStepTwoView.initCategory(mainCategories);
                registerStepTwoView.hideDialog();
            }

            @Override
            public void onFail(String msg) {
                ToolUtils.showLongToast(msg, activity);
                registerStepTwoView.hideDialog();
                activity.onBackPressed();
            }
        });
    }


    public void setAvatar(FragmentManager fragmentManager) {
        ItemSelectImageDialog itemSelectImageDialog = ItemSelectImageDialog.newInstance(activity.getString(R.string.select_avatar));
        itemSelectImageDialog.show(fragmentManager, "");
        itemSelectImageDialog.setListener(new ItemSelectImageDialog.Listener() {
            @Override
            public void onGalleryClicked() {
                setGalleryImage(AppContent.REQUEST_GALLERY_AVATAR);
            }

            @Override
            public void onCameraClicked() {
                setCameraImage(AppContent.REQUEST_CAMERA_AVATAR);
            }
        });
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

    public void selectLocation(FragmentManager fragmentManager, TextView textView) {
        PickAddressFragment pickAddressFragment = PickAddressFragment.getInstance();
        pickAddressFragment.show(fragmentManager, "");
        pickAddressFragment.setListener((selectedLatLng) -> {
            setLocation(selectedLatLng.latitude, selectedLatLng.longitude, textView);
        });
    }

    public void setLocation(double lat, double lng, TextView textView) {
        map.put("lat", String.valueOf(lat));
        map.put("lng", String.valueOf(lng));
        isLocationSelected = true;
        textView.setVisibility(View.VISIBLE);
    }

    public void setMultiPart(Bitmap bitmap, int i, String s) {
        parts[i] = ToolUtils.bitmapToMultipartBodyPart(activity, bitmap, s);
    }

    public void validateInput(
            ImageView avatar,
            ImageView id,
            ImageView license,
            EditText name,
            EditText name_in_arabic,
            EditText email,
            City city,
            EditText etBankName,
            EditText etBankNameArabic,
            EditText etIBAN,
            EditText etAddress,
            ArrayList<City> cities,
            ArrayList<MainCategory> mainCategories,
            ArrayList<MainCategory> subCategories) {

        try {
            parts[0] = ToolUtils.bitmapToMultipartBodyPart(activity
                    , ToolUtils.getBitmapFromImageView(avatar), AppContent.AVATAR);
            parts[1] = ToolUtils.bitmapToMultipartBodyPart(activity
                    , ToolUtils.getBitmapFromImageView(id), AppContent.PROOF_PROFILE_IMAGE);
            parts[2] = ToolUtils.bitmapToMultipartBodyPart(activity
                    , ToolUtils.getBitmapFromImageView(license), AppContent.COMMERCIAL_CERTIFICATION);
        } catch (Exception e) {
            e.printStackTrace();
            ToolUtils.showShortToast(activity.getString(R.string.waiting_photo), activity);
            return;
        }
        String sname = name.getText().toString();
        String sname_in_arabic = name_in_arabic.getText().toString();
        String semail = email.getText().toString();
        String bankName = etBankName.getText().toString().trim();
        String bankNameArabic = etBankNameArabic.getText().toString().trim();
        String iban = etIBAN.getText().toString().trim();
        String address = etAddress.getText().toString().trim();

        for (MultipartBody.Part part : parts) {
            if (part == null) {
                ToolUtils.showLongToast(activity.getString(R.string.required_photo), activity);
                return;
            }
        }
        if (TextUtils.isEmpty(sname)) {
            name.setError(activity.getString(R.string.required_field));
            return;
        }
        if (TextUtils.isEmpty(sname_in_arabic)) {
            name_in_arabic.setError(activity.getString(R.string.required_field));
            return;
        }
        if (TextUtils.isEmpty(semail)) {
            email.setError(activity.getString(R.string.required_field));
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(semail).matches()) {
            email.setError(activity.getString(R.string.email_valid));
            return;
        }
        if (TextUtils.isEmpty(bankName)) {
            etBankName.setError(activity.getString(R.string.required_field));
            return;
        }
        if (TextUtils.isEmpty(bankNameArabic)) {
            etBankNameArabic.setError(activity.getString(R.string.required_field));
            return;
        }
        if (TextUtils.isEmpty(iban)) {
            etIBAN.setError(activity.getString(R.string.required_field));
            return;
        }
        if (!isLocationSelected) {
            ToolUtils.showLongToast(activity.getString(R.string.select_location), activity);
            return;
        }
        if (TextUtils.isEmpty(address)) {
            etAddress.setError(activity.getString(R.string.required_field));
            return;
        }
        if (cities.isEmpty()) {
            ToolUtils.showLongToast(activity.getString(R.string.required_cities), activity);
            return;
        }
        if (mainCategories.isEmpty()) {
            ToolUtils.showLongToast(activity.getString(R.string.required_main_categories), activity);
            return;
        }
        if (mainCategories.size() > 4) {
            ToolUtils.showLongToast(activity.getString(R.string.four_main_categories), activity);
            return;
        }
        if (subCategories.isEmpty()) {
            ToolUtils.showLongToast(activity.getString(R.string.required_sub_categories), activity);
            return;
        }

        String cities_id = String.valueOf(cities.get(0).getId());
        for (int i = 1; i < cities.size(); i++) {
            cities_id += "," + cities.get(i).getId();
        }

        String main_categories_id = String.valueOf(mainCategories.get(0).getId());
        for (int i = 1; i < mainCategories.size(); i++) {
            main_categories_id += "," + mainCategories.get(i).getId();
        }

        String sub_categories_id = String.valueOf(subCategories.get(0).getId());
        for (int i = 1; i < subCategories.size(); i++) {
            sub_categories_id += "," + subCategories.get(i).getId();
        }

        map.put("names[ar]", sname_in_arabic);
        map.put("names[en]", sname);
        map.put("email", semail);
        map.put("country_code", AppContent.COUNTRY_CODE);
        map.put("city_id", String.valueOf(city.getId()));
        map.put("main_categories", main_categories_id);
        map.put("sub_categories", sub_categories_id);
        map.put("citiesCanDelivery", cities_id);
        map.put("language", AppController.getInstance().getAppSettingsPreferences().getAppLanguage());
        map.put("bank_names[en]", bankName);
        map.put("bank_names[ar]", bankNameArabic);
        map.put("iban", iban);
        map.put("address", address);

        updateToken();
    }

    private void updateToken() {
        registerStepTwoView.showDialog(activity.getString(R.string.save));
        FirebaseShared.getInstance().getTokenData().generateFCMToken(
                activity, new RequestListener<String>() {
                    @Override
                    public void onSuccess(String s, String msg) {
                        map.put("device_token", s);
                        Log.e("map_profile", map.toString());
                        updateProfile();
                    }

                    @Override
                    public void onFail(String msg) {
                        ToolUtils.showLongToast(msg, activity);
                        registerStepTwoView.hideDialog();
                    }
                }
        );
    }

    private void updateProfile() {
        ApiShared.getInstance().getAuthData().updateProfile(activity, map, parts, new RequestListener<User>() {
            @Override
            public void onSuccess(User user, String msg) {
                AppController.getInstance().getAppSettingsPreferences().setUser(user);
                registerStepTwoView.hideDialog();
                activity.onBackPressed();
            }

            @Override
            public void onFail(String msg) {
                ToolUtils.showLongToast(msg, activity);
                registerStepTwoView.hideDialog();
            }
        });
    }

    public void onActivityForResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case AppContent.REQUEST_CAMERA_AVATAR:
                    photoTakerManager.processCameraPhoto(activity);
                    break;
                case AppContent.REQUEST_GALLERY_AVATAR:
                    photoTakerManager.processGalleryPhoto(activity, data);
                    break;
                case AppContent.REQUEST_CAMERA_ID:
                    photoTakerManager.processCameraPhoto(activity);
                    break;
                case AppContent.REQUEST_GALLERY_ID:
                    photoTakerManager.processGalleryPhoto(activity, data);
                    break;
                case AppContent.REQUEST_CAMERA_LICENSE:
                    photoTakerManager.processCameraPhoto(activity);
                    break;
                case AppContent.REQUEST_GALLERY_LICENSE:
                    photoTakerManager.processGalleryPhoto(activity, data);
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            photoTakerManager.deleteLastTakenPhoto();
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
