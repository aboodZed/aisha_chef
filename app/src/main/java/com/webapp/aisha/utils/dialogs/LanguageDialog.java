package com.webapp.aisha.utils.dialogs;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.webapp.aisha.R;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.language.AppLanguageUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LanguageDialog extends DialogFragment {

    @BindView(R.id.ib_close) ImageButton ibClose;
    @BindView(R.id.tv_arabic) TextView tvArabic;
    @BindView(R.id.tv_english) TextView tvEnglish;

    public static LanguageDialog newInstance() {
        return new LanguageDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_language, container, false);
        ButterKnife.bind(this, view);
        setData();
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

    //clicks
    @OnClick(R.id.tv_arabic)
    public void arabic() {
        AppLanguageUtil.getInstance().setAppLanguage(getContext(), "ar");
        dismiss();
        getActivity().recreate();
    }

    @OnClick(R.id.tv_english)
    public void english() {
        AppLanguageUtil.getInstance().setAppLanguage(getContext(), "en");
        dismiss();
        getActivity().recreate();
    }

    @OnClick(R.id.ib_close)
    public void cancel() {
        dismiss();
    }

    private void setData() {
        tvArabic.setBackgroundResource(R.drawable.gray_button);
        tvEnglish.setBackgroundResource(R.drawable.gray_button);
        if (AppController.getInstance().getAppSettingsPreferences().getAppLanguage().equals("ar")) {
            tvArabic.setBackgroundResource(R.drawable.yellow_button);
        } else {
            tvEnglish.setBackgroundResource(R.drawable.yellow_button);
        }
    }
}
