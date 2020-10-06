package com.webapp.aisha.feture.profile.deliveryRateOne;

import android.app.Activity;
import android.util.Log;

import com.webapp.aisha.R;
import com.webapp.aisha.services.api.ApiShared;
import com.webapp.aisha.models.City;
import com.webapp.aisha.models.DayTime;
import com.webapp.aisha.models.User;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.listener.DialogView;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SetDeliveryRatePresenter {

    private Activity activity;
    private DialogView dialogView;
    private Map<String, String> map = new HashMap<>();

    public SetDeliveryRatePresenter(Activity activity, DialogView dialogView) {
        this.activity = activity;
        this.dialogView = dialogView;
    }

    public void ValidateInput(ArrayList<City> cities) {
        if (!cities.isEmpty()) {
            for (City city : cities) {
                if (city.getPrice() == 0.0) {
                    ToolUtils.showLongToast(activity.getString(R.string.required_price_field), activity);
                    return;
                }else if (city.getTimes().isEmpty()){
                    ToolUtils.showLongToast(activity.getString(R.string.required_days), activity);
                    return;
                }else if (city.getTimes().get(0).getHours().isEmpty()){
                    ToolUtils.showLongToast(activity.getString(R.string.required_hours), activity);
                    return;
                }
                map.put("cities[" + city.getId() + "][price]", String.valueOf(city.getPrice()));
                for (DayTime dayTime : city.getTimes()) {
                    for (int i = 0; i < dayTime.getHours().size(); i++) {
                        map.put("cities[" + city.getId() + "][times][" + dayTime.getDay() + "][" + i + "]"
                                , dayTime.getHours().get(i));
                    }
                }
            }
        }
        updateWorkingDay();
    }

    private void updateWorkingDay() {
        dialogView.showDialog(activity.getString(R.string.save));
        ApiShared.getInstance().getAuthData().setDeliveryTime(activity, map,
                new RequestListener<User>() {
                    @Override
                    public void onSuccess(User user, String msg) {
                        AppController.getInstance().getAppSettingsPreferences().setUser(user);
                        dialogView.hideDialog();
                        activity.onBackPressed();
                    }

                    @Override
                    public void onFail(String msg) {
                        ToolUtils.showLongToast(msg, activity);
                        dialogView.hideDialog();
                    }
                });
    }
}
