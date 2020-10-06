package com.webapp.aisha.feture.main.home;

import com.webapp.aisha.models.MainCategory;
import com.webapp.aisha.utils.listener.DialogView;

import java.util.ArrayList;

interface HomeView extends DialogView {
     void initRecycleView(ArrayList<MainCategory> mainCategories);
}
