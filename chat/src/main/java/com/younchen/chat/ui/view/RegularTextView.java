package com.younchen.chat.ui.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by pc on 2016/3/25.
 */
public class RegularTextView extends TextView {
    public RegularTextView(Context context) {
        this(context, null);
    }

    public RegularTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RegularTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        Typeface typeface = TextureManager.getInstance().getTypeFace(getContext(), "fonts/NotoSansCJKsc-Regular.ttf");
        setTypeface(typeface);
    }
}
