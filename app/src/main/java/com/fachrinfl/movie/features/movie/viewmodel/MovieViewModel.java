package com.fachrinfl.movie.features.movie.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.fachrinfl.movie.features.movie.model.Movie;
import com.fachrinfl.movie.features.movie.model.MovieRepository;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository movieRepository;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
    }

    public LiveData<List<Movie>> getPopularMovies() {
        return movieRepository.getPopularMovieLiveData();
    }

    public LiveData<Boolean> getLoading() {
        return movieRepository.getIsLoading();
    }

    public LiveData<Boolean> getError() {
        return movieRepository.getIsError();
    }

    public void clear() {
        movieRepository.clear();
    }
}
