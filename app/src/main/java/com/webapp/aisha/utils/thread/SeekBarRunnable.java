package com.webapp.aisha.utils.thread;

import android.media.MediaPlayer;
import android.os.Handler;
import android.widget.SeekBar;

import com.webapp.aisha.models.Message;

import static com.webapp.aisha.utils.AppContent.TEN_MILLIE_SECOND;

public class SeekBarRunnable implements Runnable{

    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private Handler handler;

    public SeekBarRunnable(Handler handler) {
        this.handler = handler;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }


    public void setSeekBar(SeekBar seekBar) {
        this.seekBar = seekBar;
        this.seekBar.setMax(mediaPlayer.getDuration());
    }

    public void setSeekBarRunnableFlow(MediaPlayer mediaPlayer, SeekBar seekBar) {
        setMediaPlayer(mediaPlayer);
        setSeekBar(seekBar);
    }

    @Override
    public void run() {
        if (mediaPlayer.isPlaying()) {
            int mediaPlayerCurrentPosition = mediaPlayer.getCurrentPosition();
            seekBar.setProgress(mediaPlayerCurrentPosition);
            handler.postDelayed(this, TEN_MILLIE_SECOND); //Looping the thread after TEN_MILLIE_SECOND
        }
    }
}
