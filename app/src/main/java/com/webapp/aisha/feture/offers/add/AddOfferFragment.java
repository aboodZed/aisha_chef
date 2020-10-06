package com.webapp.aisha.feture.offers.add;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.webapp.aisha.R;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.dialogs.WaitDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddOfferFragment extends Fragment implements AddOfferView {

    @BindView(R.id.iv_camera_offer) ImageView ivCameraOffer;
    @BindView(R.id.iv_upload_offer) ImageView ivUploadOffer;
    @BindView(R.id.iv_selected_offer) ImageView ivSelectedOffer;
    @BindView(R.id.et_title) EditText etTitle;
    @BindView(R.id.et_title_in_arabic) EditText etTitleInArabic;
    @BindView(R.id.et_price) EditText etPrice;
    @BindView(R.id.et_preparing_within) EditText etPreparingWithin;
    @BindView(R.id.et_details) EditText etDetails;
    @BindView(R.id.et_details_in_arabic) EditText etDetailsInArabic;
    @BindView(R.id.btn_publish) Button btnPublish;

    private AddOfferPresenter presenter;
    private NavigationView view;

    public static AddOfferFragment newInstance(NavigationView navigationView) {
        return new AddOfferFragment(navigationView);
    }

    public AddOfferFragment(NavigationView view) {
        this.view = view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_offer, container, false);
        ButterKnife.bind(this, v);
        presenter = new AddOfferPresenter(getActivity(), view, this);
        return v;
    }

    @OnClick(R.id.iv_camera_offer)
    public void camera() {
        presenter.setCameraImage(AppContent.REQUEST_CAMERA_OFFER);
    }

    @OnClick(R.id.iv_upload_offer)
    public void upload() {
        presenter.setGalleryImage(AppContent.REQUEST_GALLERY_OFFER);
    }

    @OnClick(R.id.btn_publish)
    public void publish() {
        presenter.validateInput(etTitle, etTitleInArabic, etPrice
                , etPreparingWithin, etDetails, etDetailsInArabic);
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
        ivSelectedOffer.setImageBitmap(bitmap);
        presenter.setMultiPart(bitmap, AppContent.IMAGE);
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
}