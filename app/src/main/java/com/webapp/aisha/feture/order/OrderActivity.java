package com.webapp.aisha.feture.order;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.webapp.aisha.R;
import com.webapp.aisha.feture.main.MainActivity;
import com.webapp.aisha.feture.order.chat.ChatFragment;
import com.webapp.aisha.feture.order.newOrder.NewOrderFragment;
import com.webapp.aisha.feture.order.viewOrder.ViewOrderFragment;
import com.webapp.aisha.models.Order;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.NavigateUtils;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.dialogs.WaitDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderActivity extends AppCompatActivity implements NavigationView, OrderView {

    @BindView(R.id.iv_back) ImageView ivBack;
    @BindView(R.id.iv_notification) ImageView ivNotification;
    @BindView(R.id.fl_order_container) FrameLayout flOrderContainer;

    private OrderPresenter presenter;
    private NewOrderFragment newOrderFragment;
    private ViewOrderFragment viewOrderFragment;
    private ChatFragment chatFragment;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        presenter = new OrderPresenter(this, this, this);
        initFragments();
    }

    private void initFragments() {
        if (getIntent().getExtras() != null) {
            presenter.getData(getIntent().getExtras().getInt(AppContent.ORDER_ID));
        } else {
            back();
        }
    }


    @OnClick(R.id.iv_back)
    public void back() {
        onBackPressed();
    }

    @OnClick(R.id.iv_notification)
    public void notification() {
        navigate(AppContent.notification);
    }

    @Override
    public void navigate(int page) {
        switch (page) {
            case AppContent.new_order:
                newOrderFragment = NewOrderFragment.newInstance(this, this, order);
                NavigateUtils.replaceFragment(getSupportFragmentManager(), newOrderFragment, R.id.fl_order_container);
                break;
            case AppContent.view_order:
                viewOrderFragment = ViewOrderFragment.newInstance(this,this, order);
                NavigateUtils.replaceFragment(getSupportFragmentManager(), viewOrderFragment, R.id.fl_order_container);
                break;
            case AppContent.chat:
                chatFragment = ChatFragment.newInstance(this, order);
                NavigateUtils.replaceFragment(getSupportFragmentManager(), chatFragment, R.id.fl_order_container);
                break;
            case AppContent.notification:
                NavigateUtils.fromActivityToActivityWithPage(this, MainActivity.class
                        , true, AppContent.MAIN_PAGE, AppContent.notification);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_order_container);
        if (fragment instanceof ChatFragment) {
            navigate(AppContent.view_order);
        } else if (isTaskRoot()) {
            NavigateUtils.fromActivityToActivityWithPage(this, MainActivity.class, true
                    , AppContent.MAIN_PAGE, AppContent.orders);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public void setData(Order data) {
        this.order = data;
        if (getIntent().getExtras().getInt(AppContent.ORDER_PAGE) == AppContent.chat) {
            navigate(AppContent.chat);
        } else if (data.getStatus().getCode().equals(AppContent.STATUS_PLACE)) {
            navigate(AppContent.new_order);
        } else {
            navigate(AppContent.view_order);
        }
    }

    @Override
    public void showDialog(String s) {
        WaitDialogFragment.newInstance(s).show(getSupportFragmentManager(), "");
    }

    @Override
    public void hideDialog() {
        WaitDialogFragment.newInstance("").dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_order_container);
        if (fragment instanceof ChatFragment) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}