package com.fachrinfl.movie.features.movie.view.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fachrinfl.movie.R;
import com.fachrinfl.movie.features.movie.model.Movie;

public class MovieActivity extends AppCompatActivity {

    private Movie movie;
    private ImageView movieImage;
    private String image;
    private TextView movieTitle, movieSynopsis, movieReleaseDate;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        appBarLayout = (AppBarLayout) findViewById(R.id.aplMovie);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if (Math.abs(i) == appBarLayout.getTotalScrollRange()) {
                    setNavigationToolbarColor(false);
                } else if (i == 0) {
                    setNavigationToolbarColor(true);
                } else {
                    setNavigationToolbarColor(true);
                }
            }
        });


        movieImage = (ImageView) findViewById(R.id.ivMovieLarge);
        movieTitle = (TextView) findViewById(R.id.tvTitle);
        movieSynopsis = (TextView) findViewById(R.id.tvOverview);
        movieReleaseDate = (TextView) findViewById(R.id.tvRelease);


        Intent intent = getIntent();

        if (intent.hasExtra("movie")) {

            movie = getIntent().getParcelableExtra("movie");


            image = movie.getBackdropPath();

            String path = "https://image.tmdb.org/t/p/original" + image;

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.place_holder_picture);
            requestOptions.centerCrop();

            Glide.with(this)
                    .load(path)
                    .apply(requestOptions)
                    .into(movieImage);

            getSupportActionBar().setTitle(movie.getTitle());

            movieTitle.setText(movie.getTitle());
            movieSynopsis.setText(movie.getOverview());
            movieReleaseDate.setText(movie.getReleaseDate());
        }
    }

    private void setNavigationToolbarColor(boolean state) {
        toolbar
                .getNavigationIcon()
                .setColorFilter(
                        getResources()
                                .getColor(
                                        state == true
                                                ? R.color.colorPrimary
                                                : R.color.colorNavigationSelected),
                        PorterDuff.Mode.SRC_ATOP
                );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                backData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backData();
    }

    private void backData () {
        int data = R.id.nb_movie;
        Intent intent = new Intent();
        intent.putExtra("activity", data);
        setResult(RESULT_OK, intent);
        finish();
    }

}
