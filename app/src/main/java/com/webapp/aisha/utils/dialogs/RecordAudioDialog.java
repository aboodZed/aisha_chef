package com.webapp.aisha.utils.dialogs;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.webapp.aisha.R;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.FileUtil;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.listener.RequestListener;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.webapp.aisha.utils.AppContent.ONE_SECOND;

public class RecordAudioDialog extends DialogFragment {

    @BindView(R.id.delete_audio) ImageView deleteAudio;
    @BindView(R.id.audio_counter) TextView audioCounter;
    @BindView(R.id.send_audio) ImageView sendAudio;

    private Handler handler;
    private RequestListener<Uri> listener;
    private MediaRecorder mediaRecorder;
    private String pathSave = "";
    private Uri uri;
    private Timer timer;
    private int seconds;

    public static RecordAudioDialog newInstance() {
        return new RecordAudioDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_record_audio, container, false);
        ButterKnife.bind(this, view);
        handler = new Handler(getContext().getMainLooper());
        File file = FileUtil.createAudioFile(getContext());
        if (file != null) {
            pathSave = file.getPath();
            uri = Uri.fromFile(file);
            startRecord();
        } else {
            deleteAudio();
        }
        return view;
    }

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        // Assign window properties to fill the parent
        params.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.transparent);
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        setCancelable(false);
        super.onResume();
        getDialog().setOnKeyListener((dialog, keyCode, event) -> {
            if ((keyCode == android.view.KeyEvent.KEYCODE_BACK)) {
                deleteAudio();
                return true;
            } else return false;
        });
    }

    @OnClick(R.id.send_audio)
    public void send() {
        endRecord();
    }

    @OnClick(R.id.delete_audio)
    public void delete() {
        deleteAudio();
    }

    private void setupMediaRecorder() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(pathSave);
    }

    private void startTimer() {
        timer = new Timer();
        audioCounter.setText("0:0");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ++seconds;
                handler.post(() -> {
                    audioCounter.setText((seconds / 60) + ":" + (seconds % 60));
                });
            }
        }, ONE_SECOND, ONE_SECOND);
    }

    public void startRecord() {
        startTimer();
        setupMediaRecorder();
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void endRecord() {
        try {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        listener.onSuccess(uri, "");
        dismiss();
    }

    public void deleteAudio() {
        try {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileUtil.deleteFile(uri);
        listener.onFail(getString(R.string.audio_deleted));
        dismiss();
    }

    public void setListener(RequestListener<Uri> listener) {
        this.listener = listener;
    }
}