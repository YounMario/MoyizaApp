package com.younchen.emoji.holder;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by longquan on 2016/5/13.
 */
public class OlderBaseHolder {

    private int position;
    private SparseArray<View> viewArray;
    private View convertView;

    public OlderBaseHolder(int position, int layoutId, ViewGroup parent){
        this.position=position;
        this.viewArray=new SparseArray();
        this.convertView= LayoutInflater.from(parent.getContext()).inflate(layoutId,parent,false);
        convertView.setTag(this);
    }

    public static OlderBaseHolder get(ViewGroup parent,View convertView,int position,int layoutId){
        return convertView==null ? new OlderBaseHolder(position,layoutId,parent): (OlderBaseHolder) convertView.getTag();
    }

    public View getConvertView(){
        return convertView;
    }

    public View getView(int resId){
        if(viewArray.get(resId)==null){
            View view= convertView.findViewById(resId);
            viewArray.put(resId,view);
        }
        return viewArray.get(resId);
    }

}
