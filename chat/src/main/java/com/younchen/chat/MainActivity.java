package com.younchen.chat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.younchen.chat.entity.TabEntity;
import com.younchen.chat.model.MusicMonitor;
import com.younchen.chat.ui.BaseActivity;
import com.younchen.chat.ui.fragments.BaseFragment;
import com.younchen.chat.ui.fragments.ChatListFragment;
import com.younchen.chat.ui.fragments.FriendsCircleFragment;
import com.younchen.chat.ui.fragments.SettingFragment;
import com.younchen.chat.ui.fragments.UserListFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {

    private CommonTabLayout mTabLayout;
    private ViewPager viewPager;
    private ArrayList<BaseFragment> fragments;
    private String[] titles;
    private MusicMonitor musicMonitor;
    //tab 实体
    private ArrayList<CustomTabEntity> mTabEntities;

    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_unselect, R.mipmap.tab_speech_unselect,
            R.mipmap.tab_contact_unselect, R.mipmap.tab_more_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_select, R.mipmap.tab_speech_select,
            R.mipmap.tab_contact_select, R.mipmap.tab_more_select};

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
        mTabLayout = findView(R.id.tabLayout);
        viewPager = findView(R.id.viewPager);
        for (int i = 0; i < titles.length; i++) {
            CustomTabEntity entity = new TabEntity(titles[i], mIconSelectIds[i], mIconUnselectIds[i]);
            mTabEntities.add(entity);
        }

        fragments.add(new ChatListFragment());
        fragments.add(new FriendsCircleFragment());
        fragments.add(new UserListFragment());
        fragments.add(new SettingFragment());

        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        mTabLayout.setTabData(mTabEntities);


        //显示小红点
        mTabLayout.showMsg(1, 100);
        mTabLayout.setMsgMargin(1, -5, 5);
        mTabLayout.showMsg(0, 55);
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
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        musicMonitor=new MusicMonitor(this);
    }

    @Override
    protected void onDestroy() {
        if(musicMonitor!=null){
            musicMonitor.onDestory();
        }
        super.onDestroy();
    }

    @Override
    public void initData() {
        MusicCollecter musicCollecter=new MusicCollecter();
        titles = new String[]{"聊天", "资料", "联系人", "鸡蛋"};
        mTabEntities = new ArrayList<>();
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
