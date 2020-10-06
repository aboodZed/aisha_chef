package com.webapp.aisha.utils.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.webapp.aisha.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ItemSelectImageDialog extends BottomSheetDialogFragment implements View.OnClickListener {

    @BindView(R.id.fl_background)
    FrameLayout flBackground;
    @BindView(R.id.dialog_title)
    TextView dialogTitle;
    @BindView(R.id.select_image_camera)
    TextView selectImageCamera;
    @BindView(R.id.select_image_gallery)
    TextView selectImageGallery;

    private static final String ARG_DIALOG_TITLE = "title";
    private static ItemSelectImageDialog fragment;
    private Listener mListener;

    public void setListener(Listener listener) {
        mListener = listener;
    }

    public static ItemSelectImageDialog newInstance(String title) {
        if (fragment == null) {
            fragment = new ItemSelectImageDialog();
            Bundle bundle = new Bundle();
            bundle.putString(ARG_DIALOG_TITLE, title);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_image_dialog, container, false);
        ButterKnife.bind(this, view);
        if (getArguments() != null)
            dialogTitle.setText(getArguments().getString(ARG_DIALOG_TITLE));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        flBackground.setOnClickListener(this);
        selectImageCamera.setOnClickListener(this);
        selectImageGallery.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            mListener = (Listener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.select_image_camera:
                if (mListener != null) {
                    dismiss();
                    mListener.onCameraClicked();
                }
                break;
            case R.id.select_image_gallery:
                if (mListener != null) {
                    dismiss();
                    mListener.onGalleryClicked();
                }
                break;
            case R.id.fl_background:
                dismiss();
        }
    }

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = (int) (getResources().getDisplayMetrics().widthPixels);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.transparent);
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
        setCancelable(false);
        super.onResume();
        getDialog().setOnKeyListener((dialog, keyCode, event) -> {
            if ((keyCode == android.view.KeyEvent.KEYCODE_BACK)) {
                this.dismiss();
                return true;
            } else return false;
        });
    }

    public interface Listener {
        void onGalleryClicked();

        void onCameraClicked();
    }
}