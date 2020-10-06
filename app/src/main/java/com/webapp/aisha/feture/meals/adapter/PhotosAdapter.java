package com.webapp.aisha.feture.meals.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.webapp.aisha.R;
import com.webapp.aisha.utils.ToolUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotosHolder> {

    private ArrayList<Bitmap> bitmaps = new ArrayList<>();
    private Activity activity;

    public PhotosAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public PhotosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhotosHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhotosHolder holder, int position) {
        holder.setData(bitmaps.get(position));
    }

    @Override
    public int getItemCount() {
        return bitmaps.size();
    }

    public void addItem(Bitmap bitmap) {
        bitmaps.add(bitmap);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addItem(String url) {
        if (!TextUtils.isEmpty(url)) {
            ToolUtils.loadImage(activity, url)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            addItem(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
        }
    }

    public void removeItem(Bitmap bitmap) {
        int i = bitmaps.indexOf(bitmap);
        bitmaps.remove(bitmap);
        notifyItemRemoved(i);
    }

    public ArrayList<Bitmap> getList() {
        return bitmaps;
    }

    class PhotosHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_image) ImageView ivImage;
        @BindView(R.id.iv_close) ImageView ivClose;

        private Bitmap bitmap;

        public PhotosHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setData(Bitmap bitmap) {
            this.bitmap = bitmap;
            ivImage.setImageBitmap(bitmap);
        }

        @OnClick(R.id.iv_close)
        public void close() {
            removeItem(bitmap);
        }
    }
}
