package com.webapp.aisha.feture.order;

import com.webapp.aisha.models.Order;
import com.webapp.aisha.utils.listener.DialogView;

public interface OrderView extends DialogView {
    void setData(Order data);
}
