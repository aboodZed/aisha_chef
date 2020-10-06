package com.webapp.aisha.feture.meals.edit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.R;
import com.webapp.aisha.feture.meals.adapter.ExtrasAdapter;
import com.webapp.aisha.feture.meals.adapter.ForAdapter;
import com.webapp.aisha.feture.meals.adapter.PhotosAdapter;
import com.webapp.aisha.models.Meal;
import com.webapp.aisha.models.MealExtra;
import com.webapp.aisha.models.MealFor;
import com.webapp.aisha.models.MealImage;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.dialogs.WaitDialogFragment;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditMealFragment extends Fragment implements RequestListener<Bitmap>, EditMealView {

    @BindView(R.id.tv_edit_type) TextView tvEditType;
    @BindView(R.id.iv_add_photo) ImageView ivAddPhoto;
    @BindView(R.id.rv_meal_photos) RecyclerView rvMealPhotos;
    @BindView(R.id.s_available) Switch sAvailable;
    @BindView(R.id.et_title) EditText etTitle;
    @BindView(R.id.et_title_in_arabic) EditText etTitleInArabic;
    //@BindView(R.id.et_price) EditText etPrice;
    @BindView(R.id.et_preparing_within) EditText etPreparingWithin;
    @BindView(R.id.et_details) EditText etDetails;
    @BindView(R.id.et_details_in_arabic) EditText etDetailsInArabic;
    @BindView(R.id.rv_extras) RecyclerView rvExtras;
    @BindView(R.id.btn_add_extra) Button btnAddExtra;
    @BindView(R.id.rv_for) RecyclerView rvFor;
    @BindView(R.id.btn_add_for) Button btnAddFor;
    @BindView(R.id.btn_publish) Button btnPublish;

    private EditMealPresenter presenter;
    private PhotosAdapter photosAdapter;
    private ExtrasAdapter extrasAdapter;
    private ForAdapter forAdapter;
    private static final String CATEGORY_ID = "category_id";
    private static final String ID = "id";
    private static final String TYPE = "type";
    private NavigationView view;
    private int category_id;
    private int id;
    private String type;
    private Meal meal;

    public static EditMealFragment newInstance(NavigationView navigationView, int category_id, int id, String type) {
        EditMealFragment editMealFragment = new EditMealFragment(navigationView);
        Bundle bundle = new Bundle();
        bundle.putInt(CATEGORY_ID, category_id);
        bundle.putInt(ID, id);
        bundle.putString(TYPE, type);
        editMealFragment.setArguments(bundle);
        return editMealFragment;
    }

    public EditMealFragment(NavigationView view) {
        this.view = view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_meal, container, false);
        ButterKnife.bind(this, v);
        presenter = new EditMealPresenter(getActivity(), view, this, this);
        initRecycleView();
        return v;
    }

    private void initRecycleView() {
        category_id = getArguments().getInt(CATEGORY_ID);
        id = getArguments().getInt(ID);
        type = getArguments().getString(TYPE);
        tvEditType.setText(type);

        photosAdapter = new PhotosAdapter(getActivity());
        extrasAdapter = new ExtrasAdapter(new ArrayList<>(), getActivity(), true);
        forAdapter = new ForAdapter(new ArrayList<>(), getActivity(), true);

        rvMealPhotos.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        rvExtras.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFor.setLayoutManager(new LinearLayoutManager(getActivity()));

        rvMealPhotos.setItemAnimator(new DefaultItemAnimator());
        rvExtras.setItemAnimator(new DefaultItemAnimator());
        rvFor.setItemAnimator(new DefaultItemAnimator());

        rvMealPhotos.setAdapter(photosAdapter);
        rvExtras.setAdapter(extrasAdapter);
        rvFor.setAdapter(forAdapter);
        presenter.getData(id);
    }

    @OnClick(R.id.iv_add_photo)
    public void addPhoto() {
        presenter.addPhoto(getFragmentManager());
    }

    @OnClick(R.id.btn_add_extra)
    public void addExtra() {
        if (extrasAdapter.getItemCount() != 0) {
            if (extrasAdapter.getMealExtras().get(extrasAdapter.getItemCount() - 1).getPrice() != 0) {
                extrasAdapter.addItem(new MealExtra());
            } else {
                ToolUtils.showLongToast(getString(R.string.fill_extra), getActivity());
            }
        } else {
            extrasAdapter.addItem(new MealExtra());
        }
    }

    @OnClick(R.id.btn_add_for)
    public void forPeople() {
        if (forAdapter.getItemCount() != 0) {
            if (forAdapter.getMealFors().get(forAdapter.getItemCount() - 1).getPrice() != 0) {
                forAdapter.addItem(new MealFor());
            } else {
                ToolUtils.showLongToast(getString(R.string.fill_people), getActivity());
            }
        } else {
            forAdapter.addItem(new MealFor());
        }
    }

    @OnClick({R.id.btn_publish})
    public void publish() {
        presenter.validateInput(photosAdapter.getList(), sAvailable, etTitle, etTitleInArabic
                , etPreparingWithin, etDetails, etDetailsInArabic, category_id
                , extrasAdapter.getMealExtras(), forAdapter.getMealFors());
    }

    @Override
    public void onSuccess(Bitmap bitmap, String msg) {
        photosAdapter.addItem(bitmap);
    }

    @Override
    public void onFail(String msg) {
        ToolUtils.showLongToast(msg, getActivity());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        presenter.onActivityForResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        presenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void setData(Meal data) {
        this.meal = data;
        setPhotos();
        sAvailable.setChecked(data.isIs_active());
        etTitle.setText(data.getName());
        etTitleInArabic.setText(data.getName());
        //etPrice.setText(String.valueOf(data.getPrice()));
        etPreparingWithin.setText(String.valueOf(data.getDuration()));
        etDetails.setText(data.getDescription());
        etDetailsInArabic.setText(data.getDescription());
        for (MealExtra mealExtra : data.getMealExtras()) {
            extrasAdapter.addItem(mealExtra);
        }
        for (MealFor mealFor : data.getMealFors()) {
            forAdapter.addItem(mealFor);
        }
    }

    private void setPhotos() {
        for (MealImage mealImage : meal.getMealImages()) {
            photosAdapter.addItem(mealImage.getFull());
        }
    }

    @Override
    public void showDialog(String s) {
        WaitDialogFragment.newInstance(s).show(getFragmentManager(), "");
    }

    @Override
    public void hideDialog() {
        WaitDialogFragment.newInstance("").dismiss();
    }
}