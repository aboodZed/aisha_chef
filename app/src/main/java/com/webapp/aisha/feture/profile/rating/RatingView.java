package com.webapp.aisha.feture.profile.rating;

import com.webapp.aisha.models.Rating;
import com.webapp.aisha.utils.listener.DialogView;

import java.util.ArrayList;

interface RatingView extends DialogView {
    void setData(ArrayList<Rating> data);
}
