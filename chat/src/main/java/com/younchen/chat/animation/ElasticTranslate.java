package com.younchen.chat.animation;

import android.view.View;

/**
 * zouguiquan
 */
public class ElasticTranslate {

    private float mAmplitude;
    private int mPhase = 3;
    private float mOffsetDistance;
    private long mTranslateDuration;
    private long mShakeDuration = 460;
    private float mStartDistance;
    private float mEndDistance;
    private long mDuration;
    private boolean mNeedShake = true;
    private float mTranslateFraction;
    private float mShakeFraction;
    private float mTranEndInterpolatedTime;
    private View mView;

    public ElasticTranslate(View view, float startDistance, float endDistance) {
        mStartDistance = startDistance;
        mEndDistance = endDistance;
        mOffsetDistance = mEndDistance - mStartDistance;
        mView = view;
    }

    public void setAmplitude(float amplitude) {
        mAmplitude = amplitude;
    }

    public void setPhase(int phase) {
        mPhase = phase;
    }

    public void setTranslateDuration(long duration) {
        mTranslateDuration = duration;
    }

    public void setShakeDuration(long duration) {
        mShakeDuration = duration;
    }

    public void prepareStart() {
        setDuration(mTranslateDuration + mShakeDuration);
        if (Math.abs(mOffsetDistance) <= Math.abs(mAmplitude)) {
            mNeedShake = false;
        }

        mTranslateFraction = mDuration * 1.0f / mTranslateDuration;
        mShakeFraction = mDuration * 1.0f / mShakeDuration;
        mTranEndInterpolatedTime = mTranslateDuration * 1.0f / mDuration;
    }

    public void setDuration(long druation) {
        mDuration = druation;
    }

    public float getStartDistance() {
        return mStartDistance;
    }

    public float getOffsetDistance() {
        return mOffsetDistance;
    }

    public void applyTransformation(float interpolatedTime) {
        long total = getDuration();
        float fraction = mTranslateDuration * 1.0f / total;
        float translateY = mStartDistance;

        if (mNeedShake) {
            if (interpolatedTime < fraction) {
                float newInterpolatedTime = translateInterpolatedTime(interpolatedTime);
                translateY += mOffsetDistance * newInterpolatedTime;
            } else {
                float newInterpolatedTime = shakeInterpolatedTime(interpolatedTime);
                translateY += mOffsetDistance;
                mAmplitude -= mAmplitude * (newInterpolatedTime * newInterpolatedTime * 0.82f);
                translateY += (float) (mAmplitude * Math.sin(mPhase * Math.PI * newInterpolatedTime + Math.PI));
            }
        } else {
            translateY += mOffsetDistance * interpolatedTime;
        }

        mView.setTranslationY(translateY);
    }

    public long getDuration() {
        return mDuration;
    }

    int tranInterpolatedTimeCount = 0;
    private float translateInterpolatedTime(float baseInterpolatedTime) {
        float interpolatedTime = 0f;
        if (baseInterpolatedTime == 0) {
            if (tranInterpolatedTimeCount == 0) {
                interpolatedTime = 0.06f;
            } else if (tranInterpolatedTimeCount == 1) {
                interpolatedTime = 0.1f;
            }
            tranInterpolatedTimeCount++;
        } else {
            interpolatedTime = baseInterpolatedTime * mTranslateFraction;
            interpolatedTime = 1.0f - (1.0f - interpolatedTime) * (1.0f - interpolatedTime);
//          interpolatedTime = (float)(1.0f - Math.pow((1.0f - interpolatedTime), 2 * 1.05f));
        }
        return interpolatedTime;
    }

    private float shakeInterpolatedTime(float baseInterpolatedTime) {
        float interpolatedTime = (baseInterpolatedTime - mTranEndInterpolatedTime) * mShakeFraction;
        return interpolatedTime;
    }
}
