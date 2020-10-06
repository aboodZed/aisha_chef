package com.webapp.aisha.utils.language;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.webapp.aisha.R;

import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

    private int layoutRes;

    protected void setLayoutRes(int layoutRes) {
        this.layoutRes = layoutRes;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutRes);
        ButterKnife.bind(this, this);
    }

    private ProgressDialog progressDialog;

    protected void showProgressDialog(int messageId) {
        if (progressDialog != null && progressDialog.isShowing()) {
            return;
        }

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
        }

        progressDialog.setMessage(getString(messageId));
        progressDialog.show();
    }

    protected void hideProgressDialog() {
        if (progressDialog == null) {
            return;
        }

        progressDialog.dismiss();
    }

    protected void showAlertDialog(String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(R.string.button_ok, null)
                .setCancelable(false)
                .show();
    }

    protected void showAlertDialog(int messageId) {
        showAlertDialog(getString(messageId));
    }
}
