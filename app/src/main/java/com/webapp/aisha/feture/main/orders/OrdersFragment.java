package com.webapp.aisha.feture.main.orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.webapp.aisha.R;
import com.webapp.aisha.feture.main.orders.finish.FinishedOrdersFragment;
import com.webapp.aisha.feture.main.orders.process.ProcessingOrdersFragment;
import com.webapp.aisha.utils.view_adapter.PageAdapter;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrdersFragment extends Fragment {

    @BindView(R.id.tl_tabs) TabLayout tlTabs;
    @BindView(R.id.vp_orders) ViewPager vpOrders;

    private OrdersPresenter presenter;

    private NavigationView view;
    public static int page = 0;

    public static OrdersFragment newInstance(NavigationView navigationView) {
        return new OrdersFragment(navigationView);
    }

    public OrdersFragment(NavigationView view) {
        this.view = view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_orders, container, false);
        ButterKnife.bind(this, v);
        initViewPager();
        //presenter
        presenter = new OrdersPresenter(getActivity(), view);
        return v;
    }

    private void initViewPager() {
        PageAdapter pageAdapter = new PageAdapter(getChildFragmentManager());
        pageAdapter.addFragment(FinishedOrdersFragment.newInstance(view), getString(R.string.finished));
        pageAdapter.addFragment(ProcessingOrdersFragment.newInstance(view), getString(R.string.processing));
        vpOrders.setAdapter(pageAdapter);
        tlTabs.setupWithViewPager(vpOrders);
        vpOrders.setCurrentItem(page);
        vpOrders.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                page = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}