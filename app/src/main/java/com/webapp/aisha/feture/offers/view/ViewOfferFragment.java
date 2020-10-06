package com.webapp.aisha.feture.offers.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.webapp.aisha.R;
import com.webapp.aisha.models.Offer;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.dialogs.WaitDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewOfferFragment extends Fragment implements ViewOfferView {

    @BindView(R.id.cl_background) ConstraintLayout clBackground;
    @BindView(R.id.iv_image) ImageView ivImage;
    @BindView(R.id.pb_image) ProgressBar pbImage;
    @BindView(R.id.tv_details) TextView tvDetails;
    @BindView(R.id.tv_price) TextView tvPrice;
    @BindView(R.id.tv_available) TextView tvAvailable;
    @BindView(R.id.tv_description) TextView tvDescription;
    @BindView(R.id.s_accept) Switch sAccept;
    @BindView(R.id.s_available) Switch sAvailable;

    private ViewOfferPresenter presenter;
    private NavigationView view;
    private static final String OFFER = "offer_id";
    private int offer_id;

    public static ViewOfferFragment newInstance(NavigationView navigationView, int offer_id) {
        ViewOfferFragment fragment = new ViewOfferFragment(navigationView);
        Bundle args = new Bundle();
        args.putInt(OFFER, offer_id);
        fragment.setArguments(args);
        return fragment;
    }

    public ViewOfferFragment(NavigationView view) {
        this.view = view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_offer, container, false);
        ButterKnife.bind(this, v);
        if (getArguments() != null) {
            presenter = new ViewOfferPresenter(getActivity(), view, this);
            offer_id = getArguments().getInt(OFFER);
            presenter.getOffer(offer_id);
        } else {
            view.navigate(AppContent.offers);
        }
        return v;
    }

    @Override
    public void setData(Offer offer) {
        ToolUtils.loadImageNormal(getContext(), pbImage, offer.getImg_url(), ivImage);
        tvDetails.setText(offer.getName());
        tvPrice.setText(getString(R.string.currency) + offer.getPrice());
        tvDescription.setText(offer.getDescription());
        tvAvailable.setText(getString(R.string.within) + offer.getDuration() + getString(R.string.hrs));
        if (offer.getStatus().equals(AppContent.accept)) {
            sAccept.setChecked(true);
        } else {
            sAccept.setChecked(false);
        }
        sAvailable.setChecked(offer.isIs_active());
    }

    @OnClick(R.id.s_available)
    public void available() {
        if (sAccept.isChecked()) {
            presenter.updateOffer(offer_id, sAvailable);
        } else {
            ToolUtils.showShortToast(getString(R.string.waiting_accept_offer), getActivity());
            sAvailable.setChecked(false);
        }
    }

    @Override
    public void showDialog(String s) {
        WaitDialogFragment.newInstance(s).show(getFragmentManager(), "");
    }

    @Override
    public void hideDialog() {
        WaitDialogFragment.newInstance("").dismiss();
    }
}