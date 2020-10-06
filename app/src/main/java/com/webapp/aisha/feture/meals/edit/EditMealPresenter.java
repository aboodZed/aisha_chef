package com.webapp.aisha.feture.meals.edit;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Switch;

import androidx.fragment.app.FragmentManager;

import com.webapp.aisha.R;
import com.webapp.aisha.feture.main.MainActivity;
import com.webapp.aisha.services.api.ApiShared;
import com.webapp.aisha.models.Meal;
import com.webapp.aisha.models.MealExtra;
import com.webapp.aisha.models.MealFor;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.NavigateUtils;
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

public class EditMealPresenter {

    private Activity activity;
    private NavigationView view;
    private EditMealView editMealView;
    private PhotoTakerManager photoTakerManager;
    private Map<String, String> map = new HashMap<>();
    private ArrayList<MultipartBody.Part> parts = new ArrayList<>();
    private int i = 0, e = 0, p = 0;
    private ArrayList<String> images = new ArrayList<>();
    private int id, category_id;

    public EditMealPresenter(Activity activity, NavigationView view
            , RequestListener<Bitmap> requestListener, EditMealView editMealView) {
        this.activity = activity;
        this.view = view;
        this.editMealView = editMealView;
        photoTakerManager = new PhotoTakerManager(requestListener);
    }

    public void getData(int id) {
        this.id = id;
        editMealView.showDialog(activity.getString(R.string.get_data));
        ApiShared.getInstance().getMealData().getMeal(activity, id, new RequestListener<Meal>() {
            @Override
            public void onSuccess(Meal meal, String msg) {
                editMealView.setData(meal);
                editMealView.hideDialog();
            }

            @Override
            public void onFail(String msg) {
                ToolUtils.showLongToast(msg, activity);
                editMealView.hideDialog();
            }
        });
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
            , Switch sAvailable
            , EditText etTitle
            , EditText etTitleInArabic
            , EditText etPreparingWithin
            , EditText etDetails
            , EditText etDetailsInArabic
            , int category_id
            , ArrayList<MealExtra> mealExtras
            , ArrayList<MealFor> mealFors) {

        this.category_id = category_id;
        String title = etTitle.getText().toString().trim();
        String titleInArabic = etTitleInArabic.getText().toString().trim();
        //String price = etPrice.getText().toString().trim();
        String preparingWithin = etPreparingWithin.getText().toString().trim();
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
        /*if (TextUtils.isEmpty(price)) {
            etPrice.setError(activity.getString(R.string.required_field));
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
        int re;
        if (category_id != 0) {
            re = AppController.getInstance().getAppSettingsPreferences().getReservation();
        } else {
            re = 0;
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
        map.put("pre_order_days", String.valueOf(re - 1));

        if (sAvailable.isChecked()) {
            map.put("status", "1");
        } else {
            map.put("status", "0");
        }

        for (int j = 0; j < mealExtras.size(); j++) {
            if (mealExtras.get(j).getId() != 0) {
                map.put("options[" + j + "][id]", String.valueOf(mealExtras.get(j).getId()));
                map.put("options[" + j + "][names][ar]", mealExtras.get(j).getTitle());
                map.put("options[" + j + "][names][en]", mealExtras.get(j).getTitle());
                map.put("options[" + j + "][price]", String.valueOf(mealExtras.get(j).getPrice()));
            } else {
                map.put("new_options[" + e + "][names][ar]", mealExtras.get(j).getTitle());
                map.put("new_options[" + e + "][names][en]", mealExtras.get(j).getTitle());
                map.put("new_options[" + e + "][price]", String.valueOf(mealExtras.get(j).getPrice()));
                ++e;
            }
        }

        for (int j = 0; j < mealFors.size(); j++) {
            if (mealFors.get(j).getId() != 0) {
                map.put("sizes[" + j + "][id]", String.valueOf(mealFors.get(j).getId()));
                map.put("sizes[" + j + "][people_number]", mealFors.get(j).getPeople_number());
                map.put("sizes[" + j + "][price]", String.valueOf(mealFors.get(j).getPrice()));
            } else {
                map.put("new_sizes[" + p + "][people_number]", mealFors.get(j).getPeople_number());
                map.put("new_sizes[" + p + "][price]", String.valueOf(mealFors.get(j).getPrice()));
                ++p;
            }
        }

        editMealView.showDialog(activity.getString(R.string.update_meal));
        uploadPhotos(list.size() - 1);
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
                            update();
//                            if (category_id != 0) {
//                                update();
//                            } else {
//                                toDayDish();
//                            }
                        }
                    }

                    @Override
                    public void onFail(String msg) {
                        ToolUtils.showLongToast(msg, activity);
                        editMealView.hideDialog();
                    }
                });
    }

    private void update() {
        ApiShared.getInstance().getMealData().updateMeal(activity, id, map
                , new RequestListener<Meal>() {
                    @Override
                    public void onSuccess(Meal meal, String msg) {
                        editMealView.hideDialog();
                        view.navigate(AppContent.meals);
                    }

                    @Override
                    public void onFail(String msg) {
                        editMealView.hideDialog();
                        ToolUtils.showLongToast(msg, activity);
                    }
                });
    }
//
//    private void toDayDish() {
//        Log.e("fghfhfggh", map.toString());
//        ApiShared.getInstance().getMealData().storeMeal(activity, map,
//                new RequestListener<Meal>() {
//                    @Override
//                    public void onSuccess(Meal meal, String msg) {
//                        editMealView.hideDialog();
//                        view.navigate(AppContent.home);
//                    }
//
//                    @Override
//                    public void onFail(String msg) {
//                        ToolUtils.showLongToast(msg, activity);
//                        editMealView.hideDialog();
//                    }
//                });
//    }
}
