package com.fachrinfl.movie.features.tv.view.fragment;


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
import com.fachrinfl.movie.features.tv.adapter.TvAdapter;
import com.fachrinfl.movie.features.tv.model.Tv;
import com.fachrinfl.movie.features.tv.viewmodel.TvViewModel;
import com.fachrinfl.movie.utilities.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class TvFragment extends Fragment {

    private ArrayList<Tv> tvs = new ArrayList<>();
    private TvViewModel tvViewModel;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TvAdapter tvAdapter;
    int spacing = 50;
    boolean includeEdge = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv, container, false);
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
        tvViewModel = ViewModelProviders.of(this).get(TvViewModel.class);

    }

    private void getPopularTv() {
        tvViewModel.getPopularTv().observe(this, new Observer<List<Tv>>() {
            @Override
            public void onChanged(@Nullable List<Tv> tvsList) {
                tvs = (ArrayList<Tv>) tvsList;
                initRv();
            }
        });

        tvViewModel.getLoading().observe(this, new Observer<Boolean>() {
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

        tvViewModel.getError().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isError) {
                if (isError) {
                    Toast.makeText(getContext(), getResources().getString(R.string.request_failed), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initRv() {
        tvAdapter = new TvAdapter(getContext(), tvs);

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
        tvViewModel.clear();
    }
}
