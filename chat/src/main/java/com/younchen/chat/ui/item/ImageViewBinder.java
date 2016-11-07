package com.younchen.chat.ui.item;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.younchen.chat.R;
import com.younchen.chat.ui.adapter.base.ViewHolder;
import com.younchen.chat.ui.adapter.listener.ItemClickListener;

import io.rong.imlib.model.Message;
import io.rong.imlib.model.Message.SentStatus;
import io.rong.message.ImageMessage;

/**
 * @author longquan
 * @date 2015年5月18日
 * @description
 */
public class ImageViewBinder extends MessageViewBinder {


    DisplayImageOptions imageMessageOptions;



    private void setImageOption() {
        // TODO Auto-generated method stub
        imageMessageOptions = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(true).cacheOnDisk(true)
                .cacheInMemory(true)
                .build();
    }


    @Override
    public void bindMessageView(ViewHolder holder, Message message) {
        if (!isSend(message)) {
            holder.setViewVisible(R.id.progressBar,View.GONE);
        } else {
            SentStatus status = message.getSentStatus();
            // 已经发送
            if (status.equals(SentStatus.SENT)
                    || status.equals(SentStatus.RECEIVED)) {
                holder.setViewVisible(R.id.progressBar,View.GONE);
                holder.setViewVisible(R.id.msg_status,View.GONE);
            } else if (status.equals(SentStatus.SENDING)) {
                holder.setViewVisible(R.id.msg_status,View.GONE);
                holder.setViewVisible(R.id.progressBar,View.VISIBLE);
            } else if (status.equals(SentStatus.FAILED)) {
                holder.setViewVisible(R.id.progressBar,View.GONE);
                holder.setViewVisible(R.id.msg_status,View.VISIBLE);
            }
        }

        ImageMessage imgMessage = (ImageMessage) message.getContent();
        String url = "file://" + imgMessage.getThumUri().getPath();
        ImageLoader.getInstance().displayImage(url, (ImageView) holder.getView(R.id.iv_sendPicture),
                imageMessageOptions);
    }
}
