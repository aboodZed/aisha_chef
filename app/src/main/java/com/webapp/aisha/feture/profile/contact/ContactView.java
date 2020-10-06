package com.webapp.aisha.feture.profile.contact;


import com.webapp.aisha.models.Config;
import com.webapp.aisha.utils.listener.DialogView;

import java.util.ArrayList;

public interface ContactView extends DialogView {
    void setData(ArrayList<Config> data);
}