package com.younchen.emoji.core;


import com.younchen.emoji.bean.EmojiCategory;
import com.younchen.emoji.bean.EmojiIcon;

import java.util.List;

/**
 * Created by 龙泉 on 2016/7/20.
 */
public class EmojiLoaderManager {

    private static EmojiLoaderManager mangerIns;
    private IEmojiLoder iEmojiLoder;
    private EmojiConfigure configure;

    public static EmojiLoaderManager getInstance() {
        if (mangerIns == null) {
            mangerIns = new EmojiLoaderManager();
        }
        return mangerIns;
    }

    public void init(EmojiConfigure configure) {
        this.configure = configure;
    }

    public void setEmojiLoader(IEmojiLoder iEmojiLoder) {
        this.iEmojiLoder = iEmojiLoder;
    }

    public EmojiConfigure getEvn(){
        return configure;
    }

    public void getCategory(CategoryLoadListener listener) {
        IEmojiLoder emojiLoder = getEmojiLoader();
        emojiLoder.getCategory(listener);
    }


    private IEmojiLoder getEmojiLoader() {
        if (iEmojiLoder == null) {
            iEmojiLoder = new DefaultEmojiLoader(configure.getContext());
        }
        return iEmojiLoder;
    }

    public void getEmojiIcons(EmojiCategory category, EmojiLoadListener listener) {
        IEmojiLoder emojiLoder = getEmojiLoader();
        emojiLoder.loadCategoryEmoji(category, listener);
    }


    public interface CategoryLoadListener {

        public void onLoadSuccess(List<EmojiCategory> categories);

        public void onLoadFailed(String msg);

    }

    public interface EmojiLoadListener {

        public void onLoadSuccess(List<EmojiIcon> faceIcons);

        public void onLoadFailed(String msg);
    }
}
