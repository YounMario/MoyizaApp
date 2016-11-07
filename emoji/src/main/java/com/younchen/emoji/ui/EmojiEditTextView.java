package com.younchen.emoji.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by longquan on 2016/6/7.
 */
public class EmojiEditTextView extends EditText{

    public EmojiEditTextView(Context context) {
        super(context);
        initView();
    }

    public EmojiEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public EmojiEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView(){

    }

}
