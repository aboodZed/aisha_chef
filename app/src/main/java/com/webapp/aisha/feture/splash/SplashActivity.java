package com.webapp.aisha.feture.splash;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.webapp.aisha.R;

import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //hide state bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        //ButterKnife
        ButterKnife.bind(this);
        //presenter
        SplashPresenter presenter = new SplashPresenter(this);
        presenter.splash();
    }
}
