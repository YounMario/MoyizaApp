package com.younchen.chat.ui.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.younchen.chat.R;
import com.younchen.chat.ui.adapter.listener.MyItemClickListener;
import com.younchen.chat.ui.adapter.listener.MyItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longquan on 2015/8/30.
 */
public class ChatHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> data;
    private MyItemClickListener clickListener;
    private MyItemLongClickListener longClickListener;

    public ChatHistoryAdapter(MyItemClickListener clickListener, MyItemLongClickListener longClickListener) {
        data = new ArrayList<>();
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }

    public void addItem(String item) {
        data.add(item);
        notifyDataSetChanged();
    }

    public void clearItem() {
        data.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_chat_history, null);
        TextItemHolder tholder = new TextItemHolder(view,clickListener,longClickListener);
        return tholder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        String str = data.get(position);
        TextItemHolder tHolder = (TextItemHolder) holder;
        tHolder.textView.setText(str);
    }


    public static class TextItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private TextView textView;
        private MyItemClickListener clickListener;
        private MyItemLongClickListener longClickListener;

        public TextItemHolder(View itemView, MyItemClickListener clickListener, MyItemLongClickListener longClickListener) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.txt_user_name);
            this.clickListener = clickListener;
            this.longClickListener = longClickListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view,getPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            longClickListener.onItemLongClick(view,getPosition());
            return false;
        }
    }
}
