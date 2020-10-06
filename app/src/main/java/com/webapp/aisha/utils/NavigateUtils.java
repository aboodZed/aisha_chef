package com.webapp.aisha.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.webapp.aisha.feture.meals.MealActivity;
import com.webapp.aisha.feture.order.OrderActivity;
import com.webapp.aisha.feture.subscribe.SubscribeActivity;


public class NavigateUtils {

    public static void replaceFragment(FragmentManager fragmentManager, Fragment fragment, int layout) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(layout, fragment);
        fragmentTransaction.commit();
    }

    public static void fromActivityToActivity(Context from, Class to, boolean doNotAllowToBack) {
        Intent intent = new Intent(from, to);
        if (doNotAllowToBack)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        from.startActivity(intent);
    }

    public static void fromActivityToActivityWithPage(Context from, Class to, boolean doNotAllowToBack, String key, int page) {
        Intent intent = new Intent(from, to);
        intent.putExtra(key, page);
        if (doNotAllowToBack)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        from.startActivity(intent);
    }

    public static void openOrder(Context from, int page, int orderId, boolean allowBack) {
        Intent intent = new Intent(from, OrderActivity.class);
        intent.putExtra(AppContent.ORDER_PAGE, page);
        intent.putExtra(AppContent.ORDER_ID, orderId);
        if (!allowBack) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        from.startActivity(intent);
    }

    public static void openCategory(Activity from, int page, int categoryId, String type) {
        Intent intent = new Intent(from, MealActivity.class);
        intent.putExtra(AppContent.MEALS_PAGE, page);
        intent.putExtra(AppContent.CATEGORY_ID, categoryId);
        intent.putExtra(AppContent.MEALS_TYPE, type);
        from.startActivity(intent);
    }

    public static void openPay(Activity from, int page, double value) {
        Intent intent = new Intent(from, SubscribeActivity.class);
        intent.putExtra(AppContent.SUBSCRIBE_PAGE, page);
        intent.putExtra(AppContent.PAY_VALUE, value);
        from.startActivity(intent);
    }

    public interface NavigationView {
        void navigate(int page);
    }
}