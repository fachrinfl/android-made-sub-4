package com.fachrinfl.movie.features.favourite.view.fragment;


import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.fachrinfl.movie.R;
import com.fachrinfl.movie.features.favourite.db.tv.TvHelper;
import com.fachrinfl.movie.features.favourite.repository.LoadTvCallback;
import com.fachrinfl.movie.features.tv.adapter.TvAdapter;
import com.fachrinfl.movie.features.tv.model.Tv;
import com.fachrinfl.movie.utilities.GridSpacingItemDecoration;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class FavouriteTvFragment extends Fragment implements LoadTvCallback {

    private TvHelper tvHelper;
    private TvAdapter tvAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    int spacing = 50; // 50px
    boolean includeEdge = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite_tv, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getPopularTv();

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorLabel));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPopularTv();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.rvTv);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(
                this.getResources().getConfiguration().orientation
                        == Configuration.ORIENTATION_PORTRAIT ? 2 : 4,
                spacing,
                includeEdge));
        progressBar = (ProgressBar) view.findViewById(R.id.pb_loading);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.sl_ftv);

        tvHelper = new TvHelper(getContext());
        tvHelper.open();
    }

    private void getPopularTv() {
        new LoadMoviesAsync(tvHelper, this).execute();
    }

    @Override
    public void preExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void postExecute(ArrayList<Tv> tvs) {
        tvAdapter = new TvAdapter(getContext(), tvs);
        progressBar.setVisibility(View.INVISIBLE);
        swipeRefreshLayout.setRefreshing(false);
        initRv();
    }

    private static class LoadMoviesAsync extends AsyncTask<Void, Void, ArrayList<Tv>> {

        private final WeakReference<TvHelper> tvHelperWeakReference;
        private final WeakReference<LoadTvCallback> loadTvCallbackWeakReference;

        public LoadMoviesAsync(TvHelper tvHelper, LoadTvCallback loadTvCallback) {
            tvHelperWeakReference = new WeakReference<>(tvHelper);
            loadTvCallbackWeakReference = new WeakReference<>(loadTvCallback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadTvCallbackWeakReference.get().preExecute();
        }

        @Override
        protected ArrayList<Tv> doInBackground(Void... voids) {
            return tvHelperWeakReference.get().getAllTvs();
        }

        @Override
        protected void onPostExecute(ArrayList<Tv> tvs) {
            super.onPostExecute(tvs);
            loadTvCallbackWeakReference.get().postExecute(tvs);
        }
    }

    private void initRv() {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(tvAdapter);
        tvAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tvHelper.close();
    }
}
