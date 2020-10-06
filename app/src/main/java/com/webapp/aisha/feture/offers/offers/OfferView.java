package com.webapp.aisha.feture.offers.offers;

import com.webapp.aisha.models.Offer;
import com.webapp.aisha.models.Result;
import com.webapp.aisha.utils.listener.DialogView;

import java.util.ArrayList;

public interface OfferView extends DialogView {

    void initRecycleView(Result<ArrayList<Offer>> list);
}
