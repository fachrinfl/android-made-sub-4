package com.fachrinfl.movie.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class TextViewBook extends android.support.v7.widget.AppCompatTextView {
    public TextViewBook(Context context) {
        super(context);
    }

    public TextViewBook(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextViewBook(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setTypeface(Typeface.createFromAsset(
                getContext().getAssets(), "fonts/gotham/GothamBook.ttf"
        ));
    }
}
