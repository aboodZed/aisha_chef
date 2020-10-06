package com.webapp.aisha.feture.order.chat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.webapp.aisha.R;
import com.webapp.aisha.feture.order.adapter.ChatAdapter;
import com.webapp.aisha.models.Message;
import com.webapp.aisha.models.Order;
import com.webapp.aisha.services.firebase.FireBaseReferences;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.dialogs.WaitDialogFragment;
import com.webapp.aisha.utils.listener.DialogView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.webapp.aisha.utils.AppContent.MESSAGE_TYPE_TEXT;

public class ChatFragment extends Fragment implements DialogView {

    @BindView(R.id.rv_chat) RecyclerView rvChat;
    @BindView(R.id.pb_load) ProgressBar pbLoad;
    @BindView(R.id.ll_bottom) LinearLayout llBottom;
    @BindView(R.id.ib_add) ImageButton ibAdd;
    @BindView(R.id.et_message) EditText etMessage;
    @BindView(R.id.iv_camera) ImageView ivCamera;
    @BindView(R.id.iv_voice) ImageView ivVoice;

    private ChatPresenter presenter;
    private ChatAdapter adapter;
    private NavigationView view;
    private static final String ORDER = "order";
    private Order order;
    public static boolean isChatOpen;

    public static ChatFragment newInstance(NavigationView navigationView, Order order) {
        ChatFragment fragment = new ChatFragment(navigationView);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ORDER, order);
        fragment.setArguments(bundle);
        return fragment;
    }

    public ChatFragment(NavigationView view) {
        this.view = view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, v);
        if (getArguments() != null) {
            order = (Order) getArguments().getSerializable(ORDER);
            presenter = new ChatPresenter(getActivity(), view, order, this);
        } else {
            view.navigate(AppContent.view_order);
        }
        initRecycleView();
        getMessages();
        return v;
    }

    @OnClick(R.id.ib_add)
    public void addMessage() {
        String m = etMessage.getText().toString().trim();
        if (!TextUtils.isEmpty(m)) {
            showDialog(getActivity().getString(R.string.upload_text));
            presenter.sendMessage(MESSAGE_TYPE_TEXT, m);
            etMessage.setText("");
        }
    }

    @OnClick(R.id.iv_camera)
    public void camera() {
        presenter.setImage(getFragmentManager());
    }

    @OnClick(R.id.iv_voice)
    public void audio() {
        if (checkPermissionsFromDevice()) {
        presenter.setAudio(getFragmentManager());
        } else {
            requestPermission();
        }
    }

    private void initRecycleView() {
        adapter = new ChatAdapter(getActivity());
        rvChat.setLayoutManager(new LinearLayoutManager(getContext()));
        rvChat.setItemAnimator(new DefaultItemAnimator());
        rvChat.setAdapter(adapter);
        if (order.getStatus().getCode().equals(AppContent.STATUS_DELIVERED)){
            llBottom.setVisibility(View.GONE);
        }
    }

    private void getMessages() {
        FireBaseReferences.getInstance().getChatRef().child(String.valueOf(order.getId()))
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        adapter.addItem(snapshot.getValue(Message.class));
                        rvChat.scrollToPosition(adapter.getItemCount() - 1);
                        pbLoad.setVisibility(View.GONE);
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
    isChatOpen = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isChatOpen = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    isChatOpen = false;
    }


    private boolean checkPermissionsFromDevice() {
        int write_external_storage_result = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int record_audio_result = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO);
        return write_external_storage_result == PackageManager.PERMISSION_GRANTED &&
                record_audio_result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        }, AppContent.REQUEST_PERMISSION_RECORD_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        presenter.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        presenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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