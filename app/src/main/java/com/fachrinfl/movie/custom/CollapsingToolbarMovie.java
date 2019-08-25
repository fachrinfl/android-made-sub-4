package com.fachrinfl.movie.custom;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.fachrinfl.movie.R;

public class CollapsingToolbarMovie extends android.support.design.widget.CollapsingToolbarLayout {
    public CollapsingToolbarMovie(Context context) {
        super(context);
    }

    public CollapsingToolbarMovie(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CollapsingToolbarMovie(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setCollapsedTitleTypeface(Typeface.createFromAsset(
                getContext().getAssets(), "fonts/gotham/GothamBold.ttf"
        ));
        setCollapsedTitleTextColor(getResources().getColor(R.color.colorNavigationSelected));
        setExpandedTitleTypeface(Typeface.createFromAsset(
                getContext().getAssets(), "fonts/gotham/GothamBold.ttf"
        ));
        setExpandedTitleColor(getResources().getColor(R.color.colorPrimary));
    }
}
