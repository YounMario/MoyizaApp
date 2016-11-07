package com.younchen.chat.ui.item;

import android.content.Context;

import com.younchen.chat.R;
import com.younchen.chat.model.ChatModel;
import com.younchen.chat.ui.adapter.base.ViewHolder;

import io.rong.imlib.model.Message;
import io.rong.imlib.model.Message.MessageDirection;
import io.rong.imlib.model.Message.ReceivedStatus;


/**
 * @author longquan
 * @date 2015年5月18日
 * @description listView 的item 视图
 */
public abstract class MessageViewBinder {
    private final int TEXT_MESSGE_RECIVE = 0;
    private final int TEXT_MESSGE_SEND = 1;
    private final int IAMGE_MESSGE_RECIVE = 2;
    private final int IAMGE_MESSGE_SEND = 3;
    private final int VOICE_MESSGE_RECIVE = 4;
    private final int VOICE_MESSGE_SEND = 5;

    protected Context context;


    public abstract void bindMessageView(ViewHolder holder,Message message);

    public boolean isRead(Message msg) {
        ReceivedStatus status = msg.getReceivedStatus();
        if (status.isRead()) {
            return true;
        }
        return false;
    }

    public static boolean isSend(Message msg) {
        if (msg.getMessageDirection().equals(MessageDirection.SEND)) {
            return true;
        }
        return false;
    }
}
