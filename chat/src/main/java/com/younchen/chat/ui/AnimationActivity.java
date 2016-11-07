package com.younchen.chat.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.younchen.chat.R;
import com.younchen.chat.widget.PullUpLayout;

public class AnimationActivity extends AppCompatActivity {

    RelativeLayout titleBarLayout;
    PullUpLayout pullUpLayout;

    public static void star(Context context) {
        Intent intent = new Intent(context, AnimationActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        titleBarLayout = (RelativeLayout) findViewById(R.id.layout_title_bar);
        pullUpLayout= (PullUpLayout) findViewById(R.id.pullUpView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        pullUpLayout.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        playAnimationBound();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_animation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void playAnimationBound() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator animatorUp = ObjectAnimator
                        .ofFloat(titleBarLayout, "translationY", getWindow().getDecorView().getHeight(), 0)
                        .setDuration(500);

                ObjectAnimator dropDown = ObjectAnimator
                        .ofFloat(titleBarLayout, "translationY", 400, 0)
                        .setDuration(500);
                dropDown.setInterpolator(new BounceInterpolator());

                AnimatorSet animSetXY = new AnimatorSet();
                animSetXY.play(dropDown).after(animatorUp);
                animSetXY.start();
            }
        }, 1000);
    }

    public void title() {

    }

    private void initView() {

    }

    //private ViewGroup.LayoutParams getLayoutParams(ViewGroup viewGroup) {

//        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) viewGroup.getLayoutParams();
//        params.
//
//        if (Build.VERSION.SDK_INT >= 18) {
//            params.setMargins(0, KFilterStatusBar.getStatusBarHeight(mContext), 0, 0);
//        }else {
//            params.setMargins(0, 0, 0, 0);
//        }
//        layout.requestLayout();
//        return params;
    // }
}
