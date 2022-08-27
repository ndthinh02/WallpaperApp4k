package com.example.wallpaperapp4k;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallpaperapp4k.adapter.CateroryAdapter;
import com.example.wallpaperapp4k.adapter.WallpaperAdapter;
import com.example.wallpaperapp4k.api.RetrofitSilent;
import com.example.wallpaperapp4k.model.Category;
import com.example.wallpaperapp4k.model.Photo;
import com.example.wallpaperapp4k.model.Photos;
import com.example.wallpaperapp4k.onclick.OnClickItemCategory;
import com.example.wallpaperapp4k.onclick.OnClickItemWallpaper;
import com.example.wallpaperapp4k.views.ShowDetailWallpaper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnClickItemCategory {
    private EditText edtSearch;
    private ImageView imgSearch;
    private RecyclerView rvCategory;
    private RecyclerView rvWallpaper;
    private CateroryAdapter cateroryAdapter;
    private List<Category> mListCategory;

    private WallpaperAdapter wallpaperAdapter;
    private List<Photo> mListPhoto;
    private NestedScrollView nestedScrollView;
    private String author = "563492ad6f91700001000001a2ebf27978224ad591e8448f81942e06";
    int pageCount = 1;
    public static int perPage = 80;
    private ProgressBar prb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setUpPagination(true);
    }

    private void setUpPagination(boolean isPaginationAllowed) {
        if (isPaginationAllowed) {
            nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                        getWallPaper(++pageCount);
                        prb.setVisibility(View.VISIBLE);
                    }
                }
            });
        } else {
            nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    prb.setVisibility(View.INVISIBLE);
                }
            });
        }
    }

    private void initView() {
        prb = (ProgressBar) findViewById(R.id.prb);
        edtSearch = (EditText) findViewById(R.id.edtSearch);
        imgSearch = (ImageView) findViewById(R.id.imgSearch);
        rvCategory = (RecyclerView) findViewById(R.id.rvCategory);
        rvWallpaper = (RecyclerView) findViewById(R.id.rvWallpaper);
        mListCategory = new ArrayList<>();
        cateroryAdapter = new CateroryAdapter(mListCategory, this, this::onClick);
        mListPhoto = new ArrayList<>();
        wallpaperAdapter = new WallpaperAdapter(mListPhoto, this, new OnClickItemWallpaper() {
            @Override
            public void onClick(String photo, int position) {
                showDetailWallpaper(photo, position);
            }
        });
        nestedScrollView = findViewById(R.id.nestedScrollView);
        getCategories();
        getWallPaper(pageCount);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = edtSearch.getText().toString();
                if (search.isEmpty()) {
                    Toast.makeText(getApplication(), "You must fill the search", Toast.LENGTH_SHORT).show();
                } else {
                    edtSearch.setText("");
                    getWallpaperSearch(search);
                }
            }
        });

    }

    private void showDetailWallpaper(String photo, int position) {
        Intent i = new Intent(this, ShowDetailWallpaper.class);
        i.putExtra("pos_wallpaper", position);
        i.putExtra("url_image", photo);
        startActivity(i);
    }

    private void getWallpaperSearch(String search) {
        Call<Photos> call = RetrofitSilent.getPhotos().callWallpaperSearch(author, pageCount, perPage, search);
        prb.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<Photos>() {
            @Override
            public void onResponse(Call<Photos> call, Response<Photos> response) {
                Photos photos = response.body();
                mListPhoto = photos.getPhotos();
                rvWallpaper.setAdapter(wallpaperAdapter);
                wallpaperAdapter.setData(mListPhoto);
                prb.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<Photos> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getCategories() {
        mListCategory.add(new Category("Technology", "https://images.pexels.com/photos/3861972/pexels-photo-3861972.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"));
        mListCategory.add(new Category("FootBall", "https://images.pexels.com/photos/46798/the-ball-stadion-football-the-pitch-46798.jpeg?auto=compress&cs=tinysrgb&w=600\n"));
        mListCategory.add(new Category("Flower", "https://images.pexels.com/photos/56866/garden-rose-red-pink-56866.jpeg?auto=compress&cs=tinysrgb&w=600\n"));
        mListCategory.add(new Category("Dog", "https://images.pexels.com/photos/1254140/pexels-photo-1254140.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"));
        mListCategory.add(new Category("Cat", "https://images.pexels.com/photos/617278/pexels-photo-617278.jpeg?auto=compress&cs=tinysrgb&w=600"));
        mListCategory.add(new Category("Bird", "https://images.pexels.com/photos/349758/hummingbird-bird-birds-349758.jpeg?auto=compress&cs=tinysrgb&w=600"));
        mListCategory.add(new Category("Animal", "https://images.pexels.com/photos/247502/pexels-photo-247502.jpeg?auto=compress&cs=tinysrgb&w=600"));
        mListCategory.add(new Category("Cloud ", "https://images.pexels.com/photos/844297/pexels-photo-844297.jpeg?auto=compress&cs=tinysrgb&w=600"));
        mListCategory.add(new Category("Sunset ", "https://images.pexels.com/photos/3998365/pexels-photo-3998365.png?auto=compress&cs=tinysrgb&w=600"));

        rvCategory.setAdapter(cateroryAdapter);
        cateroryAdapter.notifyDataSetChanged();

    }

    private void getWallPaper(int pageCount) {
        Call<Photos> call = RetrofitSilent.getPhotos().callPhoto(author, pageCount, perPage);
        prb.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<Photos>() {
            @Override
            public void onResponse(Call<Photos> call, Response<Photos> response) {
                Photos photos = response.body();
                mListPhoto = photos.getPhotos();
                rvWallpaper.setAdapter(wallpaperAdapter);
                wallpaperAdapter.setData(mListPhoto);
                prb.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<Photos> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getWallPaperByCategory(String category) {
        Call<Photos> call = RetrofitSilent.getPhotos().callWallpaperSearch(author, pageCount, perPage, category);
        prb.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<Photos>() {
            @Override
            public void onResponse(Call<Photos> call, Response<Photos> response) {
                Photos photos = response.body();
                mListPhoto = photos.getPhotos();
                rvWallpaper.setAdapter(wallpaperAdapter);
                wallpaperAdapter.setData(mListPhoto);
                prb.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<Photos> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(int position) {
        String category = mListCategory.get(position).getCategory();
        getWallPaperByCategory(category);
    }
}