package com.younchen.chat.ui.adapter;

import android.content.Context;

import com.younchen.chat.R;
import com.younchen.chat.model.ChatModel;
import com.younchen.chat.ui.adapter.base.BaseAdapter;
import com.younchen.chat.ui.adapter.base.ViewHolder;
import com.younchen.chat.utils.BuildUtils;
import com.younchen.utils.DateUtil;

import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.ImageMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

/**
 * Created by admin on 2015/11/12.
 */
public class ConversationAdapter extends BaseAdapter<Conversation> {

    private ChatModel chatModel;

    public ConversationAdapter(Context context) {
        super(context, R.layout.item_chat_history);
        chatModel = ChatModel.getInstance(context);
    }


    @Override
    public void covert(ViewHolder holder, int type, Conversation item) {

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Conversation con = data.get(position);
        Long messageTime;
        if (BuildUtils.isDebug) {
            holder.setText(R.id.txt_content, "文本消息");
            holder.setText(R.id.txt_date, "2014-12-5");
        } else {
            Message lastMessage = chatModel.getLastMessage(con);
            String content = getMessageDigest(lastMessage);
            holder.setText(R.id.txt_content, content);

            if (isSend(lastMessage)) {
                messageTime = lastMessage.getSentTime();
            } else {
                messageTime = lastMessage.getReceivedTime();
            }
            holder.setText(R.id.txt_date, DateUtil.parseTime(messageTime));
        }
    }

    private boolean isSend(Message msg) {
        return msg.getMessageDirection().equals(Message.MessageDirection.SEND);
    }

    private String getMessageDigest(Message lastMessage) {
        String digest = "";
        if (lastMessage.getContent() instanceof TextMessage) {
            TextMessage msg = (TextMessage) lastMessage.getContent();
            digest = msg.getContent();
        } else if (lastMessage.getContent() instanceof ImageMessage) {
            // message.getUrl().substring(message.getUrl().lastIndexOf("/"));
            digest = "图片消息";
        } else if (lastMessage.getContent() instanceof VoiceMessage) {
            digest = "语音消息";
        }
        return digest;
    }

}
