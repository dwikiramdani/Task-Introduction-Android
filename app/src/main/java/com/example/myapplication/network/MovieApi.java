package com.example.myapplication.network;

import com.example.myapplication.model.Movie;
import com.example.myapplication.model.MovieResponse;


import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface MovieApi {
    /*@GET("3/movie/upcoming")
    Single<List<Movie>> getPopularMovies();
    */

    @GET("3/movie/upcoming")
    Single<MovieResponse> getPopularMovies();
}
