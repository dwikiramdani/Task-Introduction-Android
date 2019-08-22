package com.example.myapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Movie {

    @ColumnInfo(name = "id")
    @SerializedName("id")
    public int id;

    @ColumnInfo(name = "original_title")
    @SerializedName("original_title")
    public String title;

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    public String releaseDate;

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    public float rating;

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    public String thumbPath;

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    public String overview;

    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    public String backdropPath;

    @ColumnInfo(name = "credits")
    @SerializedName("credits")
    public String credits;

    @ColumnInfo(name = "runtime")
    @SerializedName("runtime")
    public String runTime;

    @ColumnInfo(name = "tagline")
    @SerializedName("tagline")
    public String tagline;

    @ColumnInfo(name = "homepage")
    @SerializedName("homepage")
    public String homepage;

    @PrimaryKey(autoGenerate = true)
    public int uuid;

    public Movie(int id, String title, String releaseDate, float rating, String thumbPath, String overview, String backdropPath, String credits, String runTime, String tagline, String homepage) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.thumbPath = thumbPath;
        this.overview = overview;
        this.backdropPath = backdropPath;
        this.credits = credits;
        this.runTime = runTime;
        this.tagline = tagline;
        this.homepage = homepage;
    }
}
