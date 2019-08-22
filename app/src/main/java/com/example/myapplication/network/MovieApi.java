package com.example.myapplication.network;

import com.example.myapplication.model.Movie;


import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface MovieApi {
    @GET("DevTides/DogsApi/master/dogs.json")
    Single<List<Movie>> getPopularMovies();
}
