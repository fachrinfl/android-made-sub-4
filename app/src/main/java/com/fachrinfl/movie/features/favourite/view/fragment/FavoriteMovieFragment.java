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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.fachrinfl.movie.R;
import com.fachrinfl.movie.features.favourite.db.MovieHelper;
import com.fachrinfl.movie.features.favourite.repository.LoadMoviesCallback;
import com.fachrinfl.movie.features.movie.adapter.MovieAdapter;
import com.fachrinfl.movie.features.movie.model.Movie;
import com.fachrinfl.movie.utilities.GridSpacingItemDecoration;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment implements LoadMoviesCallback{

    private MovieHelper movieHelper;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MovieAdapter movieAdapter;
    int spacing = 50; // 50px
    boolean includeEdge = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getPopularMovie();

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorLabel));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPopularMovie();
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.rvMovie);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(
                this.getResources().getConfiguration().orientation
                        == Configuration.ORIENTATION_PORTRAIT ? 2 : 4,
                spacing,
                includeEdge));

        progressBar = (ProgressBar) view.findViewById(R.id.pb_loading);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.sl_fmovie);

        movieHelper = new MovieHelper(getContext());
        movieHelper.open();

    }

    private void getPopularMovie() {
        new LoadMoviesAsync(movieHelper, this).execute();
    }

    @Override
    public void preExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void postExecute(ArrayList<Movie> movies) {
        movieAdapter = new MovieAdapter(getContext(), movies);
        progressBar.setVisibility(View.INVISIBLE);
        swipeRefreshLayout.setRefreshing(false);
        initRv();

    }

    private static class LoadMoviesAsync extends AsyncTask<Void, Void, ArrayList<Movie>> {

        private final WeakReference<MovieHelper> movieHelperWeakReference;
        private final WeakReference<LoadMoviesCallback> loadMoviesCallbackWeakReference;

        public LoadMoviesAsync(MovieHelper movieHelper, LoadMoviesCallback loadMoviesCallback) {
            movieHelperWeakReference = new WeakReference<>(movieHelper);
            loadMoviesCallbackWeakReference = new WeakReference<>(loadMoviesCallback);
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadMoviesCallbackWeakReference.get().preExecute();
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            return movieHelperWeakReference.get().getAllMovies();
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            loadMoviesCallbackWeakReference.get().postExecute(movies);
        }
    }

    private void initRv() {

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        movieHelper.close();
    }
}
