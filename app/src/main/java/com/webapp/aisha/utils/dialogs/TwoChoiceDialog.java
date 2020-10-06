package com.webapp.aisha.utils.dialogs;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.webapp.aisha.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TwoChoiceDialog extends DialogFragment {

    private static final String TITLE = "title";
    private static final String FIRST_CHOICE = "first";
    private static final String SECOND_CHOICE = "second";

    @BindView(R.id.ib_close_two) ImageButton ibClose;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.btn_second_choice) Button btnSecondChoice;
    @BindView(R.id.btn_first_choice) Button btnFirstChoice;

    private Listener listener;

    public static TwoChoiceDialog newInstance(String title, String firstChoice, String secondChoice) {
        TwoChoiceDialog fragment = new TwoChoiceDialog();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putString(FIRST_CHOICE, firstChoice);
        bundle.putString(SECOND_CHOICE, secondChoice);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_two_choice, container, false);
        ButterKnife.bind(this, view);
        if (getArguments() != null) {
            setData(getArguments().getString(TITLE), getArguments().getString(FIRST_CHOICE), getArguments().getString(SECOND_CHOICE));
        } else {
            dismiss();
        }
        return view;
    }

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.8);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.transparent);
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        setCancelable(false);
        super.onResume();
        getDialog().setOnKeyListener((dialog, keyCode, event) -> {
            if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                this.dismiss();
                return true;
            } else return false;
        });
    }


    private void setData(String title, String first, String second) {
        tvTitle.setText(title);
        btnFirstChoice.setText(first);
        btnSecondChoice.setText(second);
    }

    @OnClick(R.id.ib_close_two)
    public void close() {
        dismiss();
    }

    @OnClick(R.id.btn_first_choice)
    public void first() {
        listener.onFirstChoiceClicked();
    }

    @OnClick(R.id.btn_second_choice)
    public void second() {
        listener.onSecondChoiceClicked();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onFirstChoiceClicked();

        void onSecondChoiceClicked();
    }
}
