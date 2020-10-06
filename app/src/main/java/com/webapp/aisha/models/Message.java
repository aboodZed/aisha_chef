package com.webapp.aisha.models;

import android.media.MediaPlayer;

import com.google.firebase.database.Exclude;

public class Message {

    private String text;
    private String sender_id;
    private String sender_name;
    private String sender_avatar_url;
    private String file_path;
    private String type;
    private long time;

    @Exclude
    private MediaPlayer mediaPlayer;
    @Exclude
    private boolean isPreparingAudio;
    @Exclude
    private int positionReached;

    public Message() {

    }

    @Exclude
    public int getPositionReached() {
        return positionReached;
    }

    public void setPositionReached(int positionReached) {
        this.positionReached = positionReached;
    }

    @Exclude
    public boolean isPreparingAudio() {
        return isPreparingAudio;
    }

    public void setPreparingAudio(boolean preparingAudio) {
        isPreparingAudio = preparingAudio;
    }

    @Exclude
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getSender_avatar_url() {
        return sender_avatar_url;
    }

    public void setSender_avatar_url(String sender_avatar_url) {
        this.sender_avatar_url = sender_avatar_url;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", sender_id=" + sender_id +
                ", sender_name='" + sender_name + '\'' +
                ", sender_avatar_url='" + sender_avatar_url + '\'' +
                ", file_path='" + file_path + '\'' +
                ", message_type='" + type + '\'' +
                ", time=" + time +
                '}';
    }
}
