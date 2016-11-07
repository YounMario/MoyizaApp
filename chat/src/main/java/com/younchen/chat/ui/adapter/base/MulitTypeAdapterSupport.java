package com.younchen.chat.ui.adapter.base;

/**
 * Created by 龙泉 on 2016/7/27.
 */
public interface MulitTypeAdapterSupport {

    public int getType(int position);

    public int layoutId(int type);

    int getHeaderItemPosition();

    boolean headItemUseDataSource();

    boolean hasHeader();
}
