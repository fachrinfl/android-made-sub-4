package com.fachrinfl.movie.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class TextViewBold extends android.support.v7.widget.AppCompatTextView {

    public TextViewBold(Context context) {
        super(context);
    }

    public TextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextViewBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setTypeface(Typeface.createFromAsset(
                getContext().getAssets(), "fonts/gotham/GothamBold.ttf"
        ));
    }
}
