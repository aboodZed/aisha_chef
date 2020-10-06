package com.webapp.aisha.feture.registration.register.stepOne;

import com.webapp.aisha.models.City;
import com.webapp.aisha.utils.listener.DialogView;

import java.util.ArrayList;

public interface RegisterStepOneView  extends DialogView {

    void initSpinner(ArrayList<City> cities);

}
