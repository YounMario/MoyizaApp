package com.younchen.chat.ui.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.younchen.chat.R;

/**
 * Created by pc on 2016/3/24.
 */
public class IconTextView extends TextView {

    private char[] mText;

    public IconTextView(Context context) {
        this(context, null);
    }

    public IconTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setTypeface(getContext(), "fonts/icon.ttf");
    }

    public void setTypeface(Context context, String typefaceName) {
        final Typeface typeFace = TextureManager.getInstance().getTypeFace(context, typefaceName);
        if (typeFace != null)
            setTypeface(typeFace);
    }

}
