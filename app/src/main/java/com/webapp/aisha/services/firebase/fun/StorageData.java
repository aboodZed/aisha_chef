package com.webapp.aisha.services.firebase.fun;

import android.net.Uri;

import com.google.firebase.storage.StorageReference;
import com.webapp.aisha.services.firebase.FireBaseReferences;
import com.webapp.aisha.utils.listener.RequestListener;


public class StorageData {

    public void uploadImage(Uri path, RequestListener<Uri> requestListener) {
        final StorageReference imagesRef = FireBaseReferences.getInstance().getImgRef().child(path.getLastPathSegment());
        imagesRef.putFile(path)
                .addOnFailureListener(e -> requestListener.onFail(e.getMessage() + ""))
                .addOnSuccessListener(taskSnapshot -> imagesRef.getDownloadUrl().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        requestListener.onSuccess(task.getResult(), "");
                    } else {
                        requestListener.onFail(task.getException().getLocalizedMessage());
                    }
                }));
    }


    public void uploadMedia(Uri path, RequestListener<Uri> requestListener) {
        final StorageReference mediaRef = FireBaseReferences.getInstance().getMediaRef().child(path.getLastPathSegment());
        mediaRef.putFile(path)
                .addOnFailureListener(e -> requestListener.onFail(e.getMessage() + ""))
                .addOnSuccessListener(taskSnapshot -> mediaRef.getDownloadUrl().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        requestListener.onSuccess(task.getResult(), "");
                    } else {
                        requestListener.onFail(task.getException().getLocalizedMessage() + "");
                    }
                }));
    }
}
