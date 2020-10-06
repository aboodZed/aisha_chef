package com.webapp.aisha.feture.main.orders;

import com.webapp.aisha.models.Order;
import com.webapp.aisha.utils.listener.DialogView;

import java.util.ArrayList;

public interface OrdersView extends DialogView {
   void setData(ArrayList<Order> data);
}
