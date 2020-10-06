package com.webapp.aisha.feture.registration.register.stepTwo;

import android.graphics.Bitmap;

import com.webapp.aisha.models.City;
import com.webapp.aisha.models.MainCategory;
import com.webapp.aisha.utils.listener.DialogView;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.ArrayList;

public interface RegisterStepTwoView extends DialogView, RequestListener<Bitmap> {

    void initCategory(ArrayList<MainCategory> array);

    void initCities(ArrayList<City> array);

}
