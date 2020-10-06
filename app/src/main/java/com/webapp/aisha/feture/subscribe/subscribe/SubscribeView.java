package com.webapp.aisha.feture.subscribe.subscribe;

import com.webapp.aisha.models.Subscribe;
import com.webapp.aisha.utils.listener.DialogView;

import java.util.ArrayList;

interface SubscribeView extends DialogView {

    void setDataInPager(ArrayList<Subscribe> subscribes);

}
