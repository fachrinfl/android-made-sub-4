package com.fachrinfl.movie.features.tv.view.activity;

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
import com.fachrinfl.movie.features.tv.model.Tv;

public class TvActivity extends AppCompatActivity {
    
    private Tv tv;
    private ImageView tvImage;
    private String image;
    private TextView tvTitle, tvSynopsis, tvReleaseDate;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;

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
