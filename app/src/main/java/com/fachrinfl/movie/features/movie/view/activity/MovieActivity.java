package com.fachrinfl.movie.features.movie.view.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fachrinfl.movie.R;
import com.fachrinfl.movie.features.favourite.db.movie.MovieHelper;
import com.fachrinfl.movie.features.movie.model.Movie;

public class MovieActivity extends AppCompatActivity {

    private Movie movie;
    private ImageView movieImage, favouriteImage;
    private String image;
    private TextView movieTitle, movieSynopsis, movieReleaseDate;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;

    public static String _ID = "_id";
    public static String TITLE = "title";
    public static String OVERVIEW = "overview";
    public static String BACKDROP_PATH = "backdrop_path";
    public static String POSTER_PATH = "poster_path";
    public static String RELEASE_DATE = "release_date";

    private MovieHelper movieHelper;
    private boolean favorite = false;

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


        favouriteImage = (ImageView) findViewById(R.id.ivFavourite);
        movieImage = (ImageView) findViewById(R.id.ivMovieLarge);
        movieTitle = (TextView) findViewById(R.id.tvTitle);
        movieSynopsis = (TextView) findViewById(R.id.tvOverview);
        movieReleaseDate = (TextView) findViewById(R.id.tvRelease);

        movieHelper = new MovieHelper(this);
        movieHelper.open();


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

        int isFavourite = movieHelper.queryByIdProvider(String.valueOf(movie.getId())).getCount();
        if (isFavourite > 0) {
            favorite = true;
            favouriteImage.setImageResource(R.drawable.icn_favourite_selected);
        } else {
            favorite = false;
            favouriteImage.setImageResource(R.drawable.icn_favourite_inactive);
        }

        favouriteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!favorite) {
                    favorite = true;
                    favouriteImage.setImageResource(R.drawable.icn_favourite_selected);
                    movieHelper.insertProvider(movie);
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.dtl_favourite), Toast.LENGTH_SHORT).show();
                } else {
                    favorite = false;
                    favouriteImage.setImageResource(R.drawable.icn_favourite_inactive);
                    movieHelper.deleteProvider(String.valueOf(movie.getId()));
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.dtl_unfavourite), Toast.LENGTH_SHORT).show();
                }
            }
        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (movieHelper != null) {
            movieHelper.close();
        }
    }
}
