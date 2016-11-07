package com.younchen.chat.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.younchen.chat.animation.ElasticTranslate;
import com.younchen.utils.DimenUtil;

/**
 * Created by younchen on 2016/2/15.
 */
public class PullUpLayout extends LinearLayout {
    private final String TAG = "younchen";

    private float xStart, yStart, lastX, lastY, distance;
    private int mTouchSlop;
    private boolean isDraging;
    private boolean isUp;
    private int mValidOffset = DimenUtil.dip2px(getContext(), 26);
    private PullUpCallBack pullUpCallBack;
    private MyAnimatorListener listener;


    public PullUpLayout(Context context) {
        this(context, null);
    }

    public PullUpLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullUpLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mTouchSlop = viewConfiguration.getScaledTouchSlop();
        init();
    }

    private void init() {
        isDraging = false;
    }

    public void setPullUpCallBack(PullUpCallBack pullUpCallBack) {
        this.pullUpCallBack = pullUpCallBack;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float distance;
        lastY = event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onPreTouch(event);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                distance = lastY - yStart;
                endMoving(lastY, distance);
                break;
            case MotionEvent.ACTION_MOVE:
                distance = lastY - yStart;
                isUp = distance > 0 ? false : true;
                moveLayout(distance);
                break;
        }
        return super.onTouchEvent(event);
    }

    private void moveLayout(float distance) {
        float height = getPullLayoutHeight(false);
        float absDistance = Math.abs(distance);
//        float value = Math.max(-height, Math.min(0, getTranslationY() + distance));
        float translationY = 0;
        if (isUp)
            translationY = absDistance >= height ? -height : -absDistance;
        else {
            translationY = absDistance >= height ? height : absDistance;
            translationY = getTranslationY() - translationY;
        }

        Log.d(TAG, "translation=" + translationY + " distance=" + distance);
        setTranslationY(translationY);
    }


    private void endMoving(float endY, float distance) {
        float height = getPullLayoutHeight(false);
        Animator animator = null;
        if (Math.abs(distance) > mValidOffset) {
            if (isUp) {
                animator = pullUpAnimation(getTranslationY(), -height, 1000, new MyAnimatorListener(isUp));
            } else {
                animator = pullDownAnimation(0, 1000, new MyAnimatorListener(isUp));
            }
        }
        if (animator != null) animator.start();
    }

    private Animator pullUpAnimation(float startY, float endY, int during, Animator.AnimatorListener listener) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "translationY", startY, endY);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(during);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (Float) valueAnimator.getAnimatedValue();
                float alpha = Math.max(0, Math.min(1f, value / -getPullLayoutHeight(false)));
            }
        });
        animator.addListener(listener);
        return animator;
    }

    private ValueAnimator pullDownAnimation(final float endY, final int duration, Animator.AnimatorListener listener) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "translationY", endY);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (Float) valueAnimator.getAnimatedValue();
                float alpha = Math.max(0, Math.min(1f, value / -getPullLayoutHeight(false)));
            }
        });
        animator.addListener(listener);
        return animator;
    }

    public int getPullLayoutHeight(boolean force) {
        int myHeight = getMeasuredHeight();
        if (myHeight == 0 || force) {
            //Q1
            measure(MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED);
            myHeight = getMeasuredHeight();
        }
        return myHeight;
    }

    public void onPreTouch(MotionEvent event) {
        xStart = event.getRawX();
        lastY = yStart = event.getRawY();
    }

    public interface PullUpCallBack {

        public void onPrepare();

        public void onPulling();

        public void onOpened();

        public void onClose();
    }

    public class MyAnimatorListener extends AnimatorListenerAdapter {

        private boolean isUp;

        public MyAnimatorListener(boolean isUp) {
            this.isUp = isUp;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            if (isUp) {
                //  pullUpCallBack.onOpened();
            } else {
                //   pullUpCallBack.onClose();
            }
        }
    }
}
