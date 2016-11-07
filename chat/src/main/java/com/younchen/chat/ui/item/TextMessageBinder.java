package com.younchen.chat.ui.item;

import android.content.Context;
import android.view.View;
import android.view.View.OnLongClickListener;

import com.younchen.chat.R;
import com.younchen.chat.ui.adapter.base.ViewHolder;

import io.rong.imlib.model.Message;
import io.rong.imlib.model.Message.SentStatus;
import io.rong.message.TextMessage;

/**
 * @author longquan
 * @date 2015年5月18日
 * @description
 */
public class TextMessageBinder extends MessageViewBinder {


    @Override
    public void bindMessageView(ViewHolder holder, Message message) {
        TextMessage testMessage = (TextMessage) message.getContent();
        // 设置内容
        holder.setText(R.id.tv_chatcontent,testMessage.getContent());
        // 如果是发送的消息
        if (isSend(message)) {
            SentStatus status = message.getSentStatus();
            // 已经发送
            if (status.equals(SentStatus.SENT)
                    || status.equals(SentStatus.RECEIVED)) {
                holder.setViewVisible(R.id.progressBar, View.GONE);
                holder.setViewVisible(R.id.msg_status, View.GONE);
            } else if (status.equals(SentStatus.SENDING)) {
                holder.setViewVisible(R.id.msg_status, View.GONE);
                holder.setViewVisible(R.id.progressBar, View.VISIBLE);
            } else if (status.equals(SentStatus.FAILED)) {
                holder.setViewVisible(R.id.progressBar, View.GONE);
                holder.setViewVisible(R.id.msg_status, View.VISIBLE);
            }
        }
        holder.setOnItemLongClickListener(R.id.tv_chatcontent, new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
    }
}
