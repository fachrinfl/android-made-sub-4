package com.fachrinfl.movie.features.tv.view.activity;

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
import com.fachrinfl.movie.features.favourite.db.tv.TvHelper;
import com.fachrinfl.movie.features.tv.model.Tv;

public class TvActivity extends AppCompatActivity {
    
    private Tv tv;
    private ImageView tvImage, favouriteImage;
    private String image;
    private TextView tvTitle, tvSynopsis, tvReleaseDate;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;

    private TvHelper tvHelper;
    private boolean favorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        appBarLayout = (AppBarLayout) findViewById(R.id.aplTv);
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
        tvImage = (ImageView) findViewById(R.id.ivTvLarge);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvSynopsis = (TextView) findViewById(R.id.tvOverview);
        tvReleaseDate = (TextView) findViewById(R.id.tvRelease);


        Intent intent = getIntent();

        if (intent.hasExtra("tv")) {

            tv = getIntent().getParcelableExtra("tv");


            image = tv.getBackdropPath();

            String path = "https://image.tmdb.org/t/p/original" + image;

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.place_holder_picture);
            requestOptions.centerCrop();

            Glide.with(this)
                    .load(path)
                    .apply(requestOptions)
                    .into(tvImage);

            getSupportActionBar().setTitle(tv.getOriginalName());

            tvTitle.setText(tv.getOriginalName());
            tvSynopsis.setText(tv.getOverview());
            tvReleaseDate.setText(tv.getFirstAirDate());

            tvHelper = new TvHelper(this);
            tvHelper.open();

            int isFavourite = tvHelper.queryByIdProvider(String.valueOf(tv.getId())).getCount();
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
                        tvHelper.insertProvider(tv);
                        Toast.makeText(getBaseContext(), "Add Favourite", Toast.LENGTH_SHORT).show();
                    } else {
                        favorite = false;
                        favouriteImage.setImageResource(R.drawable.icn_favourite_inactive);
                        tvHelper.deleteProvider(String.valueOf(tv.getId()));
                        Toast.makeText(getBaseContext(), "Remove Favourite", Toast.LENGTH_SHORT).show();
                    }
                }
            });
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
        int data = R.id.nb_tv;
        Intent intent = new Intent();
        intent.putExtra("activity", data);
        setResult(RESULT_OK, intent);
        finish();
    }
}
