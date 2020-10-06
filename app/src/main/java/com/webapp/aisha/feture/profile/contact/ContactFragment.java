package com.webapp.aisha.feture.profile.contact;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.webapp.aisha.R;
import com.webapp.aisha.models.Config;
import com.webapp.aisha.utils.dialogs.WaitDialogFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactFragment extends Fragment implements ContactView {

    @BindView(R.id.ll_call) LinearLayout llCall;
    @BindView(R.id.tv_tel_number) TextView tvTelNumber;
    @BindView(R.id.ll_gmail) LinearLayout llGmail;
    @BindView(R.id.tv_mail_address) TextView tvMailAddress;
    @BindView(R.id.iv_facebook) ImageView ivFacebook;
    @BindView(R.id.iv_twitter) ImageView ivTwitter;
    @BindView(R.id.iv_instgram) ImageView ivInstgram;
    @BindView(R.id.iv_linkin) ImageView ivLinkin;
    @BindView(R.id.et_subject) EditText etSubject;
    @BindView(R.id.et_message) EditText etMessage;
    @BindView(R.id.btn_send) Button btnSend;

    private ContactPresenter presenter;
    private ArrayList<Config> configs;

    public static ContactFragment newInstance() {
        return new ContactFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contact, container, false);
        ButterKnife.bind(this, v);
        presenter = new ContactPresenter(getActivity(), this);
        return v;
    }

    @OnClick(R.id.ll_call)
    public void call() {
        presenter.call(tvTelNumber.getText().toString());
    }

    @OnClick(R.id.ll_gmail)
    public void gmail() {
        Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto"
                , tvMailAddress.getText().toString(), null));
        startActivity(Intent.createChooser(i, getString(R.string.send)));
    }

    @OnClick(R.id.iv_facebook)
    public void facebook() {
        for (Config config : configs) {
            if (config.getKey().equals("facebook")){
                openLink(config.getValue().toString());
            }
        }
    }

    @OnClick(R.id.iv_twitter)
    public void twitter() {
        for (Config config : configs) {
            if (config.getKey().equals("twitter")){
                openLink(config.getValue().toString());
            }
        }
    }

    @OnClick(R.id.iv_instgram)
    public void instgram() {
        for (Config config : configs) {
            if (config.getKey().equals("instagram")){
                openLink(config.getValue().toString());
            }
        }
    }

    @OnClick(R.id.iv_linkin)
    public void linkin() {
        for (Config config : configs) {
            if (config.getKey().equals("google_play")){
                openLink(config.getValue().toString());
            }
        }
    }

    private void openLink(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    @OnClick(R.id.btn_send)
    public void send() {
        presenter.sendMessage(etSubject, etMessage);
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
    public void setData(ArrayList<Config> data) {
        this.configs = data;
        for (Config config : data) {
            if (config.getKey().equals("SiteMobile")) {
                tvTelNumber.setText(config.getValue().toString());
            } else if (config.getKey().equals("SiteEmail")) {
                tvMailAddress.setText(config.getValue().toString());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        presenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}