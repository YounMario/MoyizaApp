package com.younchen.chat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.younchen.chat.ui.BaseActivity;
import com.younchen.chat.ui.fragments.BaseFragment;
import com.younchen.chat.ui.fragments.ChatListFragment;
import com.younchen.chat.ui.fragments.FriendsCircleFragment;
import com.younchen.chat.ui.fragments.SettingFragment;
import com.younchen.chat.ui.fragments.UserListFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {

    private ViewPager viewPager;
    private ArrayList<BaseFragment> fragments;
    private BottomNavigationBar bottomBar;

    private MyViewPagerAdapter myViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        initEvent();
    }


    @Override
    public void initView() {
        getRootView();
        bottomBar = (BottomNavigationBar) findViewById(R.id.bottom_bar);
        bottomBar.addItem(new BottomNavigationItem(R.mipmap.tab_home_unselect, "Home"))
                .addItem(new BottomNavigationItem(R.mipmap.tab_speech_unselect, "Books"))
                .addItem(new BottomNavigationItem(R.mipmap.tab_contact_unselect, "Music"))
                .addItem(new BottomNavigationItem(R.mipmap.tab_more_unselect, "Movies & TV"))
                .initialise();


        bottomBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {

            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });

        viewPager = findView(R.id.viewPager);
        fragments.add(new ChatListFragment());
        fragments.add(new FriendsCircleFragment());
        fragments.add(new UserListFragment());
        fragments.add(new SettingFragment());

        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(myViewPagerAdapter);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (fragments != null && fragments.size() > 0) {
            for (int i = 0; i < fragments.size(); i++) {
                fragments.get(i).onToucnEvent(event);
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void initEvent() {

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void initData() {
        fragments = new ArrayList<>();
    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {

        private List<BaseFragment> fragments;


        public MyViewPagerAdapter(FragmentManager fm, List<BaseFragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
