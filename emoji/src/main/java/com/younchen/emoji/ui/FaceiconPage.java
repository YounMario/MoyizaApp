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
import com.younchen.emoji.bean.EmojiCategory;
import com.younchen.emoji.bean.EmojiIcon;
import com.younchen.emoji.core.EmojiLoaderManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 龙泉 on 2016/7/20.
 */
public class FaceiconPage extends ViewPager {

    private EmojiCategory category;
    private FaceIconPageAdapter adapter;

    private EmojiIconClickListener emojiIconClickListener;

    private final int EMOJI_COL_COUNT = 6;
    private final int EMOJI_ROW_COUNT = 4;
    private final int EMOJI_COUNT_EACH_PAGE = 24;

    private int[] subColors = {Color.BLACK, Color.GRAY};

    public FaceiconPage(Context context) {
        super(context);
    }

    public FaceiconPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public FaceiconPage(Context context, EmojiCategory category) {
        super(context);
        this.category = category;
        setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
    }

    public void setEmojiIconClickListener(EmojiIconClickListener emojiIconClickListener) {
        this.emojiIconClickListener = emojiIconClickListener;
    }

    public void onInit() {
        EmojiLoaderManager.getInstance().getEmojiIcons(category, new EmojiLoaderManager.EmojiLoadListener() {
            @Override
            public void onLoadSuccess(List<EmojiIcon> faceIcons) {
                ArrayList<View> subList = new ArrayList<>();
                int page = faceIcons.size() / EMOJI_COUNT_EACH_PAGE;
                page = page == 0 ? 1 : page;
                for (int i = 0; i < page; i++) {
                    GridView mGridView = new GridView(getContext());
                    mGridView.setLayoutParams(new GridView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                    mGridView.setNumColumns(6);
                    mGridView.setColumnWidth(GridView.AUTO_FIT);
                    int start = i * EMOJI_COUNT_EACH_PAGE;
                    int end;
                    if (start + EMOJI_COUNT_EACH_PAGE > faceIcons.size()) {
                        end = start + faceIcons.size() % EMOJI_COUNT_EACH_PAGE;
                    } else {
                        end = start + EMOJI_COUNT_EACH_PAGE;
                    }

                    final EmojiAdapter adapter = new EmojiAdapter(faceIcons.subList(start, end));
                    mGridView.setAdapter(adapter);
                    mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if(emojiIconClickListener != null){
                                emojiIconClickListener.onEmojiIconClicked(adapter.getItem(position));
                            }
                        }
                    });
                    subList.add(mGridView);
                }
                adapter = new FaceIconPageAdapter(subList);
                setAdapter(adapter);
            }

            @Override
            public void onLoadFailed(String msg) {

            }
        });
    }

    @Override
    public FaceIconPageAdapter getAdapter() {
        return adapter;
    }

    public EmojiCategory getCategory() {
        return this.category;
    }

    public void onDestory() {

    }

    class FaceIconPageAdapter extends PagerAdapter {

        private List<View> list;

        public FaceIconPageAdapter(List<View> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }


    public static interface EmojiIconClickListener {

        public void onEmojiIconClicked(EmojiIcon emojiIcon);

    }
}
