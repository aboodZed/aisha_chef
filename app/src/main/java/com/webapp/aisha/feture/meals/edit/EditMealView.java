package com.webapp.aisha.feture.meals.edit;

import com.webapp.aisha.models.Meal;
import com.webapp.aisha.utils.listener.DialogView;

public interface EditMealView extends DialogView {

    void setData(Meal data);
}
