package com.example.wallpaperapp4k.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSilent {
    public static String BASE_URL = "https://api.pexels.com/v1/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static RetrofitService getPhotos() {
        RetrofitService retrofitService = getRetrofit().create(RetrofitService.class);
        return retrofitService;
    }
}
