package com.webapp.aisha.feture.profile.privacy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.webapp.aisha.R;
import com.webapp.aisha.models.PageData;
import com.webapp.aisha.utils.dialogs.WaitDialogFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PrivacyFragment extends DialogFragment implements PrivacyView {

    @BindView(R.id.tv_privacy) TextView tvPrivacy;
    @BindView(R.id.fl_load) FrameLayout flLoad;
    private PrivacyPresenter presenter;

    public static PrivacyFragment newInstance() {
        return new PrivacyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_privacy, container, false);
        ButterKnife.bind(this, v);
        presenter = new PrivacyPresenter(getActivity(), this);
        return v;
    }

    @Override
    public void setData(ArrayList<PageData> data) {
        for (PageData pageData : data) {
            if (pageData.getPage_code().equals("privacy_policy")){
                tvPrivacy.setText(pageData.getContent());
            }
        }
    }

    @Override
    public void showDialog(String s) {
        flLoad.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDialog() {
        flLoad.setVisibility(View.GONE);
    }
}