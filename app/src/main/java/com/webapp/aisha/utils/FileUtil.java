package com.webapp.aisha.utils;


import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileUtil {

    @Nullable
    public static File createImageFile(Context context) {
        File imageFile;
        try {
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
            String imageFileName = "aisha" + timeStamp + "_";
            File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            imageFile = File.createTempFile(
                    imageFileName,
                    ".jpg",
                    storageDir);
        } catch (IOException exception) {
            return null;
        }
        return imageFile;
    }

    @Nullable
    public static File createAudioFile(Context context) {
        File audioFile;
        try {
            // Create an audio file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
            String audioFileName = "AISHA_" + timeStamp + "_audio";
            File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PODCASTS);
            audioFile = File.createTempFile(
                    audioFileName,
                    ".3gp",
                    storageDir);
        } catch (IOException exception) {
            return null;
        }
        return audioFile;
    }

    public static void deleteCameraImageWithUri(@Nullable Uri uri) {
        if (uri == null) {
            return;
        }

        String uriString = uri.toString();
        if (TextUtils.isEmpty(uriString)) {
            return;
        }

        String filePath = uriString.substring(uriString.lastIndexOf('/'));
        String completePath = Environment.getExternalStorageDirectory().getPath()
                + AppContent.FILE_PROVIDER_PATH
                + filePath;
        File imageFile = new File(completePath);
        if (imageFile.exists()) {
            imageFile.delete();
        }
    }

    public static void deleteFile(Uri uri){
        if (uri == null){
            return;
        }
        String uriString = uri.toString();
        if (TextUtils.isEmpty(uriString)) {
            return;
        }

        File file = new File(uriString);
        if (file.exists()){
            file.delete();
        }
    }
}