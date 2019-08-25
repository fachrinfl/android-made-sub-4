package com.fachrinfl.movie.features.movie.service;

import com.fachrinfl.movie.features.movie.model.MovieResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieDataServices {

    @GET("movie/popular")
    Observable<MovieResponse> getPopularMovie(@Query("api_key") String apiKey);

}
