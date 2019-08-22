package com.example.myapplication.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.Movie;
import com.example.myapplication.model.MovieDatabase;

public class DetailViewModel extends AndroidViewModel {

    public MutableLiveData<Movie> movieLiveData = new MutableLiveData<Movie>();
    private RetrieveMovieTask task;

    public DetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void fetch(int uuid) {
        task = new RetrieveMovieTask();
        task.execute(uuid);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if(task != null) {
            task.cancel(true);
            task = null;
        }
    }

    private class RetrieveMovieTask extends AsyncTask<Integer, Void, Movie> {

        @Override
        protected Movie doInBackground(Integer... integers) {
            int uuid = integers[0];
            return MovieDatabase.getInstance(getApplication()).movieDao().getMovie(uuid);
        }

        @Override
        protected void onPostExecute(Movie movie) {
            movieLiveData.setValue(movie);
        }
    }
}
