package com.younchen.chat.ui.item;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.younchen.chat.R;
import com.younchen.chat.ui.adapter.base.ViewHolder;
import com.younchen.chat.ui.adapter.listener.ItemClickListener;
import com.younchen.media.SoundPlayer;
import com.younchen.utils.DimenUtil;

import io.rong.imlib.model.Message;
import io.rong.imlib.model.Message.SentStatus;
import io.rong.message.VoiceMessage;

/**
 * @author longquan
 * @date 2015年5月18日
 * @description
 */
public class VoiceMessageBinder extends MessageViewBinder {

    public ImageView iv;
    public ProgressBar pb;
    public ImageView staus_iv;
    // 语音消息长度文本
    public TextView time_lenth;
    public TextView size;
    public ImageView iv_read_status;
    // 聊天气泡容器
    public TextView timestamp;


    public static AnimationDrawable currentAnimation = new AnimationDrawable();



    // 重绘长度
    private void resizeView(View view, int width, int height, boolean isLeft) {
        MarginLayoutParams margin = new MarginLayoutParams(
                view.getLayoutParams());

        margin.setMargins(margin.leftMargin, margin.topMargin,
                margin.rightMargin, margin.bottomMargin);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                margin);

        layoutParams.width = width;

        if (isLeft) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.leftMargin = DimenUtil.px2sp(context, 20);
        } else {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.rightMargin = DimenUtil.px2sp(context, 20);
        }
        view.setLayoutParams(layoutParams);
    }


    @Override
    public void bindMessageView(ViewHolder holder, Message message) {

//        boolean isSend = isSend(message);
//        VoiceMessage voiceMessage = (VoiceMessage) message.getContent();
//
//        time_lenth.setText(voiceMessage.getDuration() + "\"");
//
//        int w = View.MeasureSpec.makeMeasureSpec(0,
//                View.MeasureSpec.UNSPECIFIED);
//        int h = View.MeasureSpec.makeMeasureSpec(0,
//                View.MeasureSpec.UNSPECIFIED);
//        iv.measure(w, h);
//
//        int width = iv.getMeasuredWidth();
//        int height = iv.getMeasuredHeight();
//        // 获取时间 根据时间的长度定制消息框长度
//        int add_width = width * voiceMessage.getDuration() / 10;
//        int new_w = width;
//        if (add_width < 4 * width) {
//            new_w = width + width * voiceMessage.getDuration() / 10;
//        }
//
//        boolean isLeft = true;
//        if (isSend) {
//            isLeft = false;
//        }
//        resizeView(iv, new_w, height, isLeft);
//
//        String path = voiceMessage.getUri().getPath();
//
//        if (!SoundPlayer.getInstance().isPlaying(path)) {
//            if (isSend) {
//                // iv.setImageResource(R.drawable.chatto_voice_playing_f3);
//            } else {
//                // iv.setImageResource(R.drawable.chatfrom_voice_playing_f3);
//            }
//        }
//
//        // 如果是接受到的消息
//        if (!isSend) {
//            if (message.getReceivedStatus().isRead())
//                iv_read_status.setVisibility(View.GONE);
//            else {
//                iv_read_status.setVisibility(View.VISIBLE);
//            }
//        } else {
//
//            SentStatus status = message.getSentStatus();
//            // 已经发送
//            if (status.equals(SentStatus.SENT)
//                    || status.equals(SentStatus.RECEIVED)) {
//                holder.getView()
//                pb.setVisibility(View.GONE);
//                staus_iv.setVisibility(View.GONE);
//            } else if (status.equals(SentStatus.SENDING)) {
//                staus_iv.setVisibility(View.GONE);
//                pb.setVisibility(View.VISIBLE);
//            } else if (status.equals(SentStatus.FAILED)) {
//                pb.setVisibility(View.GONE);
//                staus_iv.setVisibility(View.VISIBLE);
//            }
//        }
    }

    class VoicePlayClickListner implements OnClickListener {

        private AnimationDrawable animationDrawable;
        private Message message;
        private ImageView imgView;
        private VoiceMessage voiceMessage;

        public VoicePlayClickListner(Message message, ImageView imageView) {
            // TODO Auto-generated constructor stub
            this.message = message;
            this.imgView = imageView;
            voiceMessage = (VoiceMessage) message.getContent();
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub

            if (isRead(message)) {
            }

            if (animationDrawable == null) {
                initAnimation();
            }
            String path = voiceMessage.getUri().getPath();

            if (!SoundPlayer.getInstance().isPlaying(path)) {
                // 先停掉当前的动画
                stopVoiceAnimation(currentAnimation);
                showVoiceAnimation(animationDrawable);
                SoundPlayer.getInstance().playFile(path);
                // adapter.notifyDataSetChanged();
            } else {
                stopVoiceAnimation(animationDrawable);
                SoundPlayer.getInstance().stopPlay();
                // adapter.notifyDataSetChanged();
            }
        }

        private void initAnimation() {
            if (!isSend(message)) {
                // imgView.setImageResource(voice_from_icon);
            } else {
                // imgView.setImageResource(R.anim.voice_to_icon);
            }
            animationDrawable = (AnimationDrawable) imgView.getDrawable();
            SoundPlayer.getInstance().setOnPlayCompleteListenner(
                    new OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            // TODO Auto-generated method stub
                            SoundPlayer.getInstance().stopPlay();
                            stopVoiceAnimation(animationDrawable);
                        }
                    });
        }

        private void showVoiceAnimation(AnimationDrawable voiceAnimation) {
            if (voiceAnimation != null && voiceAnimation.isRunning()) {
                stopVoiceAnimation(voiceAnimation);
            }
            currentAnimation = voiceAnimation;
            voiceAnimation.start();
        }

        private void stopVoiceAnimation(AnimationDrawable voiceAnimation) {
            if (voiceAnimation != null) {
                voiceAnimation.stop();
            }
            if (!isSend(message)) {
                //  imgView.setImageResource(R.anim.voice_from_icon);
            } else {
                //imgView.setImageResource(R.anim.voice_to_icon);
            }
        }
    }
}
