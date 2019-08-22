package com.example.myapplication.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieDao {
    @Insert
    List<Long> insertAll(Movie... movies);

    @Query("SELECT * FROM movie")
    List<Movie> getAllMovie();

    @Query("SELECT * FROM movie WHERE uuid = :id")
    Movie getMovie(int id);

    @Query("DELETE FROM movie")
    void deleteAllMovie();
}
