package com.younchen.chat.ui.adapter.base;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by mryoun on 2016/7/27.
 */
public abstract class MulitTypeAdapter<T> extends BaseAdapter<T> {

    protected MulitTypeAdapterSupport support;

    public MulitTypeAdapter(Context context,MulitTypeAdapterSupport support) {
        super(context, -1);
        this.support = support;
    }

    public void setMulitTypeAdapterSupport(MulitTypeAdapterSupport support){
        this.support = support;
    }

    @Override
    public int getItemViewType(int position) {
        return support.getType(position);
    }

    @Override
    public T getItem(int postion) {
        if (support.headItemUseDataSource() && support.hasHeader()) {
            return super.getItem(postion - 1);
        } else {
            return super.getItem(postion);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (position == support.getHeaderItemPosition() && support.hasHeader()) {
            coverHeadItem(holder, position);
        } else {
            covert(holder, getItemViewType(position), data.get(position));
        }
    }

    protected abstract void coverHeadItem(ViewHolder holder, int position);


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutId = support.layoutId(viewType);
        return ViewHolder.get(parent,layoutId);
    }

}
