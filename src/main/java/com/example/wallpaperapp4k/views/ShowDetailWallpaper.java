package com.example.wallpaperapp4k.views;

import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.wallpaperapp4k.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.IOException;

public class ShowDetailWallpaper extends AppCompatActivity {

    private RelativeLayout relativeLayout;
    private ImageView img;
    String imgurl;
    DownloadManager downloadManager;
    WallpaperManager wallpaperManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail_wallpaper);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
    }

    private void initView() {
        relativeLayout = findViewById(R.id.relative);
        imgurl = getIntent().getStringExtra("url_image");
        img = findViewById(R.id.img);
        Glide.with(getApplicationContext()).load(imgurl).into(img);
        relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showDialog();
                return false;
            }
        });
        wallpaperManager = WallpaperManager.getInstance(getApplicationContext());

    }

    private void showDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_dialog);
        Button btnSetWallPaper = bottomSheetDialog.findViewById(R.id.btnSetWallpaper);
        Button btnDownload = bottomSheetDialog.findViewById(R.id.btnDownload);
        btnSetWallPaper.setOnClickListener(view -> {
            Glide.with(this).asBitmap().load(imgurl).listener(new RequestListener<Bitmap>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                    Toast.makeText(getApplicationContext(), "Failed to load wallpaper", Toast.LENGTH_SHORT).show();
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                    try {
                        wallpaperManager.setBitmap(resource);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Failed to set wallpaper", Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }
            }).submit();

            bottomSheetDialog.dismiss();
            FancyToast.makeText(ShowDetailWallpaper.this, "Wallpaper Set to home screen", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();

        });

        btnDownload.setOnClickListener(view -> {
            downloadImage(imgurl);
            bottomSheetDialog.dismiss();
        });
        bottomSheetDialog.show();
    }

    private void downloadImage(String imgURL) {
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(imgURL);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setTitle("Download");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, String.valueOf(System.currentTimeMillis()));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        if (downloadManager != null) {
            downloadManager.enqueue(request);
            FancyToast.makeText(ShowDetailWallpaper.this, "Downloaded wallpaper", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();

        }
    }


}