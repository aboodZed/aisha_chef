package com.webapp.aisha.utils;

import java.util.LinkedHashSet;
import java.util.Set;

public interface AppContent {
    String APP_NAME = "Aisha Chef";
    String APP_TYPE = "agent";

    String COUNTRY_CODE = "972";

    //api
    String BASE_URL = "https://3zayem.com/api/";

    int TEN_MILLIE_SECOND = 10;
    int ONE_SECOND = 1000;

    String accept = "accepted";
    String pending = "pending";

    //photo manager
    String FILE_PROVIDER_AUTHORITY = "com.webapp.aisha.provider";
    String FILE_PROVIDER_PATH = "/Android/data/com.webapp.aisha/files/Pictures";

    //registration
    String REGISTRATION_PAGE = "registrationPage";
    int login = 2001;
    int register_step_1 = 2002;
    int register_step_2 = 2003;
    int reset_step_1 = 2004;
    int reset_step_2 = 2005;
    int reset_step_3 = 2006;
    int MAIN = 2007;

    //main
    String MAIN_PAGE = "mainPage";
    int home = 2011;
    int wallet = 2012;
    int orders = 2013;
    int schedule = 2014;
    int profile = 2015;
    int notification = 2016;

    //offers
    String OFFER_PAGE = "offer_page";
    int offers = 2020;
    int view_offer = 2021;
    int add_offer = 2022;

    //meals
    String MEALS_PAGE = "meals_page";
    String CATEGORY_ID = "category_id";
    String MEALS_TYPE = "meals_type";
    int meals = 2030;
    int add_meal = 2031;
    int edit_meal = 2032;

    //edit meal types
    String today_dish_type = "Today's Dish editing";
    String edit_meal_type = "Meal editing";

    //profile
    String PROFILE_PAGE = "profile_page";
    int delivery_rate_one = 2040;
    int delivery_rate_two = 2041;
    int edit_profile = 2042;
    int rating = 2043;
    int privacy_policy = 2044;
    int contact_us = 2045;

    //subscribe
    String SUBSCRIBE_PAGE = "subscribe_page";
    int subscribe = 2050;
    int payment = 2051;
    String PAY_VALUE = "pay_value";

    //order
    String ORDER_PAGE = "order_page";
    String ORDER_ID = "order_id";
    int new_order = 2060;
    int view_order = 2061;
    int chat = 2062;

    //REQUEST
    int REQUEST_PERMISSIONS_R_W_STORAGE_CAMERA = 1000;
    int REQUEST_GALLERY_AVATAR = 1001;
    int REQUEST_CAMERA_AVATAR = 1002;
    int REQUEST_GALLERY_ID = 1003;
    int REQUEST_CAMERA_ID = 1004;
    int REQUEST_GALLERY_LICENSE = 1005;
    int REQUEST_CAMERA_LICENSE = 1006;
    int REQUEST_CAMERA_OFFER = 1007;
    int REQUEST_GALLERY_OFFER = 1008;
    int REQUEST_CAMERA_MEAL = 1009;
    int REQUEST_GALLERY_MEAL = 1010;
    int REQUEST_CAMERA_PAYMENT = 1011;
    int REQUEST_GALLERY_PAYMENT = 1012;
    int REQUEST_PHONE_CALL_CODE = 1013;
    int REQUEST_GALLERY_PHOTO = 1014;
    int REQUEST_CAMERA_PHOTO = 1015;
    int REQUEST_PERMISSION_RECORD_CODE = 1016;

    //multiPartBody
    String AVATAR = "avatar";
    String PROOF_PROFILE_IMAGE = "proof_profile_image";
    String COMMERCIAL_CERTIFICATION = "commercial_certification";
    String IMAGE = "image";

    //message status
    String STATUS_NEW_ORDER = "new_order";
    String STATUS_ORDER_CHANGED = "order_status_changed";
    String STATUS_OFFER_ACCEPT = "offer_accepted";
    String STATUS_OFFER_REJECT = "offer_rejected";
    String STATUS_DASHBOARD = "dashboard_msg";
    String STATUS_MESSAGE = "message_receive";
    String STATUS_CONTACT_US = "contact_us_reply";
    String STATUS_WITHDRAW_ACCEPT = "withdraw_accepted";
    String STATUS_WITHDRAW_REJECT = "withdraw_rejected";
    String STATUS_BANK_TRANSFER_ACCEPT = "bank_transfer_accepted";
    String STATUS_BANK_TRANSFER_REJECT = "bank_transfer_rejected";

    //order status
    String STATUS_PLACE = "placed";
    String STATUS_PREPARING = "preparing";
    String STATUS_READY = "ready";
    String STATUS_ON_WAY = "on_way";
    String STATUS_DELIVERED = "delivered";

    //total data
    String SUB_TOTAL = "sub_total";
    String TAX = "tax";
    String DELIVERY = "delivery";
    String TOTAL = "total";
    String SYS_COMMISSION = "sys_commission";
    //config keys
    String min_withdraw_amount = "min_withdraw_amount";
    String credit_limit = "credit_limit";
    String tax = "vat_tax";
    String sys_commission = "sys_commission";

    //firebase
    String IMAGE_REF = "Images";
    String Audio_REF = "Audio";
    String MESSAGE_REF = "Chat";

    // Message Types
    String MESSAGE_TYPE_TEXT = "text";
    String MESSAGE_TYPE_IMAGE = "image";
    String MESSAGE_TYPE_AUDIO = "audio";

    String sat = "sat";
    String sun = "sun";
    String mon = "mon";
    String tue = "tue";
    String wed = "wed";
    String thu = "thu";
    String fri = "fri";

    //hyper pay
    String MERCHANT_ID = "ff80808138516ef4013852936ec200f2";
    String CHECKOUT_ID = "checkout_id";
    String PAYMENT_BRAND = "payment_brand";
    String AMOUNT = "amount";
    String subscribe_id = "subscribe_id";

    class Config {

        /* The payment brands for Ready-to-Use UI and Payment Button */
        public static final Set<String> PAYMENT_BRANDS;

        static {
            PAYMENT_BRANDS = new LinkedHashSet<>();

            PAYMENT_BRANDS.add("VISA");
            PAYMENT_BRANDS.add("MASTER");
            PAYMENT_BRANDS.add("MADA");
        }

        /* The default amount and currency */
        public static final String CURRENCY = "SAR";
    }
}

