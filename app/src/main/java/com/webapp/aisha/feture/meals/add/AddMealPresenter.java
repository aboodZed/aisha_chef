package com.webapp.aisha.feture.meals.add;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import androidx.fragment.app.FragmentManager;

import com.webapp.aisha.R;
import com.webapp.aisha.services.api.ApiShared;
import com.webapp.aisha.models.Meal;
import com.webapp.aisha.models.MealExtra;
import com.webapp.aisha.models.MealFor;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.PermissionUtil;
import com.webapp.aisha.utils.PhotoTakerManager;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.dialogs.ItemSelectImageDialog;
import com.webapp.aisha.utils.listener.DialogView;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class AddMealPresenter {

    private Activity activity;
    private NavigationView view;
    private DialogView dialogView;
    private PhotoTakerManager photoTakerManager;
    private Map<String, String> map = new HashMap<>();
    private ArrayList<String> images = new ArrayList<>();
    private ArrayList<MultipartBody.Part> parts = new ArrayList<>();
    private int i = 0;

    public AddMealPresenter(Activity activity, NavigationView view
            , RequestListener<Bitmap> requestListener, DialogView dialogView) {
        this.activity = activity;
        this.view = view;
        this.dialogView = dialogView;
        photoTakerManager = new PhotoTakerManager(requestListener);
    }

    public void addPhoto(FragmentManager fragmentManager) {
        ItemSelectImageDialog itemSelectImageDialog = ItemSelectImageDialog
                .newInstance(activity.getString(R.string.select_photo_meal));
        itemSelectImageDialog.show(fragmentManager, "");
        itemSelectImageDialog.setListener(new ItemSelectImageDialog.Listener() {
            @Override
            public void onGalleryClicked() {
                if (!PermissionUtil.isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE, activity)) {
                    PermissionUtil.requestPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE
                            , AppContent.REQUEST_PERMISSIONS_R_W_STORAGE_CAMERA);
                } else {
                    activity.startActivityForResult(photoTakerManager.getPhotoGalleryIntent(activity)
                            , AppContent.REQUEST_GALLERY_MEAL);
                }
            }

            @Override
            public void onCameraClicked() {
                if (!PermissionUtil.isPermissionGranted(Manifest.permission.CAMERA, activity)) {
                    PermissionUtil.requestPermission(activity, Manifest.permission.CAMERA
                            , AppContent.REQUEST_PERMISSIONS_R_W_STORAGE_CAMERA);
                } else {
                    activity.startActivityForResult(photoTakerManager.getPhotoCameraIntent(activity)
                            , AppContent.REQUEST_CAMERA_MEAL);
                }
            }
        });
    }

    public void setMultiPart(Bitmap bitmap) {
        parts.add(ToolUtils.bitmapToMultipartBodyPart(activity, bitmap, "file"));
    }


    public void onActivityForResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case AppContent.REQUEST_CAMERA_MEAL:
                    photoTakerManager.processCameraPhoto(activity);
                    break;
                case AppContent.REQUEST_GALLERY_MEAL:
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

    public void validateInput(ArrayList<Bitmap> list
            , EditText etTitle
            , EditText etTitleInArabic
            , EditText etPreparingWithin
            , EditText etDetails
            , EditText etDetailsInArabic
            , int category_id
            , ArrayList<MealExtra> mealExtras
            , ArrayList<MealFor> mealFors) {

        String title = etTitle.getText().toString().trim();
        String titleInArabic = etTitleInArabic.getText().toString().trim();
        //String price = etPrice.getText().toString().trim();
        String preparingWithin = etPreparingWithin.getText().toString().trim();
        String details = etDetails.getText().toString().trim();
        String detailsInArabic = etDetailsInArabic.getText().toString().trim();
        int re = AppController.getInstance().getAppSettingsPreferences().getReservation();

        if (TextUtils.isEmpty(title)) {
            etTitle.setError(activity.getString(R.string.required_field));
            return;
        }
        if (TextUtils.isEmpty(titleInArabic)) {
            etTitleInArabic.setError(activity.getString(R.string.required_field));
            return;
        }
        /*if (TextUtils.isEmpty(price)) {
            etPrice.setError(activity.getString(R.string.required_field));
            return;
        }
        if (Integer.parseInt(price) <= 0){
            etPrice.setError(activity.getString(R.string.price_above_zero));
            return;
        }*/
        if (TextUtils.isEmpty(preparingWithin)) {
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

        if (list.isEmpty()) {
            ToolUtils.showLongToast(activity.getString(R.string.required_photo), activity);
            return;
        } else {
            for (Bitmap bitmap : list) {
                setMultiPart(bitmap);
            }
        }

        map.put("names[ar]", titleInArabic);
        map.put("names[en]", title);
        map.put("descriptions[ar]", detailsInArabic);
        map.put("descriptions[en]", details);
        map.put("category_id", String.valueOf(category_id));
        map.put("price", String.valueOf(mealFors.get(0).getPrice()));
        map.put("duration", preparingWithin);
        map.put("need_pre_order", String.valueOf(re));
        if (re == 1) {
            map.put("pre_order_days", "1");
        }

        for (int i = 0; i < mealExtras.size(); i++) {
            if (!mealExtras.get(i).getTitle_in_arabic().equals("")
                    && !mealExtras.get(i).getTitle().equals("")
                    && mealExtras.get(i).getPrice() > 0) {
                map.put("options[" + i + "][names][ar]", mealExtras.get(i).getTitle_in_arabic());
                map.put("options[" + i + "][names][en]", mealExtras.get(i).getTitle());
                map.put("options[" + i + "][price]", String.valueOf(mealExtras.get(i).getPrice()));
            }
        }

        for (int i = 0; i < mealFors.size(); i++) {
            if (!mealFors.get(i).getPeople_number().equals("")
                    && mealFors.get(i).getPrice() > 0) {
                map.put("sizes[" + i + "][people_number]", mealFors.get(i).getPeople_number());
                map.put("sizes[" + i + "][price]", String.valueOf(mealFors.get(i).getPrice()));
            }
        }
        if (!mealFors.get(0).getPeople_number().equals("")
                && mealFors.get(0).getPrice() > 0) {
            dialogView.showDialog(activity.getString(R.string.store_meal));
            uploadPhotos(list.size() - 1);
        } else {
            ToolUtils.showShortToast(activity.getString(R.string.add_for), activity);
        }
    }

    private void uploadPhotos(int b) {
        ApiShared.getInstance().getGetData().uploadPhoto(activity, parts.get(i),
                new RequestListener<String>() {
                    @Override
                    public void onSuccess(String s, String msg) {
                        images.add("\"" + s.replace("\\", "\\\\")
                                .replace("\"", "\\\"") + "\"");
                        if (i != b) {
                            ++i;
                            uploadPhotos(b);
                        } else {
                            map.put("images", images.toString());
                            store();
                        }
                    }

                    @Override
                    public void onFail(String msg) {
                        ToolUtils.showLongToast(msg, activity);
                        dialogView.hideDialog();
                    }
                });
    }

    public void store() {
        ApiShared.getInstance().getMealData().storeMeal(activity, map, new RequestListener<Meal>() {
            @Override
            public void onSuccess(Meal meal, String msg) {
                dialogView.hideDialog();
                view.navigate(AppContent.meals);
            }

            @Override
            public void onFail(String msg) {
                Log.e("abbbb", msg);
                ToolUtils.showLongToast(msg, activity);
                dialogView.hideDialog();
            }
        });
    }
}
