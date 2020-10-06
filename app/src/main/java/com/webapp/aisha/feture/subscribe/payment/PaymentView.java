package com.webapp.aisha.feture.subscribe.payment;

import android.graphics.Bitmap;

import com.webapp.aisha.models.Bank;
import com.webapp.aisha.utils.listener.DialogView;
import com.webapp.aisha.utils.listener.RequestListener;

import java.util.ArrayList;

public interface PaymentView extends DialogView, RequestListener<Bitmap> {
    void setData(ArrayList<Bank> data);
}
