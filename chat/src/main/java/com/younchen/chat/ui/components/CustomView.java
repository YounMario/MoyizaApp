package com.younchen.chat.ui.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.younchen.chat.utils.EventUtil;
import com.younchen.chat.utils.YounLog;

/**
 * Created by Administrator on 2015/11/23.
 */
public class CustomView extends RelativeLayout {
    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        String action = EventUtil.ation(ev);
        YounLog.e("onInterceptTouchEvent:" + action);
        //return false;
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        String action = EventUtil.ation(ev);
        YounLog.e("onTouchEvent:" + action);
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        String action = EventUtil.ation(ev);
        YounLog.e("dispatchTouchEvent:" + action);
        return super.dispatchTouchEvent(ev);
    }
}
