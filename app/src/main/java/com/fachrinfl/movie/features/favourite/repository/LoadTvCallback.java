package com.fachrinfl.movie.features.favourite.repository;

import com.fachrinfl.movie.features.tv.model.Tv;

import java.util.ArrayList;

public interface LoadTvCallback {
    void preExecute();

    void postExecute(ArrayList<Tv> tvs);
}
