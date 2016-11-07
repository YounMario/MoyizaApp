package com.younchen.chat.ui.adapter;

import android.content.Context;

import com.younchen.chat.R;
import com.younchen.chat.model.ChatModel;
import com.younchen.chat.ui.adapter.base.MulitTypeAdapter;
import com.younchen.chat.ui.adapter.base.MulitTypeAdapterSupport;
import com.younchen.chat.ui.adapter.base.ViewHolder;
import com.younchen.chat.ui.item.BinderCreator;
import com.younchen.chat.ui.item.MessageViewBinder;

import io.rong.imlib.model.Message;

/**
 * Created by Administrator on 2015/11/14.
 */
public class ChatAdapter extends MulitTypeAdapter<Message>{

    private final int TEXT_MESSGE_RECIVE = 0;
    private final int TEXT_MESSGE_SEND = 1;
    private final int IAMGE_MESSGE_RECIVE = 2;
    private final int IAMGE_MESSGE_SEND = 3;
    private final int VOICE_MESSGE_RECIVE = 4;
    private final int VOICE_MESSGE_SEND = 5;

    public ChatAdapter(Context context) {
        super(context, null);
        init();
    }

    private void init() {
        MulitTypeAdapterSupport support = new MulitTypeAdapterSupport() {
            @Override
            public int getType(int position) {
                Message message = data.get(position);

                if (message.getObjectName().equals(ChatModel.TEXT)) {
                    if (!isSend(message)) {
                        return TEXT_MESSGE_RECIVE;
                    } else {
                        return TEXT_MESSGE_SEND;
                    }
                }
                if (message.getObjectName().equals(ChatModel.IMG)) {
                    if (!isSend(message)) {
                        return IAMGE_MESSGE_RECIVE;
                    } else {
                        return IAMGE_MESSGE_SEND;
                    }
                }
                if (message.getObjectName().equals(ChatModel.VOICE)) {
                    if (!isSend(message)) {
                        return VOICE_MESSGE_RECIVE;
                    } else {
                        return VOICE_MESSGE_SEND;
                    }
                }
                throw new RuntimeException("type not found");
            }

            @Override
            public int layoutId(int type) {
                switch (type) {
                    case TEXT_MESSGE_RECIVE:
                        return R.layout.item_received_text;
                    case TEXT_MESSGE_SEND:
                        return R.layout.item_sent_text;
                    case VOICE_MESSGE_SEND:
                        return R.layout.item_sent_voice;
                    case VOICE_MESSGE_RECIVE:
                        return R.layout.item_received_voice;
                    case IAMGE_MESSGE_RECIVE:
                        return R.layout.item_received_img;
                    case IAMGE_MESSGE_SEND:
                        return R.layout.item_sent_img;
                    default:
                        throw new RuntimeException("not found type");
                }
            }

            @Override
            public int getHeaderItemPosition() {
                return 0;
            }

            @Override
            public boolean headItemUseDataSource() {
                return false;
            }

            @Override
            public boolean hasHeader() {
                return false;
            }
        };
        setMulitTypeAdapterSupport(support);
    }


    @Override
    public void covert(ViewHolder holder,int type, Message item) {
        MessageViewBinder viewBinder = BinderCreator.getIns().getBindCreator(type);
        viewBinder.bindMessageView(holder,item);
    }

    public boolean isSend(Message msg) {
        if (msg.getMessageDirection().equals(Message.MessageDirection.RECEIVE)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void coverHeadItem(ViewHolder holder, int position) {

    }
}
