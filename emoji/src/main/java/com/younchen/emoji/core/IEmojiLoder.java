package com.younchen.emoji.core;

import com.younchen.emoji.bean.EmojiCategory;

/**
 * Created by 龙泉 on 2016/7/22.
 */
public interface IEmojiLoder {

    public void getCategory(EmojiLoaderManager.CategoryLoadListener listener);

    public void loadCategoryEmoji(EmojiCategory category, EmojiLoaderManager.EmojiLoadListener listener);
}
