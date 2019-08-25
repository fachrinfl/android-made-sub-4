package com.fachrinfl.movie.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class TextViewMedium extends AppCompatTextView {

    public TextViewMedium(Context context) {
        super(context);
    }

    public TextViewMedium(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextViewMedium(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setTypeface(
                Typeface.createFromAsset(
                        getContext().getAssets(),
                        "fonts/gotham/GothamMedium.ttf"
                )
        );
    }
}
