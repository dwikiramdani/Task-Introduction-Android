package com.example.myapplication.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ItemMovieBinding;
import com.example.myapplication.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> implements MovieClickListener {

    private ArrayList<Movie> movies;

    public MovieListAdapter(ArrayList<Movie> movies){
        this.movies = movies;
    }

    public void updateMoviesList(List<Movie> newMovies){
        movies.clear();
        movies.addAll(newMovies);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemMovieBinding view = DataBindingUtil.inflate(inflater, R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.itemView.setMovie(movies.get(position));
        holder.itemView.setListener(this);
    }

    @Override
    public void onMovieClicked(View v) {
        String uuidString = ((TextView)v.findViewById(R.id.movieId)).getText().toString();
        int uuid = Integer.valueOf(uuidString);
        ListFragmentDirections.ActionDetail action = ListFragmentDirections.actionDetail();
        action.setMovieUuid(uuid);
        Navigation.findNavController(v).navigate(action);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        public ItemMovieBinding itemView;

        public MovieViewHolder(@NonNull ItemMovieBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }
    }
}
