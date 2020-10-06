package com.webapp.aisha.feture.registration.register.stepOne;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.CheckBox;
import android.widget.EditText;

import com.webapp.aisha.R;
import com.webapp.aisha.services.api.ApiShared;
import com.webapp.aisha.models.City;
import com.webapp.aisha.models.NameInLanguage;
import com.webapp.aisha.models.User;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.language.AppLanguageUtil;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.ArrayList;

public class RegisterStepOnePresenter {

    private Activity activity;
    private RegisterStepOneView registerStepOneView;
    private NavigationView view;

    public RegisterStepOnePresenter(Activity activity, RegisterStepOneView registerStepOneView, NavigationView view) {
        this.activity = activity;
        this.registerStepOneView = registerStepOneView;
        this.view = view;
        getData();
    }

    private void getData() {
        registerStepOneView.showDialog(activity.getString(R.string.get_data));
        ApiShared.getInstance().getGetData().getCities(activity, new RequestListener<ArrayList<City>>() {
            @Override
            public void onSuccess(ArrayList<City> cities, String msg) {
                registerStepOneView.initSpinner(cities);
                registerStepOneView.hideDialog();
            }

            @Override
            public void onFail(String msg) {
                ToolUtils.showLongToast(msg, activity);
                registerStepOneView.hideDialog();
            }
        });
    }

    public void validateInput(
            EditText name,
            EditText name_in_arabic,
            EditText email,
            EditText phone,
            EditText password,
            EditText password_confirm,
            City city,
            CheckBox checked) {

        String sname = name.getText().toString();
        String sname_in_arabic = name_in_arabic.getText().toString();
        String semail = email.getText().toString();
        String sphone = phone.getText().toString();
        String spassword = password.getText().toString();
        String spassword_confirm = password_confirm.getText().toString();

        if (TextUtils.isEmpty(sname)) {
            name.setError(activity.getString(R.string.required_field));
            return;
        }
        if (TextUtils.isEmpty(sname_in_arabic)) {
            name_in_arabic.setError(activity.getString(R.string.required_field));
            return;
        }
        if (TextUtils.isEmpty(semail)) {
            email.setError(activity.getString(R.string.required_field));
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(semail).matches()) {
            email.setError(activity.getString(R.string.email_valid));
            return;
        }
        if (TextUtils.isEmpty(sphone)) {
            phone.setError(activity.getString(R.string.required_field));
            return;
        }
        if (sphone.length() != 9) {
            phone.setError(activity.getString(R.string.nine_digits));
            return;
        }
        if (TextUtils.isEmpty(spassword)) {
            password.setError(activity.getString(R.string.required_field));
            return;
        }
        if (TextUtils.isEmpty(spassword_confirm)) {
            password_confirm.setError(activity.getString(R.string.required_field));
            return;
        }
        if (!spassword.equals(spassword_confirm)) {
            password_confirm.setError(activity.getString(R.string.not_match));
            return;
        }
        if (!checked.isChecked()) {
            checked.setError(activity.getString(R.string.must_accept_terms));
            return;
        }

        ArrayList<NameInLanguage> names = new ArrayList<>();
        names.add(new NameInLanguage(AppLanguageUtil.AR, sname_in_arabic));
        names.add(new NameInLanguage(AppLanguageUtil.EN, sname));
        User user = new User(names, semail, AppContent.COUNTRY_CODE, sphone, city, spassword);
        AppController.getInstance().getAppSettingsPreferences().setUser(user);
        view.navigate(AppContent.register_step_2);
    }
}
