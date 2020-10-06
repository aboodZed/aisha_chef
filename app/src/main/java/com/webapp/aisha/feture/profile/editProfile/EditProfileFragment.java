package com.webapp.aisha.feture.profile.editProfile;

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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.R;
import com.webapp.aisha.feture.registration.adapter.CitiesAdapter;
import com.webapp.aisha.feture.registration.adapter.RegisterAdapter;
import com.webapp.aisha.feture.registration.register.stepTwo.RegisterStepTwoView;
import com.webapp.aisha.models.City;
import com.webapp.aisha.models.MainCategory;
import com.webapp.aisha.models.User;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.dialogs.ChangePasswordDialog;
import com.webapp.aisha.utils.dialogs.WaitDialogFragment;
import com.webapp.aisha.utils.view_adapter.SpinnerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditProfileFragment extends Fragment implements RegisterStepTwoView, CategoryListener {

    @BindView(R.id.iv_avatar) ImageView ivAvatar;
    @BindView(R.id.pb_avatar) ProgressBar pbAvatar;
    @BindView(R.id.ll_changePassword) LinearLayout llChangePassword;
    @BindView(R.id.et_full_name) EditText etFullName;
    @BindView(R.id.et_full_name_arabic) EditText etFullNameArabic;
    @BindView(R.id.et_email) EditText etEmail;
    @BindView(R.id.s_area) Spinner sArea;
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
    @BindView(R.id.btn_save) Button btnSave;

    private RegisterAdapter category, subCategory;
    private CitiesAdapter serve;
    private EditProfilePresenter presenter;
    private int i;

    public static EditProfileFragment newInstance() {
        return new EditProfileFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        ButterKnife.bind(this, v);
        presenter = new EditProfilePresenter(getActivity(), this);
        setData();
        return v;
    }

    //functions
    private void setData() {
        User user = AppController.getInstance().getAppSettingsPreferences().getUser();
        ToolUtils.loadImageNormal(getContext(), pbAvatar, user.getAvatar_url(), ivAvatar);
        ToolUtils.loadImageNormal(getContext(), null, user.getProof_profile_image_url(), ivSelectedId);
        ToolUtils.loadImageNormal(getContext(), null, user.getCommercial_certification_url(), ivSelectedLicense);
        etFullNameArabic.setText(user.getNames().get(0).getName());
        etFullName.setText(user.getNames().get(1).getName());
        etEmail.setText(user.getEmail());
        etBankInArabic.setText(user.getBank_names().get(0).getName());
        etBankInEnglish.setText(user.getBank_names().get(1).getName());
        etIbna.setText(user.getIban());
        etAddress.setText(user.getAddress());
        presenter.setLocation(user.getLat(), user.getLng(), tvSelectedLocation);
    }

    @OnClick(R.id.ll_changePassword)
    public void changePassword() {
        ChangePasswordDialog changePasswordDialog = new ChangePasswordDialog();
        changePasswordDialog.show(getFragmentManager(), "change password");
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

    @OnClick(R.id.btn_save)
    public void save() {
        presenter.validateInput(ivAvatar, ivSelectedId, ivSelectedLicense,
                etFullName, etFullNameArabic, etEmail, (City) sArea.getSelectedItem()
                , etBankInEnglish, etBankInArabic, etIbna, etAddress, serve.getSelected()
                , category.getSelected(), subCategory.getSelected());
    }

    @Override
    public void initCategory(ArrayList<MainCategory> array) {
        category = new RegisterAdapter(getActivity(), array, AppController.getInstance()
                .getAppSettingsPreferences().getUser().getMain_categories(), true);

        subCategory = new RegisterAdapter(getActivity(), array, AppController.getInstance()
                .getAppSettingsPreferences().getUser().getSub_categories(), false);

        rvCategory.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        rvCategory.setItemAnimator(new DefaultItemAnimator());
        rvCategory.setAdapter(category);

        rvSubCategory.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        rvSubCategory.setItemAnimator(new DefaultItemAnimator());
        rvSubCategory.setAdapter(subCategory);

        category.setCategoryListener(this);
        subCategory.setCategoryListener(this);
    }

    @Override
    public void initCities(ArrayList<City> array) {
        serve = new CitiesAdapter(array, AppController.getInstance()
                .getAppSettingsPreferences().getUser().getCities_can_delivery());

        rvServe.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        rvServe.setItemAnimator(new DefaultItemAnimator());
        rvServe.setAdapter(serve);
        initSpinner(array);
    }


    private void initSpinner(ArrayList<City> list) {
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getContext(), list);
        sArea.setAdapter(spinnerAdapter);
        int id = AppController.getInstance().getAppSettingsPreferences().getUser().getCity().getId();
        for (int j = 0; j < list.size(); j++) {
            if (list.get(j).getId() == id) {
                sArea.setSelection(j);
            }
        }
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
    public boolean isSelected(MainCategory mainCategory) {
        for (MainCategory mainCategory1 : category.getSelected()) {
            if (mainCategory1.getId() == mainCategory.getId()) {
                ToolUtils.showShortToast(getString(R.string.is_selected_before_main), getActivity());
                return true;
            }
        }
        for (MainCategory mainCategory1 : subCategory.getSelected()) {
            if (mainCategory1.getId() == mainCategory.getId()) {
                ToolUtils.showShortToast(getString(R.string.is_selected_before_sub), getActivity());
                return true;
            }
        }
        return false;
    }
}