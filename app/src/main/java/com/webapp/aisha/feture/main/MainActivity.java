package com.webapp.aisha.feture.main;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.webapp.aisha.R;
import com.webapp.aisha.feture.main.home.HomeFragment;
import com.webapp.aisha.feture.main.notification.NotificationFragment;
import com.webapp.aisha.feture.main.orders.OrdersFragment;
import com.webapp.aisha.feture.main.profile.ProfileFragment;
import com.webapp.aisha.feture.main.schedule.ScheduleFragment;
import com.webapp.aisha.feture.main.wallet.WalletFragment;
import com.webapp.aisha.feture.profile.ProfileActivity;
import com.webapp.aisha.models.User;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.NavigateUtils;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.ToolUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView {

    @BindView(R.id.cl_top) ConstraintLayout clTop;
    @BindView(R.id.s_online) Switch sOnline;
    @BindView(R.id.iv_notification) ImageView ivNotification;
    @BindView(R.id.fl_container) FrameLayout flContainer;
    @BindView(R.id.bnv_bottom) BottomNavigationView bnvBottom;
    @BindView(R.id.tv_home) TextView tvHome;
    @BindView(R.id.tv_wallet) TextView tvWallet;
    @BindView(R.id.tv_orders) TextView tvOrders;
    @BindView(R.id.tv_schedule) TextView tvSchedule;
    @BindView(R.id.ll_profile) LinearLayout llProfile;
    @BindView(R.id.tv_profile) TextView tvProfile;
    @BindView(R.id.civ_user) CircleImageView civUser;

    private HomeFragment homeFragment;
    private OrdersFragment ordersFragment;
    private ProfileFragment profileFragment;
    private ScheduleFragment scheduleFragment;
    private WalletFragment walletFragment;
    private NotificationFragment notificationFragment;

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //bind
        ButterKnife.bind(this);
        //presenter
        presenter = new MainPresenter(this, this);
        //init
        initFragment();
    }

    public void initFragment() {
        User user = AppController.getInstance().getAppSettingsPreferences().getUser();
        ToolUtils.loadImageNormal(this, null, user.getAvatar_url(), civUser);
        sOnline.setChecked(user.isIs_online());

        if (getIntent().getExtras() != null) {
            navigate(getIntent().getExtras().getInt(AppContent.MAIN_PAGE, AppContent.home));
        } else {
            navigate(AppContent.home);
        }
    }

    @OnClick(R.id.s_online)
    public void online() {
        presenter.updateStatus(sOnline);
    }

    @OnClick(R.id.tv_home)
    public void home() {
        navigate(AppContent.home);
    }

    @OnClick(R.id.tv_wallet)
    public void wallet() {
        navigate(AppContent.wallet);
    }

    @OnClick(R.id.tv_orders)
    public void orders() {
        navigate(AppContent.orders);
    }

    @OnClick(R.id.tv_schedule)
    public void schedule() {
        navigate(AppContent.schedule);
    }

    @OnClick(R.id.ll_profile)
    public void profile() {
        navigate(AppContent.profile);
    }

    @OnClick(R.id.iv_notification)
    public void notification() {
        navigate(AppContent.notification);
    }


    @Override
    public void navigate(int page) {
        tvHome.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_un_home, 0, 0);
        tvHome.setTextColor(getColor(R.color.black));
        tvWallet.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_un_wallet, 0, 0);
        tvWallet.setTextColor(getColor(R.color.black));
        tvOrders.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_un_orders, 0, 0);
        tvOrders.setTextColor(getColor(R.color.black));
        tvSchedule.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_un_schedule, 0, 0);
        tvSchedule.setTextColor(getColor(R.color.black));
        tvProfile.setTextColor(getColor(R.color.black));
        switch (page) {
            case AppContent.home:
                homeFragment = HomeFragment.newInstance(this);
                tvHome.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_home, 0, 0);
                tvHome.setTextColor(getColor(R.color.colorPrimaryDark));
                NavigateUtils.replaceFragment(getSupportFragmentManager(), homeFragment, R.id.fl_container);
                break;
            case AppContent.wallet:
                walletFragment = WalletFragment.newInstance(this);
                tvWallet.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_wallet, 0, 0);
                tvWallet.setTextColor(getColor(R.color.colorPrimaryDark));
                NavigateUtils.replaceFragment(getSupportFragmentManager(), walletFragment, R.id.fl_container);
                break;
            case AppContent.orders:
                ordersFragment = OrdersFragment.newInstance(this);
                tvOrders.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_orders, 0, 0);
                tvOrders.setTextColor(getColor(R.color.colorPrimaryDark));
                NavigateUtils.replaceFragment(getSupportFragmentManager(), ordersFragment, R.id.fl_container);
                break;
            case AppContent.schedule:
                scheduleFragment = ScheduleFragment.newInstance(this);
                tvSchedule.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_schedule, 0, 0);
                tvSchedule.setTextColor(getColor(R.color.colorPrimaryDark));
                NavigateUtils.replaceFragment(getSupportFragmentManager(), scheduleFragment, R.id.fl_container);
                break;
            case AppContent.profile:
                profileFragment = ProfileFragment.newInstance(this);
                tvProfile.setTextColor(getColor(R.color.colorPrimaryDark));
                NavigateUtils.replaceFragment(getSupportFragmentManager(), profileFragment, R.id.fl_container);
                break;
            case AppContent.notification:
                notificationFragment = NotificationFragment.newInstance(this);
                NavigateUtils.replaceFragment(getSupportFragmentManager(), notificationFragment, R.id.fl_container);
                break;
            case AppContent.delivery_rate_one:
                NavigateUtils.fromActivityToActivityWithPage(this, ProfileActivity.class
                        , true, AppContent.PROFILE_PAGE, AppContent.delivery_rate_one);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentById(R.id.fl_container) instanceof HomeFragment) {
            super.onBackPressed();
        } else {
            navigate(AppContent.home);
        }
    }
}
