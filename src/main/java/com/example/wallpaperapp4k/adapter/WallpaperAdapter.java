package com.example.wallpaperapp4k.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wallpaperapp4k.R;
import com.example.wallpaperapp4k.model.Photo;
import com.example.wallpaperapp4k.onclick.OnClickItemWallpaper;

import java.util.List;

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.ViewHolder> {
    private List<Photo> mListPhoto;
    private Context mContext;
    private OnClickItemWallpaper onClickItemWallpaper;


    public WallpaperAdapter(List<Photo> mListPhoto, Context mContext, OnClickItemWallpaper onClickItemWallpaper) {
        this.mListPhoto = mListPhoto;
        this.mContext = mContext;
        this.onClickItemWallpaper = onClickItemWallpaper;
    }

    public void setData(List<Photo> mListPhoto) {
        this.mListPhoto = mListPhoto;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.wallpaper_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Photo photo = mListPhoto.get(position);
        Glide.with(mContext).load(photo.getSrc().getMedium()).placeholder(R.drawable.wallpaper).into(holder.img);
        String imgURL = photo.getSrc().getMedium();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onClickItemWallpaper.onClick(imgURL,holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListPhoto.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }
}
