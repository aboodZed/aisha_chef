package com.webapp.aisha.services.api;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

class CallErrorInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        Response response = chain.proceed(builder.build());
        if (response.code() < 200 || response.code() > 300) {
            try {
                if (response.body() != null) {
                    String responseString = response.body().string();
                    Log.e(getClass().getName() + ": response", responseString);
                    JSONObject statusJSON = new JSONObject(responseString).getJSONObject("status");
                    String message = statusJSON.getString("message");
                    throw new IOException(message);
                }
            } catch (JSONException e) {
                throw new IOException(e.getMessage());
            }
        }
        return response;
    }
}
