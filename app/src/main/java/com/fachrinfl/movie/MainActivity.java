package com.fachrinfl.movie;

import android.content.Intent;
import android.graphics.Typeface;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.fachrinfl.movie.features.favourite.view.fragment.FavouriteFragment;
import com.fachrinfl.movie.features.movie.view.fragment.MovieFragment;
import com.fachrinfl.movie.features.tv.view.fragment.TvFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    public View viewDropShadow;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Movie");

        viewDropShadow = (View) findViewById(R.id.view);

        ((TextView)toolbar.getChildAt(0)).setTypeface(Typeface.createFromAsset(
                getApplicationContext().getAssets(), "fonts/gotham/GothamBold.ttf"
        ));

        setSupportActionBar(toolbar);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        if(savedInstanceState == null) {
            showFragment(R.id.nb_movie);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        showFragment(id);
        return true;
    }

    private void showFragment(int itemId) {
        switch (itemId) {
            case R.id.nb_movie:
                viewDropShadow.setVisibility(View.VISIBLE);
                fragment = new MovieFragment();
                break;
            case  R.id.nb_tv:
                viewDropShadow.setVisibility(View.VISIBLE);
                fragment = new TvFragment();
                break;
            case  R.id.nb_favourite:
                viewDropShadow.setVisibility(View.INVISIBLE);
                fragment = new FavouriteFragment();
                break;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_content, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            int screen = data.getIntExtra("acivity", 0);
            showFragment(screen);
        }
    }
}
