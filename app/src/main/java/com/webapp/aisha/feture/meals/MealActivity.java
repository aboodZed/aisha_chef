package com.webapp.aisha.feture.meals;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.webapp.aisha.R;
import com.webapp.aisha.feture.main.MainActivity;
import com.webapp.aisha.feture.meals.add.AddMealFragment;
import com.webapp.aisha.feture.meals.edit.EditMealFragment;
import com.webapp.aisha.feture.meals.meals.MealsFragment.ViewMealListener;
import com.webapp.aisha.feture.meals.meals.MealsFragment;
import com.webapp.aisha.feture.profile.ProfileActivity;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.NavigateUtils;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MealActivity extends AppCompatActivity implements NavigationView, ViewMealListener {

    @BindView(R.id.iv_back) ImageView ivBack;
    @BindView(R.id.iv_notification) ImageView ivNotification;
    @BindView(R.id.fl_meals_container) FrameLayout flMealsContainer;

    private MealPresenter presenter;
    private EditMealFragment editMealFragment;
    private AddMealFragment addMealFragment;
    private MealsFragment mealsFragment;
    private int category_id;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);
        ButterKnife.bind(this);
        presenter = new MealPresenter(this, this);
        initFragment();
    }

    private void initFragment() {
        if (getIntent().getExtras() != null) {
            category_id = getIntent().getExtras().getInt(AppContent.CATEGORY_ID);
            type = getIntent().getExtras().getString(AppContent.MEALS_TYPE);
            navigate(getIntent().getExtras().getInt(AppContent.MEALS_PAGE));
        } else {
            onBackPressed();
        }
    }

    @OnClick(R.id.iv_back)
    public void back() {
        onBackPressed();
    }

    @OnClick(R.id.iv_notification)
    public void notification() {
        navigate(AppContent.notification);
    }

    @Override
    public void navigate(int page) {
        switch (page) {
            case AppContent.meals:
                mealsFragment = MealsFragment.newInstance(this, category_id, this);
                NavigateUtils.replaceFragment(getSupportFragmentManager(), mealsFragment, R.id.fl_meals_container);
                break;
            case AppContent.add_meal:
                addMealFragment = AddMealFragment.newInstance(this, category_id);
                NavigateUtils.replaceFragment(getSupportFragmentManager(), addMealFragment, R.id.fl_meals_container);
                break;
            case AppContent.edit_meal:
                NavigateUtils.replaceFragment(getSupportFragmentManager(), editMealFragment, R.id.fl_meals_container);
                break;
            case AppContent.home:
                NavigateUtils.fromActivityToActivity(this, MainActivity.class, true);
                break;
            case AppContent.notification:
                NavigateUtils.fromActivityToActivityWithPage(this, MainActivity.class
                        , true, AppContent.MAIN_PAGE, AppContent.notification);
                break;
            case AppContent.delivery_rate_one:
                NavigateUtils.fromActivityToActivityWithPage(this, ProfileActivity.class
                        , true, AppContent.PROFILE_PAGE, AppContent.delivery_rate_one);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_meals_container);
        if (fragment == addMealFragment || fragment == editMealFragment) {
            navigate(AppContent.meals);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_meals_container);
        if (fragment instanceof EditMealFragment) {
            fragment.onActivityResult(requestCode, resultCode, data);
        } else if (fragment instanceof AddMealFragment) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void viewMeal(int id) {
        editMealFragment = EditMealFragment.newInstance(this, category_id, id, type);
        navigate(AppContent.edit_meal);
    }
}