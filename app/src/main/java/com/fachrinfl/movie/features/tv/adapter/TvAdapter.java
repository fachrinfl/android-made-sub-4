package com.fachrinfl.movie.features.tv.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fachrinfl.movie.R;
import com.fachrinfl.movie.features.tv.model.Tv;
import com.fachrinfl.movie.features.tv.view.activity.TvActivity;

import java.util.ArrayList;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.TvViewHolder>{

    private Context context;
    private ArrayList<Tv> tvArrayList;

    public TvAdapter(Context context, ArrayList<Tv> tvArrayList) {
        this.context = context;
        this.tvArrayList = tvArrayList;
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rv_tv, viewGroup, false);
        return new TvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvViewHolder tvViewHolder, int i) {
        Tv tv = tvArrayList.get(i);

        tvViewHolder.tvTitle.setText(tv.getOriginalName());
        tvViewHolder.tvRelease.setText(tv.getFirstAirDate());


        String imagePath="https://image.tmdb.org/t/p/w500" + tv.getPosterPath();

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.place_holder_picture);
        requestOptions.centerCrop();

        Glide.with(context)
                .load(imagePath)
                .apply(requestOptions)
                .into(tvViewHolder.ivCover);


    }

    @Override
    public int getItemCount() {
        return tvArrayList.size();
    }

    public class TvViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivCover;
        public TextView tvTitle, tvRelease;

        public TvViewHolder(@NonNull View itemView) {
            super(itemView);

            ivCover = (ImageView) itemView.findViewById(R.id.ivCover);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvRelease = (TextView) itemView.findViewById(R.id.tvRelease);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Tv selectedTv = tvArrayList.get(position);

                        Intent intent = new Intent(context, TvActivity.class);
                        intent.putExtra("tv", selectedTv);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
