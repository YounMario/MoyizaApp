package com.younchen.chat.ui.components;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.younchen.chat.utils.EventUtil;
import com.younchen.chat.utils.YounLog;

/**
 * Created by Administrator on 2015/11/23.
 */
public class MyViewPager extends ViewPager {


    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        String action = EventUtil.ation(ev);
        YounLog.e("onInterceptTouchEvent:" + action);
        if(!isFakeDragging()){
          YounLog.e("draging");
        }
        //return false;
        return super.onInterceptTouchEvent(ev);
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
