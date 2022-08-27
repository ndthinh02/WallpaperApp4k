package com.example.wallpaperapp4k.viewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.Glide;
import com.example.wallpaperapp4k.R;
import com.example.wallpaperapp4k.model.Photo;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private List<Photo> mListPhoto;
    private Context mContext;
    private String imgURL;



    public ViewPagerAdapter(List<Photo> mListPhoto, Context mContext, String imgURL) {
        this.mListPhoto = mListPhoto;
        this.mContext = mContext;
        this.imgURL = imgURL;
    }

    public void setData(List<Photo> mListPhoto) {
        this.mListPhoto = mListPhoto;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mListPhoto.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_viewpager, container, false);
        assert layoutInflater != null;
        ImageView img = view.findViewById(R.id.img);
        Photo photo = mListPhoto.get(position);
//        ImageView img = new ImageView(mContext);
        Glide.with(mContext).load(photo.getSrc().getMedium()).into(img);
        Toast.makeText(mContext, photo.getSrc().getMedium(), Toast.LENGTH_SHORT).show();
        container.addView(img);
        return img;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);

    }
}
