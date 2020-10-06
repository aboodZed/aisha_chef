package com.webapp.aisha.feture.meals.meals;

import com.webapp.aisha.models.Meal;
import com.webapp.aisha.utils.listener.DialogView;

import java.util.ArrayList;

interface MealsView extends DialogView {

    void setMeals(ArrayList<Meal> meals);
}
