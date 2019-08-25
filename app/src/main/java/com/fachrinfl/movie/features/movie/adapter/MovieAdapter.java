package com.fachrinfl.movie.features.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fachrinfl.movie.R;
import com.fachrinfl.movie.features.movie.model.Movie;
import com.fachrinfl.movie.features.movie.view.activity.MovieActivity;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private Context context;
    private ArrayList<Movie> movieArrayList;

    public MovieAdapter(Context context, ArrayList<Movie> movieArrayList) {
        this.context = context;
        this.movieArrayList = movieArrayList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rv_movie, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        Movie movie = movieArrayList.get(i);

        movieViewHolder.tvTitle.setText(movie.getTitle());
        movieViewHolder.tvRelease.setText(movie.getReleaseDate());

        String imagePath="https://image.tmdb.org/t/p/w500" + movie.getPosterPath();

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.place_holder_picture);
        requestOptions.centerCrop();

        Glide.with(context)
                .load(imagePath)
                .apply(requestOptions)
                .into(movieViewHolder.ivCover);

    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivCover;
        public TextView tvTitle, tvRelease;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            ivCover = (ImageView) itemView.findViewById(R.id.ivCover);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvRelease = (TextView) itemView.findViewById(R.id.tvRelease);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Movie selectedMovie = movieArrayList.get(position);

                        Intent intent = new Intent(context, MovieActivity.class);
                        intent.putExtra("movie", selectedMovie);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
