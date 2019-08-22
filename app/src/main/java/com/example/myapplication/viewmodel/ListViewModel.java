package com.example.myapplication.viewmodel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.model.Movie;
import com.example.myapplication.model.MovieDao;
import com.example.myapplication.model.MovieDatabase;
import com.example.myapplication.model.MovieResponse;
import com.example.myapplication.network.MovieApiService;
import com.example.myapplication.util.NotificationsHelper;
import com.example.myapplication.util.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends AndroidViewModel {

    public MutableLiveData<List<Movie>> movie = new MutableLiveData<List<Movie>>();
    public MutableLiveData<Boolean> movieLoadError = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();

    private MovieApiService movieService = new MovieApiService();
    private CompositeDisposable disposable = new CompositeDisposable();

    private AsyncTask<List<Movie>, Void, List<Movie>> insertTask;
    private AsyncTask<Void, Void, List<Movie>> retrieveTask;

    private SharedPreferencesHelper prefHelper = SharedPreferencesHelper.getInstance(getApplication());
    private long refreshTime = 5 * 60 * 1000 * 1000 * 1000L;

    public ListViewModel(@NonNull Application application) {
        super(application);
    }

    public void refresh() {
        checkCacheDuration();
        long updateTime = prefHelper.getUpdateTime();
        long currentTime = System.nanoTime();
        if(updateTime != 0 && currentTime - updateTime < refreshTime) {
            fetchFromDatabase();
        } else {
            fetchFromRemote();
        }
    }

    public void refreshBypassCache() {
        fetchFromRemote();
    }

    private void checkCacheDuration() {
        String cachePreference = prefHelper.getCacheDuration();

        if(!cachePreference.equals("")) {
            try {
                int cachePreferenceInt = Integer.parseInt(cachePreference);
                refreshTime = cachePreferenceInt * 1000 * 1000 * 1000L;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private void fetchFromDatabase() {
        loading.setValue(true);
        retrieveTask = new RetrieveMovieTask();
        retrieveTask.execute();
    }

    private void fetchFromRemote() {
        loading.setValue(true);
        disposable.add(
                movieService.getPopularMovie()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<MovieResponse>() {
                            @Override
                            public void onSuccess(MovieResponse movies) {
                                insertTask = new InsertMovieTask();
                                Log.d("MovieApiService",  String.valueOf(movies.getPage()));
                                insertTask.execute(movies.getResults());
                                Toast.makeText(getApplication(), "Movie retrieved from endpoint", Toast.LENGTH_SHORT).show();
                                NotificationsHelper.getInstance(getApplication()).createNotification();
                            }

                            @Override
                            public void onError(Throwable e) {
                                movieLoadError.setValue(true);
                                loading.setValue(false);
                                e.printStackTrace();
                            }
                        })
        );
    }

    private void movieRetrieved(List<Movie> movies ) {
        movie.setValue(movies);
        movieLoadError.setValue(false);
        loading.setValue(false);
    }

    private class InsertMovieTask extends AsyncTask<List<Movie>, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(List<Movie>... lists) {
            List<Movie> list = lists[0];
            MovieDao dao = MovieDatabase.getInstance(getApplication()).movieDao();
            dao.deleteAllMovie();

            ArrayList<Movie> newList = new ArrayList<>(list);
            List<Long> result = dao.insertAll(newList.toArray(new Movie[0]));

            int i = 0;
            while (i < list.size()) {
                list.get(i).uuid = result.get(i).intValue();
                ++i;
            }

            return list;
        }

        @Override
        protected void onPostExecute(List<Movie> movie) {
            movieRetrieved(movie);
            prefHelper.saveUpdateTime(System.nanoTime());
        }
    }

    private class RetrieveMovieTask extends AsyncTask<Void, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(Void... voids) {
            return MovieDatabase.getInstance(getApplication()).movieDao().getAllMovie();
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            movieRetrieved(movies);
            Toast.makeText(getApplication(), "Movie retrieved from database", Toast.LENGTH_SHORT).show();
        }
    }
}
