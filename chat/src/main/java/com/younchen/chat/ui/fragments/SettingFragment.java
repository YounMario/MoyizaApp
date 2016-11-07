package com.younchen.chat.ui.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.younchen.chat.R;
import com.younchen.chat.model.TestFileModel;
import com.younchen.chat.ui.PlayeMusicIconView;
import com.younchen.chat.widget.PullUpLayout;
import com.younchen.emoji.core.DefaultEmojiLoader;

import java.util.ArrayList;

public class SettingFragment extends BaseFragment implements View.OnClickListener {

    private View pressToSpeak;
    private ImageView imgView;
    private AnimationDrawable anim;

    private PlayeMusicIconView myDrawble;

    private Button play, stop, next,btnUnzip;
    private PullUpLayout pullUpLayout;

    private TestFileModel testFileModel;

    private Context context;


    private ViewPager myPager;
    public SettingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myPager= (ViewPager) view.findViewById(R.id.my_pager);
        context=getContext();
        btnUnzip= (Button) view.findViewById(R.id.btn_unzip);

        EmojiPagerAdapter adapter=new EmojiPagerAdapter(null);
        myPager.setAdapter(adapter);
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initView(View view) {

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
    }

    private void stopAnimation() {
    }

    private void playAnimation() {

    }

    class EmojiPagerAdapter extends PagerAdapter {

        View[] gridViews;

        public EmojiPagerAdapter(View[] views){
//            this.gridViews=views;
        }

        @Override
        public int getCount() {
            return 3;
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view=LayoutInflater.from(context).inflate(R.layout.layout_test,null);
            container.addView(view);
            return view;
//            return gridViews[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //  container.removeView(gridViews[position]);
        }
    }


}
