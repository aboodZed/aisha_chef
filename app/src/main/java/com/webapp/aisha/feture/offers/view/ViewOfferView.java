package com.webapp.aisha.feture.offers.view;

import com.webapp.aisha.models.Offer;
import com.webapp.aisha.utils.listener.DialogView;

public interface ViewOfferView extends DialogView {
    void setData(Offer offer);
}
