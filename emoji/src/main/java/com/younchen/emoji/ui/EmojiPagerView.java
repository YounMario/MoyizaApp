package com.younchen.emoji.ui;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.younchen.emoji.R;
import com.younchen.emoji.bean.EmojiCategory;
import com.younchen.emoji.bean.EmojiIcon;
import com.younchen.emoji.core.EmojiLoaderManager;
import com.younchen.emoji.ui.tab.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 龙泉 on 2016/7/20.
 */
public class EmojiPagerView extends LinearLayout {

    private ViewPager parentPager;
    private PagerSlidingTabStrip tab;
    private EmojiClickListener emojiClickListener;

    public EmojiPagerView(Context context) {
        super(context);
    }

    public EmojiPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmojiPagerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    public void init() {
        initView();
        EmojiLoaderManager.getInstance().getCategory(new EmojiLoaderManager.CategoryLoadListener() {
            @Override
            public void onLoadSuccess(List<EmojiCategory> categories) {
                ArrayList<FaceiconPage> list = new ArrayList<>();
                for (EmojiCategory category : categories) {
                    FaceiconPage page = new FaceiconPage(getContext(), category);
                    page.setEmojiIconClickListener(new FaceiconPage.EmojiIconClickListener() {
                        @Override
                        public void onEmojiIconClicked(EmojiIcon emojiIcon) {
                            if (emojiClickListener != null) {
                                emojiClickListener.onEmojiClicked(emojiIcon);
                            }
                        }
                    });
                    list.add(page);
                }
                parentPager.setAdapter(new ContainerPager(list));
            }

            @Override
            public void onLoadFailed(String msg) {

            }
        });

        tab.setViewPager(parentPager);
        tab.setTabPaddingLeftRight(0);
    }

    private void initView() {
        tab = (PagerSlidingTabStrip) findViewById(R.id.tab);
        parentPager = (ViewPager) findViewById(R.id.container_viewpager);
    }


    public void setOnEmojiCliclickListener(EmojiClickListener emojiClickListener) {
        this.emojiClickListener = emojiClickListener;
    }

    class ContainerPager extends PagerAdapter implements PagerSlidingTabStrip.IconTabProvider {

        List<FaceiconPage> list;

        public ContainerPager(List<FaceiconPage> list) {
            this.list = list;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            list.get(position).onInit();
            container.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            list.get(position).onDestory();
            container.removeView(list.get(position));
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getPageIconResId(int position) {
            return -1;
        }

        @Override
        public String getPageIconImage(int position) {
            EmojiCategory category = list.get(position).getCategory();
            return category.getIconUrl();
        }
    }

    public static interface EmojiClickListener {

        public void onEmojiClicked(EmojiIcon emoji);
    }
}
