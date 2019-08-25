package com.fachrinfl.movie.features.favourite.repository;

import com.fachrinfl.movie.features.movie.model.Movie;

import java.util.ArrayList;

public interface LoadMoviesCallback {
    void preExecute();

    void postExecute(ArrayList<Movie> movies);
}
