package com.fachrinfl.movie.features.tv.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.fachrinfl.movie.features.tv.model.Tv;
import com.fachrinfl.movie.features.tv.model.TvRepository;

import java.util.List;

public class TvViewModel extends AndroidViewModel {

    private TvRepository tvRepository;

    public TvViewModel(@NonNull Application application) {
        super(application);
        tvRepository = new TvRepository(application);
    }

    public LiveData<List<Tv>> getPopularTv() {
        return tvRepository.getPopularTvLiveData();
    }

    public LiveData<Boolean> getLoading() {
        return tvRepository.getIsLoading();
    }

    public LiveData<Boolean> getError() {
        return tvRepository.getIsError();
    }

    public void clear () {
        tvRepository.clear();
    }
}
