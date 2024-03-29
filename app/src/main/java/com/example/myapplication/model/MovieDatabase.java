package com.example.myapplication.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Movie.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {

    private static MovieDatabase instance;

    public static MovieDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    MovieDatabase.class,
                    "moviedatabase")
                    .build();
        }
        return instance;
    }
    public abstract MovieDao movieDao();
}
