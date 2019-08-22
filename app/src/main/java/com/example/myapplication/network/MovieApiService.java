package com.example.myapplication.network;

import com.example.myapplication.model.Movie;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieApiService {
    private static final String BASE_URL = "https://api.themoviedb.org/";
    private MovieApi api;

    public MovieApiService(){
        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(MovieApi.class);
    }

    public Single<List<Movie>> getPopularMovie(){
        return api.getPopularMovies();
    }

}
