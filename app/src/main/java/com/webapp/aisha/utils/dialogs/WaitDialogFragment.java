package com.webapp.aisha.utils.dialogs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.webapp.aisha.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WaitDialogFragment extends DialogFragment {

    @BindView(R.id.tv_details)
    TextView tvDetails;

    private static final String DIALOG_TITLE = "title";
    private static WaitDialogFragment fragment;

    public static WaitDialogFragment newInstance(String title) {
        if (fragment == null) {
            fragment = new WaitDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString(DIALOG_TITLE, title);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wait, container, false);
        ButterKnife.bind(this, view);
        if (getArguments() != null)
            tvDetails.setText(getArguments().getString(DIALOG_TITLE));
        return view;
    }

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.transparent);
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
        setCancelable(false);
        super.onResume();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        fragment = null;
    }
}
