package com.webapp.aisha.feture.registration.register.stepTwo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.webapp.aisha.R;
import com.webapp.aisha.utils.dialogs.PickAddressFragment;
import com.webapp.aisha.services.api.ApiShared;
import com.webapp.aisha.models.City;
import com.webapp.aisha.models.MainCategory;
import com.webapp.aisha.models.User;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
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

public class RegisterStepTwoPresenter {

    private Activity activity;
    private NavigationView view;
    private RegisterStepTwoView registerStepTwoView;
    private PhotoTakerManager photoTakerManager;
    private Map<String, String> map = new HashMap<>();
    private MultipartBody.Part parts[] = new MultipartBody.Part[3];
    private boolean isLocationSelected;

    public RegisterStepTwoPresenter(Activity activity, NavigationView view
            , RegisterStepTwoView registerStepTwoView) {
        this.activity = activity;
        this.view = view;
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
            map.put("lat", String.valueOf(selectedLatLng.latitude));
            map.put("lng", String.valueOf(selectedLatLng.longitude));
            isLocationSelected = true;
            textView.setVisibility(View.VISIBLE);
        });
    }

    public void setMultiPart(Bitmap bitmap, int i, String s) {
        parts[i] = ToolUtils.bitmapToMultipartBodyPart(activity, bitmap, s);
    }

    public void validateInput(EditText etBankName,
                              EditText etBankNameArabic,
                              EditText etIBAN,
                              EditText etAddress,
                              ArrayList<City> cities,
                              ArrayList<MainCategory> mainCategories,
                              ArrayList<MainCategory> subCategories) {

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

        if (TextUtils.isEmpty(bankNameArabic)) {
            etBankNameArabic.setError(activity.getString(R.string.required_field));
            return;
        }

        if (TextUtils.isEmpty(bankName)) {
            etBankName.setError(activity.getString(R.string.required_field));
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

        String main_categories_id = String.valueOf(mainCategories.get(0).getId());
        for (int i = 1; i < mainCategories.size() - 1; i++) {
            main_categories_id += "," + mainCategories.get(i).getId();
        }

        String sub_categories_id = String.valueOf(subCategories.get(0).getId());
        for (int i = 1; i < subCategories.size(); i++) {
            sub_categories_id += "," + subCategories.get(i).getId();
        }

        String cities_id = String.valueOf(cities.get(0).getId());
        for (int i = 1; i < cities.size() - 1; i++) {
            cities_id += "," + cities.get(i).getId();
        }

        User user = AppController.getInstance().getAppSettingsPreferences().getUser();
        map.put("names[ar]", user.getNames().get(0).getName());
        map.put("names[en]", user.getNames().get(1).getName());
        map.put("email", user.getEmail());
        map.put("country_code", user.getCountry_code());
        map.put("mobile", user.getMobile());
        map.put("password", user.getPassword());
        map.put("city_id", String.valueOf(user.getCity().getId()));
        map.put("main_categories", main_categories_id);
        map.put("sub_categories", sub_categories_id);
        map.put("citiesCanDelivery", cities_id);
        map.put("language", AppController.getInstance().getAppSettingsPreferences().getAppLanguage());
        map.put("bank_names[en]", bankName);
        map.put("bank_names[ar]", bankNameArabic);
        map.put("iban", iban);
        map.put("address", address);

        registerStepTwo();
    }

    private void registerStepTwo() {
        registerStepTwoView.showDialog(activity.getString(R.string.sign_up));
        ApiShared.getInstance().getAuthData().register(activity, map, parts, new RequestListener<User>() {
            @Override
            public void onSuccess(User user, String msg) {
                AppController.getInstance().getAppSettingsPreferences().setUser(user);
                registerStepTwoView.hideDialog();
                ToolUtils.showLongToast(activity.getString(R.string.waiting_documentation), activity);
                view.navigate(AppContent.login);
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
