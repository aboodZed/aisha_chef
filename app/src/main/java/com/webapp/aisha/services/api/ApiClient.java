package com.webapp.aisha.services.api;

import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.AppController;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit() {
        OkHttpClient client = getClient();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(AppContent.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .addNetworkInterceptor(new AddHeaderInterceptor())
                .addInterceptor(new CallErrorInterceptor())
                .addInterceptor(new AuthInterceptor(AppController.getInstance()))
                .connectTimeout(10, TimeUnit.MINUTES)
                .writeTimeout(10, TimeUnit.MINUTES)
                .readTimeout(10, TimeUnit.MINUTES)
                .retryOnConnectionFailure(true)
                .build();
        client.connectionPool().evictAll();
        return client;
    }

    private static class AddHeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();
            builder.addHeader("Authorization", AppController.getInstance().getAppSettingsPreferences().getKeyAccessToken());
            builder.addHeader("X-Requested-With", "XMLHttpRequest");
            builder.header("Accept-Language", AppController.getInstance()
                    .getAppSettingsPreferences().getAppLanguage());
            builder.header("Connection", "close");
            return chain.proceed(builder.build());
        }
    }
}