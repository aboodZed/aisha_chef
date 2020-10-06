package com.webapp.aisha.feture.order.adapter;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webapp.aisha.R;
import com.webapp.aisha.models.Message;
import com.webapp.aisha.utils.AppContent;
import com.webapp.aisha.utils.AppController;
import com.webapp.aisha.utils.ToolUtils;
import com.webapp.aisha.utils.thread.SeekBarRunnable;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.webapp.aisha.utils.AppContent.TEN_MILLIE_SECOND;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Message> messages = new ArrayList<>();
    private Activity activity;
    private SeekBarRunnable seekBarRunnable;
    private Handler handler;

    public ChatAdapter(Activity activity) {
        this.activity = activity;
        handler = new Handler();
        seekBarRunnable = new SeekBarRunnable(handler);
    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getSender_id().equals(String.valueOf(AppController
                .getInstance().getAppSettingsPreferences().getUser().getId()))) {
            return 1;
        } else {
            return 2;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new ChatHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chat_right, parent, false));
        } else {
            return new ChatHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chat_left, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ChatHolder) {
            ((ChatHolder) holder).setData(messages.get(position), position);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addItem(Message chatMessage) {
        messages.add(chatMessage);
        notifyItemInserted(getItemCount() - 1);
    }

    public class ChatHolder extends RecyclerView.ViewHolder implements SeekBar.OnSeekBarChangeListener {

        @BindView(R.id.tv_message_text) TextView tvMessageText;
        @BindView(R.id.iv_image) ImageView ivImage;
        @BindView(R.id.pb_image) ProgressBar pbImage;
        @BindView(R.id.ll_audio) LinearLayout llAudio;
        @BindView(R.id.pb_audio) ProgressBar pbAudio;
        @BindView(R.id.iv_audio) ImageView ivAudio;
        @BindView(R.id.sb_audio) SeekBar sbAudio;
        @BindView(R.id.tv_message_time) TextView tvMessageTime;
        @BindView(R.id.pb_wait_avatar) ProgressBar pbWaitAvatar;
        @BindView(R.id.iv_user_image) ImageView ivUserImage;

        private Message message;
        private int position;

        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            sbAudio.setOnSeekBarChangeListener(this);
        }

        private void setData(Message data, int position) {
            this.message = data;
            this.position = position;
            ivImage.setVisibility(View.GONE);
            tvMessageText.setVisibility(View.GONE);
            pbImage.setVisibility(View.GONE);
            llAudio.setVisibility(View.GONE);
            ivAudio.setImageResource(R.drawable.ic_play);
            ivAudio.setVisibility(View.VISIBLE);
            sbAudio.setProgress(0);
            pbAudio.setVisibility(View.GONE);

            tvMessageTime.setText(ToolUtils.getTime(data.getTime()));
            ToolUtils.loadImageNormal(activity, pbWaitAvatar, data.getSender_avatar_url(), ivUserImage);

            switch (data.getType()) {
                case AppContent.MESSAGE_TYPE_TEXT:
                    tvMessageText.setText(data.getText());
                    tvMessageText.setVisibility(View.VISIBLE);
                    break;
                case AppContent.MESSAGE_TYPE_IMAGE:
                    ivImage.setVisibility(View.VISIBLE);
                    pbImage.setVisibility(View.VISIBLE);
                    ToolUtils.loadImageNormal(activity, pbImage, data.getFile_path(), ivImage);
                    break;
                case AppContent.MESSAGE_TYPE_AUDIO:
                    llAudio.setVisibility(View.VISIBLE);
                    if (message.getMediaPlayer() != null) {
                        sbAudio.setProgress(message.getPositionReached());
                        Log.e("getPositionReached", String.valueOf(message.getPositionReached()));
                        if (message.getMediaPlayer().isPlaying()) {
                            seekBarRunnable.setSeekBarRunnableFlow(message.getMediaPlayer(), sbAudio);
                            handler.postDelayed(seekBarRunnable, TEN_MILLIE_SECOND);
                            ivAudio.setImageResource(R.drawable.ic_pause);
                        } else {
                            ivAudio.setImageResource(R.drawable.ic_play);
                        }
                    } else if (message.isPreparingAudio()) {
                        ivAudio.setVisibility(View.GONE);
                        pbAudio.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }

        @OnClick(R.id.iv_audio)
        public void play() {
            try {
                if (message.getMediaPlayer() == null) {
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(message.getFile_path());
                    mediaPlayer.prepareAsync();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    message.setPreparingAudio(true);
                    notifyItemChanged(position);
                    mediaPlayer.setOnPreparedListener(mp -> {
                        message.setPreparingAudio(false);
                        message.setMediaPlayer(mp);
                        playAudio(message);
                    });
                } else if (message.getMediaPlayer().isPlaying()) {
                    pauseAudio(message);
                } else { // User has previously played this audio before, so we won't load it again
                    playAudio(message);
                }
            } catch (Exception e) {
                Log.e(getClass().getName(), e.getMessage());
                ToolUtils.showLongToast(activity.getString(R.string.error_in_input), activity);
                message.setPreparingAudio(false);
                notifyItemChanged(position);
            }
        }

        private void playAudio(Message message) {
            // Stop running media players if there is
            for (int i = 0; i < messages.size(); i++) {
                if (messages.get(i).getMediaPlayer() != null && messages.get(i).getMediaPlayer().isPlaying()) {
                    handler.removeCallbacks(seekBarRunnable);
                    messages.get(i).setPositionReached(messages.get(i).getMediaPlayer().getCurrentPosition());
                    messages.get(i).getMediaPlayer().pause();
                    notifyItemChanged(i);
                    break;
                }
            }
            message.getMediaPlayer().start();
            message.getMediaPlayer().setOnCompletionListener(mp -> {
                handler.removeCallbacks(seekBarRunnable);
                message.setPositionReached(0);
                notifyItemChanged(position);
            });
            notifyItemChanged(position);
        }

        private void pauseAudio(Message message) {
            message.getMediaPlayer().pause();
            message.setPositionReached(message.getMediaPlayer().getCurrentPosition());
            notifyItemChanged(position);
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            handleSeekBarClick(seekBar);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            handleSeekBarClick(seekBar);
        }

        private void handleSeekBarClick(SeekBar seekBar) {
            try {
                message.setPositionReached(seekBar.getProgress());
                message.getMediaPlayer().seekTo(seekBar.getProgress());
            } catch (Exception e) {
                Log.e("error", e.getMessage());
            }
        }
    }
}
