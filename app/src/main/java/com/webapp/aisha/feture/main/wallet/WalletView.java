package com.webapp.aisha.feture.main.wallet;

import com.webapp.aisha.utils.listener.DialogView;

public interface WalletView<T> extends DialogView {
   void setData(T data);
}
