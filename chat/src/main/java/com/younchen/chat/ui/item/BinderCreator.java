package com.younchen.chat.ui.item;

import android.util.SparseArray;

/**
 * Created by 龙泉 on 2016/7/27.
 */
public class BinderCreator {

    private static BinderCreator instance;
    private SparseArray<MessageViewBinder> binders;

    private final int TEXT_MESSGE_RECIVE = 0;
    private final int TEXT_MESSGE_SEND = 1;
    private final int IAMGE_MESSGE_RECIVE = 2;
    private final int IAMGE_MESSGE_SEND = 3;
    private final int VOICE_MESSGE_RECIVE = 4;
    private final int VOICE_MESSGE_SEND = 5;
    private final int EMOJI_MESSAGE_RECIVE = 6;
    private final int EMOJI_MESSAGE_SEND = 7;


    public static BinderCreator getIns(){
        if(instance == null){
            synchronized (BinderCreator.class){
                instance = new BinderCreator();
            }
        }
        return instance;
    }

    public BinderCreator(){
        binders = new SparseArray<>();
    }

    public MessageViewBinder getBindCreator(int msgType){
        if(binders.get(msgType)!=null){
            return binders.get(msgType);
        }
        MessageViewBinder binder=null;
        switch (msgType){
            case TEXT_MESSGE_RECIVE:
            case TEXT_MESSGE_SEND:
                binder = new TextMessageBinder();
                break;
            case IAMGE_MESSGE_RECIVE:
            case IAMGE_MESSGE_SEND:
                binder = new ImageViewBinder();
                break;
            case VOICE_MESSGE_RECIVE:
            case VOICE_MESSGE_SEND:
                binder = new VoiceMessageBinder();
                break;
            case EMOJI_MESSAGE_RECIVE:
            case EMOJI_MESSAGE_SEND:
                binder= new EmojiViewBinder();
                break;
        }
        if(binder != null){
            binders.put(msgType,binder);
        }
        return binder;
    }


}
