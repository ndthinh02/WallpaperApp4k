package com.example.wallpaperapp4k.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.wallpaperapp4k.R;
import com.example.wallpaperapp4k.model.Category;
import com.example.wallpaperapp4k.onclick.OnClickItemCategory;

import java.util.List;

public class CateroryAdapter extends RecyclerView.Adapter<CateroryAdapter.ViewHolder> {
    private List<Category> mListCategory;
    private Context mContext;
    private OnClickItemCategory onClickItemCategory;
    private ImageView imgCategory;
    private TextView tvCatogory;


    public CateroryAdapter(List<Category> mListCategory, Context mContext, OnClickItemCategory onClickItemCategory) {
        this.mListCategory = mListCategory;
        this.mContext = mContext;
        this.onClickItemCategory = onClickItemCategory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.category_item_rv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = mListCategory.get(position);
        holder.tvCatogory.setText(category.getCategory());
        Glide.with(mContext).load(category.getCaregoryURL()).placeholder(R.drawable.wallpaper).into(holder.imgCategory);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickItemCategory.onClick(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mListCategory.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgCategory;
        private TextView tvCatogory;
        private ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCategory = (ImageView) itemView.findViewById(R.id.imgCategory);
            tvCatogory = (TextView) itemView.findViewById(R.id.tvCatogory);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }
}
