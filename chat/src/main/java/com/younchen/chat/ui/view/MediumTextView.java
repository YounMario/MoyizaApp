package com.younchen.chat.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.younchen.chat.R;

/**
 * Created by pc on 2016/3/25.
 */
public class MediumTextView extends TextView {

    private int textColor;

    public MediumTextView(Context context) {
        this(context, null);
    }

    public MediumTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MediumTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MediumTextView);
        textColor = array.getColor(R.styleable.MediumTextView_medium_text_color, -1);
        array.recycle();
        init();
    }


    public void init() {
        //NotoSansCJKsc-Regular.ttf
        //NotoSansCJKsc-Medium.ttf


        Typeface typeface = TextureManager.getInstance().getTypeFace(getContext(), "fonts/Black.ttf");
        setTypeface(typeface);
        if (textColor != -1) {
            setTextColor(textColor);
        }
    }
}
