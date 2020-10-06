package com.webapp.aisha.services.api;

import com.webapp.aisha.services.api.fun.AuthData;
import com.webapp.aisha.services.api.fun.GetData;
import com.webapp.aisha.services.api.fun.MealData;
import com.webapp.aisha.services.api.fun.NotificationData;
import com.webapp.aisha.services.api.fun.OfferData;
import com.webapp.aisha.services.api.fun.OrderData;
import com.webapp.aisha.services.api.fun.PackageData;
import com.webapp.aisha.services.api.fun.SubscribeData;
import com.webapp.aisha.services.api.fun.WalletData;
import com.webapp.aisha.services.api.fun.WorkingTimeData;

public class ApiShared {

    private static final ApiShared instance = new ApiShared();

    public static ApiShared getInstance(){
        return instance;
    }

    private AuthData authData;
    private GetData getData;
    private OfferData offerData;
    private PackageData packageData;
    private MealData mealData;
    private OrderData orderData;
    private WalletData walletData;
    private WorkingTimeData workingTimeData;
    private SubscribeData subscribeData;
    private NotificationData notificationData;

    public ApiShared() {
        authData = new AuthData();
        getData = new GetData();
        offerData = new OfferData();
        packageData = new PackageData();
        mealData = new MealData();
        orderData = new OrderData();
        walletData = new WalletData();
        workingTimeData = new WorkingTimeData();
        subscribeData = new SubscribeData();
        notificationData = new NotificationData();
    }

    public AuthData getAuthData() {
        return authData;
    }

    public GetData getGetData() {
        return getData;
    }

    public OfferData getOfferData() {
        return offerData;
    }

    public PackageData getPackageData() {
        return packageData;
    }

    public MealData getMealData() {
        return mealData;
    }

    public OrderData getOrderData() {
        return orderData;
    }

    public WalletData getWalletData() {
        return walletData;
    }

    public WorkingTimeData getWorkingTimeData() {
        return workingTimeData;
    }

    public SubscribeData getSubscribeData() {
        return subscribeData;
    }

    public NotificationData getNotificationData() {
        return notificationData;
    }
}
