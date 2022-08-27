package com.example.wallpaperapp4k.api;

import com.example.wallpaperapp4k.model.Photo;
import com.example.wallpaperapp4k.model.Photos;

import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("curated")
    Call<Photos> callPhoto(@Header("Authorization") String authorization,
                           @Query("page") int pageCount,
                           @Query("per_page") int perPage


    );

    @GET("search")
    Call<Photos> callWallpaperSearch(@Header("Authorization") String authorization,
                                       @Query("page") int pageCount,
                                       @Query("per_page") int perPage,
                                       @Query("query") String search);
}
