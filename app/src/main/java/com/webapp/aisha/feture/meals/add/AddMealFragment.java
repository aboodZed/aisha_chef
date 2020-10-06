package com.webapp.aisha.feture.meals.add;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.R;
import com.webapp.aisha.feture.main.MainActivity;
import com.webapp.aisha.feture.meals.adapter.ExtrasAdapter;
import com.webapp.aisha.feture.meals.adapter.ForAdapter;
import com.webapp.aisha.feture.meals.adapter.PhotosAdapter;
import com.webapp.aisha.feture.profile.ProfileActivity;
import com.webapp.aisha.models.MealExtra;
import com.webapp.aisha.models.MealFor;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.NavigateUtils;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.dialogs.WaitDialogFragment;
import com.webapp.aisha.utils.listener.DialogView;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddMealFragment extends Fragment implements RequestListener<Bitmap>, DialogView {

    @BindView(R.id.tv_edit_type) TextView tvEditType;
    @BindView(R.id.iv_add_photo) ImageView ivAddPhoto;
    @BindView(R.id.rv_meal_photos) RecyclerView rvMealPhotos;
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

    private AddMealPresenter presenter;
    private ExtrasAdapter extrasAdapter;
    private ForAdapter forAdapter;
    private PhotosAdapter photosAdapter;
    private NavigationView view;
    private static final String ID = "id";
    private int id;

    public static AddMealFragment newInstance(NavigationView navigationView, int id) {
        AddMealFragment addMealFragment = new AddMealFragment(navigationView);
        Bundle bundle = new Bundle();
        bundle.putInt(ID, id);
        addMealFragment.setArguments(bundle);
        return addMealFragment;
    }

    public AddMealFragment(NavigationView view) {
        this.view = view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_meal, container, false);
        ButterKnife.bind(this, v);
        presenter = new AddMealPresenter(getActivity(), view, this, this);
        id = getArguments().getInt(ID);
        checkReservation();
        initRecycleView();
        return v;
    }

    private void checkReservation() {
        if (AppController.getInstance().getAppSettingsPreferences()
                .getUser().getCities_can_delivery().isEmpty()) {
            NavigateUtils.fromActivityToActivityWithPage(getActivity(), ProfileActivity.class,
                    false, AppContent.PROFILE_PAGE, AppContent.delivery_rate_one);
        }
    }

    private void initRecycleView() {
        photosAdapter = new PhotosAdapter(getActivity());
        extrasAdapter = new ExtrasAdapter(new ArrayList<>(), getActivity(), false);
        forAdapter = new ForAdapter(new ArrayList<>(), getActivity(), false);

        rvMealPhotos.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        rvExtras.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFor.setLayoutManager(new LinearLayoutManager(getActivity()));

        rvMealPhotos.setItemAnimator(new DefaultItemAnimator());
        rvExtras.setItemAnimator(new DefaultItemAnimator());
        rvFor.setItemAnimator(new DefaultItemAnimator());

        rvMealPhotos.setAdapter(photosAdapter);
        rvExtras.setAdapter(extrasAdapter);
        rvFor.setAdapter(forAdapter);

        extrasAdapter.addItem(new MealExtra());
        forAdapter.addItem(new MealFor());
    }

    @OnClick(R.id.iv_add_photo)
    public void addPhoto() {
        presenter.addPhoto(getFragmentManager());
    }

    @OnClick(R.id.btn_add_extra)
    public void addExtra() {
        if (extrasAdapter.getItemCount() > 0) {
            if (extrasAdapter.getMealExtras().get(extrasAdapter.getItemCount() - 1).getPrice() > 0) {
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
        if (forAdapter.getItemCount() > 0) {
            if (forAdapter.getMealFors().get(forAdapter.getItemCount() - 1).getPrice() != 0) {
                forAdapter.addItem(new MealFor());
            } else {
                ToolUtils.showLongToast(getString(R.string.fill_people), getActivity());
            }
        } else {
            forAdapter.addItem(new MealFor());
        }
    }

    @OnClick(R.id.btn_publish)
    public void publish() {
        presenter.validateInput(photosAdapter.getList(), etTitle, etTitleInArabic
                , etPreparingWithin, etDetails, etDetailsInArabic, id
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
    public void showDialog(String s) {
        WaitDialogFragment.newInstance(s).show(getFragmentManager(), "");
    }

    @Override
    public void hideDialog() {
        WaitDialogFragment.newInstance("").dismiss();
    }
}