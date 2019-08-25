package com.fachrinfl.movie.features.tv.model;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import com.fachrinfl.movie.R;
import com.fachrinfl.movie.features.tv.service.RetrofitTvInstance;
import com.fachrinfl.movie.features.tv.service.TvDataServices;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class TvRepository {
    private Application application;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<List<Tv>> tvLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> isError = new MutableLiveData<>();
    private ArrayList<Tv> tvs;
    private Observable<TvResponse> tvResponseObservable;

    public TvRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Tv>> getPopularTvLiveData() {
        isLoading.setValue(true);
        isError.setValue(false);
        tvs = new ArrayList<>();
        TvDataServices getTvDataService = RetrofitTvInstance.getService();
        tvResponseObservable = getTvDataService
                .getPopularTv(application.getApplicationContext().getString(R.string.api_key));

       compositeDisposable.add(
               tvResponseObservable
                       .subscribeOn(Schedulers.io())
                       .observeOn(AndroidSchedulers.mainThread())
                       .flatMap(new Function<TvResponse, Observable<Tv>>() {
                           @Override
                           public Observable<Tv> apply(TvResponse tvResponse) throws Exception {
                               return Observable.fromArray(tvResponse.getResults().toArray(new Tv[0]));
                           }
                       })
                       .subscribeWith(new DisposableObserver<Tv>() {
                           @Override
                           public void onNext(Tv tv) {
                               tvs.add(tv);
                           }

                           @Override
                           public void onError(Throwable e) {
                               isLoading.setValue(false);
                               isError.setValue(true);
                           }

                           @Override
                           public void onComplete() {
                               tvLiveData.postValue(tvs);
                               isLoading.setValue(false);
                               isError.setValue(false);
                           }
                       }));
        return tvLiveData;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<Boolean> getIsError() {
        return isError;
    }

    public void clear() {
        compositeDisposable.clear();
    }
}
