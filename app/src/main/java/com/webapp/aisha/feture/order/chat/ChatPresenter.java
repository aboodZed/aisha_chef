package com.webapp.aisha.feture.order.chat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.fragment.app.FragmentManager;

import com.webapp.aisha.R;
import com.webapp.aisha.models.Message;
import com.webapp.aisha.models.Order;
import com.webapp.aisha.services.api.ApiShared;
import com.webapp.aisha.services.firebase.FirebaseShared;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.NavigateUtils.NavigationView;
import com.webapp.aisha.utils.PermissionUtil;
import com.webapp.aisha.utils.PhotoTakerManager;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.dialogs.ItemSelectImageDialog;
import com.webapp.aisha.utils.dialogs.RecordAudioDialog;
import com.webapp.aisha.utils.listener.DialogView;
import com.webapp.aisha.utils.listener.RequestListener;

import static android.app.Activity.RESULT_OK;
import static com.webapp.aisha.utils.AppContent.MESSAGE_TYPE_AUDIO;
import static com.webapp.aisha.utils.AppContent.MESSAGE_TYPE_IMAGE;
import static com.webapp.aisha.utils.AppContent.MESSAGE_TYPE_TEXT;

public class ChatPresenter implements RequestListener<Bitmap> {

    private Activity activity;
    private NavigationView view;
    private Order order;
    private PhotoTakerManager photoTakerManager;
    private DialogView dialogView;

    public ChatPresenter(Activity activity, NavigationView view, Order order, DialogView dialogView) {
        this.activity = activity;
        this.view = view;
        this.order = order;
        this.photoTakerManager = new PhotoTakerManager(this);
        this.dialogView = dialogView;
    }

    public void setImage(FragmentManager fragmentManager) {
        ItemSelectImageDialog itemSelectImageDialog = ItemSelectImageDialog.newInstance(activity.getString(R.string.select_image));
        itemSelectImageDialog.show(fragmentManager, "");
        itemSelectImageDialog.setListener(new ItemSelectImageDialog.Listener() {
            @Override
            public void onGalleryClicked() {
                setGalleryImage(AppContent.REQUEST_GALLERY_PHOTO);
            }

            @Override
            public void onCameraClicked() {
                setCameraImage(AppContent.REQUEST_CAMERA_PHOTO);
            }
        });
    }

    public void setAudio(FragmentManager fragmentManager) {
        RecordAudioDialog recordAudioDialog = RecordAudioDialog.newInstance();
        recordAudioDialog.show(fragmentManager, "");
        recordAudioDialog.setListener(new RequestListener<Uri>() {
            @Override
            public void onSuccess(Uri uri, String msg) {
                dialogView.showDialog(activity.getString(R.string.upload_some_files));
                FirebaseShared.getInstance().getStorageData().uploadMedia(uri
                        , new RequestListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri, String msg) {
                                sendMessage(MESSAGE_TYPE_AUDIO, String.valueOf(uri));
                            }

                            @Override
                            public void onFail(String msg) {
                                ToolUtils.showLongToast(msg, activity);
                                dialogView.hideDialog();
                            }
                        });
            }

            @Override
            public void onFail(String msg) {
                ToolUtils.showLongToast(msg, activity);
            }
        });
    }

    public void setCameraImage(int requestNum) {
        if (!PermissionUtil.isPermissionGranted(Manifest.permission.CAMERA, activity)) {
            PermissionUtil.requestPermission(activity, Manifest.permission.CAMERA
                    , AppContent.REQUEST_PERMISSIONS_R_W_STORAGE_CAMERA);
        } else {
            activity.startActivityForResult(photoTakerManager.getPhotoCameraIntent(activity), requestNum);
        }
    }

    public void setGalleryImage(int requestNum) {
        if (!PermissionUtil.isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE, activity)) {
            PermissionUtil.requestPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE
                    , AppContent.REQUEST_PERMISSIONS_R_W_STORAGE_CAMERA);
        } else {
            activity.startActivityForResult(photoTakerManager.getPhotoGalleryIntent(activity), requestNum);
        }
    }

    public void sendMessage(String type, String s) {
        Message message = new Message();
        switch (type) {
            case MESSAGE_TYPE_TEXT:
                message.setText(s);
                message.setFile_path("");
                break;
            case AppContent.MESSAGE_TYPE_IMAGE:
            case AppContent.MESSAGE_TYPE_AUDIO:
                message.setText("");
                message.setFile_path(s);
                break;
        }
        message.setType(type);
        FirebaseShared.getInstance().getMessageData().createNewMessage(order.getId(), message,
                new RequestListener<Message>() {
                    @Override
                    public void onSuccess(Message message, String msg) {
                        ApiShared.getInstance().getNotificationData()
                                .sendOrderNotification(activity, message.getText(), order.getId()
                                        , new RequestListener<Object>() {
                                            @Override
                                            public void onSuccess(Object o, String msg) {

                                            }

                                            @Override
                                            public void onFail(String msg) {
                                                ToolUtils.showLongToast(msg, activity);
                                            }
                                        });
                        dialogView.hideDialog();
                    }

                    @Override
                    public void onFail(String msg) {
                        ToolUtils.showLongToast(msg, activity);
                        dialogView.hideDialog();
                    }
                });
    }

    @Override
    public void onSuccess(Bitmap bitmap, String msg) {
        dialogView.showDialog(activity.getString(R.string.upload_some_files));
        FirebaseShared.getInstance().getStorageData().uploadImage(photoTakerManager.getCurrentPhotoUri()
                , new RequestListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri, String msg) {
                        sendMessage(MESSAGE_TYPE_IMAGE, String.valueOf(uri));
                    }

                    @Override
                    public void onFail(String msg) {
                        dialogView.hideDialog();
                        ToolUtils.showLongToast(msg, activity);
                    }
                });
    }

    @Override
    public void onFail(String msg) {
        ToolUtils.showLongToast(msg, activity);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case AppContent.REQUEST_CAMERA_PHOTO:
                    photoTakerManager.processCameraPhoto(activity);
                    break;
                case AppContent.REQUEST_GALLERY_PHOTO:
                    photoTakerManager.processGalleryPhoto(activity, data);
                    break;
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case AppContent.REQUEST_PERMISSIONS_R_W_STORAGE_CAMERA:
                    ToolUtils.showLongToast(activity.getString(R.string.permission_granted), activity);
                    break;
                case AppContent.REQUEST_PERMISSION_RECORD_CODE:
                    ToolUtils.showLongToast(activity.getString(R.string.permission_granted), activity);
                    break;
            }
        } else {
            ToolUtils.showLongToast(activity.getString(R.string.permission_denial), activity);
        }
    }
}
