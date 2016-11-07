package com.younchen.emoji.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.younchen.emoji.adapter.EmojiAdapter;
import com.younchen.emoji.bean.EmojiIcon;
import com.younchen.emoji.listener.IconItemClickListener;

import java.util.List;

/**
 * Created by younchen on 2016/5/13.
 */
public class EmojiPanel extends LinearLayout {

    private ViewPager viewPager;
    private EmojiPagerAdapter emojiPageAdapter;
    private int emojiTotalCount;
    private int pages;
    private int COUNT_FOR_EACH_PAGE = 28;
    private IconItemClickListener iconItemClickListener;

    private Context context;
    private int[] colors = {Color.BLUE, Color.GREEN, Color.RED};

    public EmojiPanel(Context context) {
        this(context, null);
    }

    public EmojiPanel(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmojiPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
        loadDate();
    }

    private void init(Context context) {
        this.context = context;
    }

    public void setOnIconItemClickListener(IconItemClickListener iconItemClickListener){
        this.iconItemClickListener=iconItemClickListener;
    }

    private void initView() {
        viewPager=new ViewPager(getContext());
        addView(viewPager);
    }

    public void loadDate() {

    }

    private void initData() {
//        List<EmojiIcon> emojiIcons = DisplayRules.getAllByType();
//        emojiTotalCount = emojiIcons.size();
//        pages = emojiTotalCount / COUNT_FOR_EACH_PAGE + (emojiTotalCount % COUNT_FOR_EACH_PAGE > 0 ? 1 : 0);
//        GridView[] gridViews = new GridView[pages];
//        for (int i = 0; i < pages; i++) {
//            GridView mGridView = new GridView(context);
//            mGridView.setLayoutParams(new GridView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//            mGridView.setNumColumns(7);
//            mGridView.setColumnWidth(GridView.AUTO_FIT);
////            mGridView.setVerticalSpacing(5);
////            mGridView.setHorizontalSpacing(5);
//            final EmojiAdapter adapter = new EmojiAdapter(emojiIcons.subList(1, 28));
//            mGridView.setAdapter(adapter);
//            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    if(iconItemClickListener!=null){
//                        EmojiIcon icon=adapter.getItem(position);
//                        iconItemClickListener.onItemSelected(icon);
//                    }
//                }
//            });
//            gridViews[i] = mGridView;
//        }
//        emojiPageAdapter = new EmojiPagerAdapter(gridViews);
//        viewPager.setAdapter(emojiPageAdapter);
    }

    class EmojiPagerAdapter extends PagerAdapter {

        View[] gridViews;

        public EmojiPagerAdapter(View[] views) {
            this.gridViews = views;
        }

        @Override
        public int getCount() {
            return gridViews.length;
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(gridViews[position]);
            return gridViews[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(gridViews[position]);
        }
    }

}
