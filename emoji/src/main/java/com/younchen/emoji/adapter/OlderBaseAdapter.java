package com.younchen.emoji.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.younchen.emoji.holder.OlderBaseHolder;

import java.util.LinkedList;

/**
 * Created by pc on 2016/5/13.
 */
public abstract class OlderBaseAdapter<T> extends BaseAdapter{

    protected LinkedList<T> objects;
    protected int layoutId;
    protected OlderBaseHolder holder;

    public OlderBaseAdapter(int layoutId){
        objects=new LinkedList<>();
        this.layoutId=layoutId;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public T getItem(int i) {
        return objects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void addItem(T item){
        objects.add(item);
        notifyDataSetChanged();
    }

    public void removeItem(int index){
        objects.remove(index);
        notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        OlderBaseHolder holder= OlderBaseHolder.get(viewGroup,view,position,layoutId);
        View coverView=holder.getConvertView();
        setUpView(position,coverView,viewGroup,holder);
        return coverView;
    }

    public abstract void setUpView(int position,View view,ViewGroup viewGroup,OlderBaseHolder holder);

}
