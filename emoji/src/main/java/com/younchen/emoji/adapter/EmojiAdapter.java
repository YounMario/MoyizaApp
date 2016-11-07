package com.younchen.emoji.adapter;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.younchen.emoji.R;
import com.younchen.emoji.bean.EmojiIcon;
import com.younchen.emoji.core.EmojiLoaderManager;
import com.younchen.emoji.holder.OlderBaseHolder;

import java.util.List;

/**
 * Created by pc on 2016/5/13.
 */
public class EmojiAdapter extends OlderBaseAdapter<EmojiIcon> {

    public EmojiAdapter(int layoutId) {
        super(layoutId);
    }

    public EmojiAdapter(List<EmojiIcon> data) {
         this(R.layout.item_emoji);
        loadData(data);
    }

    private void loadData(List<EmojiIcon> data) {
        for (EmojiIcon emojiIcon :
                data) {
            addItem(emojiIcon);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void setUpView(int position, View view, ViewGroup viewGroup, OlderBaseHolder holder) {
        ImageView imageView = (ImageView) holder.getView(R.id.img_emoji);
        EmojiLoaderManager.getInstance().getEvn().getImageLoader().loadImage(imageView,getItem(position).getUrl());
    }
}
