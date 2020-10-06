package com.webapp.aisha.services.api;

import com.webapp.aisha.models.BankTransfer;
import com.webapp.aisha.models.City;
import com.webapp.aisha.models.Claim;
import com.webapp.aisha.models.Config;
import com.webapp.aisha.models.Contact;
import com.webapp.aisha.models.MainCategory;
import com.webapp.aisha.models.Meal;
import com.webapp.aisha.models.Notification;
import com.webapp.aisha.models.Offer;
import com.webapp.aisha.models.Order;
import com.webapp.aisha.models.PageData;
import com.webapp.aisha.models.Pay;
import com.webapp.aisha.models.Rating;
import com.webapp.aisha.models.ResetCode;
import com.webapp.aisha.models.Result;
import com.webapp.aisha.models.Subscribe;
import com.webapp.aisha.models.User;
import com.webapp.aisha.models.Wallet;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface ApiInterface {

    //settings
    @GET("cities")
    Call<Result<ArrayList<City>>> getCities();

    @GET("categories")
    Call<Result<ArrayList<MainCategory>>> getCategories();

    @GET("pages")
    Call<Result<ArrayList<PageData>>> getPages();

    @GET("config")
    Call<Result<ArrayList<Config>>> getConfig();

    @POST("contact")
    Call<Result<Contact>> contact(@QueryMap Map<String, String> map);

    //auth
    @Multipart
    @POST("agents/auth/register")
    Call<Result<User>> signUp(@QueryMap Map<String, String> map,
                              @Part MultipartBody.Part avatar,
                              @Part MultipartBody.Part proof_profile_image,
                              @Part MultipartBody.Part commercial_certification);

    @POST("agents/auth/login")
    Call<Result<User>> login(@QueryMap Map<String, String> map);

    @POST("agents/auth/forget_password")
    Call<Result<ResetCode>> forgetPassword(@QueryMap Map<String, String> map);

    @POST("agents/auth/reset_password")
    Call<Result<ResetCode>> resetPassword(@QueryMap Map<String, String> map);

    @POST("agents/profile/update_password")
    Call<Result<User>> changePassword(@QueryMap Map<String, String> map);

    @POST("agents/profile/update")
    Call<Result<User>> changeStatus(@Query("is_online") int is_online);

    @POST("agents/profile/update")
    Call<Result<User>> setWorkingDays(@Query("working_days") String days);

    @Multipart
    @POST("agents/profile/update")
    Call<Result<User>> updateProfile(@QueryMap Map<String, String> map,
                                     @Part MultipartBody.Part avatar,
                                     @Part MultipartBody.Part proof_profile_image,
                                     @Part MultipartBody.Part commercial_certification);

    @GET("agents/profile")
    Call<Result<User>> getProfile();

    @POST("agents/profile/update_working_times")
    Call<Result<User>> setDeliveryTime(@QueryMap Map<String, String> map);

    //offer
    @GET
    Call<Result<ArrayList<Offer>>> getOffers(@Url String url);

    @GET("agents/offers/{id}")
    Call<Result<Offer>> getOffer(@Path("id") int id);

    @Multipart
    @POST("agents/offers")
    Call<Result<Offer>> storeOffer(@QueryMap Map<String, String> map,
                                   @Part MultipartBody.Part photo);

    @POST("agents/offers/{id}")
    Call<Result<Offer>> updateOffer(@Path("id") int id, @Query("status") int status);

    //meal
    @GET
    Call<Result<ArrayList<Meal>>> getMeals(@Url String url);

    @GET("agents/meals/{id}")
    Call<Result<Meal>> getMeal(@Path("id") int id);

    @POST("agents/meals")
    Call<Result<Meal>> storeMeal(@QueryMap Map<String, String> map);

    @POST("agents/meals/{id}")
    Call<Result<Meal>> updateMeal(@Path("id") int id, @QueryMap Map<String, String> map);

    //package
    @GET("agents/packages")
    Call<Result<ArrayList<Subscribe>>> getSubscribe();

    @POST("agents/packages/{id}/pay?type=credit_card")
    Call<Result<Pay>> pay(@Path("id") int id);

    @GET
    Call<Result<User>> subscribe(@Url String url);

    @Multipart
    @POST("agents/packages/{id}/bank_transfer")
    Call<Result<User>> bankTransfer(@Path("id") int id
            , @QueryMap Map<String, String> map
            , @Part MultipartBody.Part photo);

    @GET
    Call<Result<ArrayList<Rating>>> getRating(@Url String url);

    //photo
    @Multipart
    @POST("upload_img")
    Call<Result<String>> uploadPhoto(@Part MultipartBody.Part file);

    //order list
    @GET
    Call<Result<ArrayList<Order>>> getOrders(@Url String url);

    //wallet
    @POST("agents/withdraws")
    Call<Result<Claim>> sendClaim(@Query("amount") double amount);

    @Multipart
    @POST("agents/bank_transfers")
    Call<Result<BankTransfer>> sendBankTransfer(@QueryMap Map<String, String> map,
                                                @Part MultipartBody.Part photo);

    //wallet list
    @GET
    Call<Result<ArrayList<Wallet>>> getProcessing(@Url String url);

    @GET
    Call<Result<ArrayList<BankTransfer>>> getBankTransfers(@Url String url);

    @GET
    Call<Result<ArrayList<Claim>>> getClaims(@Url String url);

    //order
    @GET("agents/orders/{id}")
    Call<Result<Order>> getOrder(@Path("id") int id);

    @POST("agents/orders/{id}/set_as_preparing")
    Call<Result<Order>> setOrderPreparing(@Path("id") int id);

    @POST("agents/orders/{id}/set_as_ready")
    Call<Result<Order>> setOrderReady(@Path("id") int id);

    @POST("agents/orders/{id}/set_as_on_way")
    Call<Result<Order>> setOrderOnWay(@Path("id") int id);

    //notification
    @POST("send_msg_notification")
    Call<Result<Object>> sendNotification(@Query("body") String body, @Query("order_id") int order_id);

    @GET
    Call<Result<ArrayList<Notification>>> getNotifications(@Url String url);

    @GET("agents/notifications/{id}/read")
    Call<Result<Notification>> setNotificationRead(@Path("id") int id);

    /*@POST("fcmToken")
    Call<Result<>> fcmToken(@Query("token") String token);*/


}
