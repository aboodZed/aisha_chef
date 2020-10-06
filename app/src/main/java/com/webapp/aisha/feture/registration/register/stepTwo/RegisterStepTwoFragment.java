package com.webapp.aisha.feture.registration.register.stepTwo;

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
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.R;
import com.webapp.aisha.feture.profile.editProfile.CategoryListener;
import com.webapp.aisha.feture.registration.adapter.CitiesAdapter;
import com.webapp.aisha.feture.registration.adapter.RegisterAdapter;
import com.webapp.aisha.models.City;
import com.webapp.aisha.models.MainCategory;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.dialogs.WaitDialogFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterStepTwoFragment extends Fragment implements RegisterStepTwoView, CategoryListener {

    @BindView(R.id.iv_avatar) CircleImageView ivAvatar;
    @BindView(R.id.pb_avatar) ProgressBar pbAvatar;
    @BindView(R.id.iv_camera_id) ImageView ivCameraId;
    @BindView(R.id.iv_upload_id) ImageView ivUploadId;
    @BindView(R.id.iv_selected_id) ImageView ivSelectedId;
    @BindView(R.id.iv_camera_license) ImageView ivCameraLicense;
    @BindView(R.id.iv_upload_license) ImageView ivUploadLicense;
    @BindView(R.id.iv_selected_license) ImageView ivSelectedLicense;
    @BindView(R.id.et_bank_in_arabic) EditText etBankInArabic;
    @BindView(R.id.et_bank_in_english) EditText etBankInEnglish;
    @BindView(R.id.et_ibna) EditText etIbna;
    @BindView(R.id.iv_location) ImageView ivLocation;
    @BindView(R.id.tv_selected_location) TextView tvSelectedLocation;
    @BindView(R.id.et_address) EditText etAddress;
    @BindView(R.id.rv_serve) RecyclerView rvServe;
    @BindView(R.id.rv_category) RecyclerView rvCategory;
    @BindView(R.id.rv_sub_category) RecyclerView rvSubCategory;
    @BindView(R.id.btn_sign_up) Button btnSignUp;

    private NavigationView view;
    private RegisterStepTwoPresenter presenter;
    private RegisterAdapter mainCategory;
    private RegisterAdapter subCategory;
    private CitiesAdapter serves;
    private int i = 0;

    public static RegisterStepTwoFragment newInstance(NavigationView navigationView) {
        return new RegisterStepTwoFragment(navigationView);
    }

    public RegisterStepTwoFragment(NavigationView view) {
        this.view = view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register_step_two, container, false);
        ButterKnife.bind(this, v);
        presenter = new RegisterStepTwoPresenter(getActivity(), view, this);
        return v;
    }


    //on clicks
    @OnClick(R.id.iv_back)
    public void back() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.iv_avatar)
    public void setIvAvatar() {
        i = 0;
        presenter.setAvatar(getFragmentManager());
    }

    @OnClick(R.id.iv_camera_id)
    public void setIvCameraId() {
        i = 1;
        presenter.setCameraImage(AppContent.REQUEST_CAMERA_ID);
    }

    @OnClick(R.id.iv_upload_id)
    public void setIvUploadId() {
        i = 1;
        presenter.setGalleryImage(AppContent.REQUEST_GALLERY_ID);
    }

    @OnClick(R.id.iv_camera_license)
    public void setIvCameraLicense() {
        i = 2;
        presenter.setCameraImage(AppContent.REQUEST_CAMERA_LICENSE);
    }

    @OnClick(R.id.iv_upload_license)
    public void setIvUploadLicense() {
        i = 2;
        presenter.setGalleryImage(AppContent.REQUEST_GALLERY_LICENSE);
    }

    @OnClick(R.id.iv_location)
    public void setIvLocation() {
        presenter.selectLocation(getFragmentManager(), tvSelectedLocation);
    }

    @OnClick(R.id.btn_sign_up)
    public void signUp() {
        presenter.validateInput(etBankInEnglish, etBankInArabic, etIbna, etAddress
                , serves.getSelected(), mainCategory.getSelected(), subCategory.getSelected());
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
    public void initCategory(ArrayList<MainCategory> array) {
        mainCategory = new RegisterAdapter(getActivity(), array, new ArrayList<>(), true);
        subCategory = new RegisterAdapter(getActivity(), array, new ArrayList<>(), false);

        rvCategory.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        rvCategory.setItemAnimator(new DefaultItemAnimator());
        rvCategory.setAdapter(mainCategory);

        rvSubCategory.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        rvSubCategory.setItemAnimator(new DefaultItemAnimator());
        rvSubCategory.setAdapter(subCategory);

        mainCategory.setCategoryListener(this::isSelected);
        subCategory.setCategoryListener(this::isSelected);
    }

    @Override
    public void initCities(ArrayList<City> array) {
        serves = new CitiesAdapter(array, new ArrayList<>());
        rvServe.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        rvServe.setItemAnimator(new DefaultItemAnimator());
        rvServe.setAdapter(serves);
    }


    @Override
    public void onSuccess(Bitmap bitmap, String msg) {
        if (bitmap != null) {
            switch (i) {
                case 0:
                    ivAvatar.setImageBitmap(bitmap);
                    presenter.setMultiPart(bitmap, i, AppContent.AVATAR);
                    break;
                case 1:
                    ivSelectedId.setImageBitmap(bitmap);
                    presenter.setMultiPart(bitmap, i, AppContent.PROOF_PROFILE_IMAGE);
                    break;
                case 2:
                    ivSelectedLicense.setImageBitmap(bitmap);
                    presenter.setMultiPart(bitmap, i, AppContent.COMMERCIAL_CERTIFICATION);
                    break;
            }
        } else {
            ToolUtils.showLongToast(getString(R.string.error_in_process_the_photo), getActivity());
        }
    }

    @Override
    public void onFail(String msg) {
        ToolUtils.showLongToast(msg, getActivity());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        presenter.onActivityForResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        presenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean isSelected(MainCategory category) {
        for (MainCategory mainCategory1 : mainCategory.getSelected()) {
            if (mainCategory1.getId() == category.getId()) {
                ToolUtils.showShortToast(getString(R.string.is_selected_before_main), getActivity());
                return true;
            }
        }
        for (MainCategory mainCategory1 : subCategory.getSelected()) {
            if (mainCategory1.getId() == category.getId()) {
                ToolUtils.showShortToast(getString(R.string.is_selected_before_sub), getActivity());
                return true;
            }
        }
        return false;
    }
}
