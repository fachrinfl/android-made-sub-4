package com.fachrinfl.movie.features.movie.view.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
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
import android.widget.Toast;

import com.fachrinfl.movie.R;
import com.fachrinfl.movie.features.movie.adapter.MovieAdapter;
import com.fachrinfl.movie.features.movie.model.Movie;
import com.fachrinfl.movie.features.movie.viewmodel.MovieViewModel;
import com.fachrinfl.movie.utilities.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MovieFragment extends Fragment {

    private ArrayList<Movie> movies = new ArrayList<>();
    private MovieViewModel movieViewModel;
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
        return inflater.inflate(R.layout.fragment_movie, container, false);
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
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getPopularMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> moviesList) {
                movies = (ArrayList<Movie>) moviesList;
                initRv();
            }
        });

    }

    private void getPopularMovie() {
        movieViewModel.getPopularMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> moviesList) {
                movies = (ArrayList<Movie>) moviesList;
                initRv();
            }
        });

        movieViewModel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isLoading) {
                if (isLoading) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        movieViewModel.getError().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isError) {
                if (isError) {
                    Toast.makeText(getContext(), getResources().getString(R.string.request_failed), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initRv() {
        movieAdapter = new MovieAdapter(getContext(), movies);

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
        movieViewModel.clear();
    }
}
