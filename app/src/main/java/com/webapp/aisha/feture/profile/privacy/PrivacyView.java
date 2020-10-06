package com.webapp.aisha.feture.profile.privacy;

import com.webapp.aisha.models.PageData;
import com.webapp.aisha.utils.listener.DialogView;

import java.util.ArrayList;

interface PrivacyView extends DialogView {
    void setData(ArrayList<PageData> data);
}
