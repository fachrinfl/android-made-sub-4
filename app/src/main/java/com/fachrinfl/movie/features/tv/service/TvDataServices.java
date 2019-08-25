package com.fachrinfl.movie.features.tv.service;

import com.fachrinfl.movie.features.tv.model.TvResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TvDataServices {

    @GET("tv/popular")
    Observable<TvResponse> getPopularTv(@Query("api_key") String apiKey);

}
