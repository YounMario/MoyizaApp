package com.younchen.chat.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;

/**
 * Created by Administrator on 2015/10/25.
 */
public class ChatModel {


    public static String TEXT = "RC:TxtMsg";
    public static String IMG = "RC:ImgMsg";
    public static String VOICE = "RC:VcMsg";
    public static String IMG_TEXT = "RC:ImgTextMsg";
    public static String LOCATION = "RC:LBSMsg";

    // 为了让会话提示语在第一个位置显示， 提示语属于message,数据库存取，是时间排序。so..
    public static String oldDate = "1900-01-01 00:00:00";

    private boolean isLogin;
    private static ChatModel chatModel;

    private RongIMClient client;

    public ChatModel(Context context) {
        client = RongIMClient.getInstance();
    }

    public String getLoginuserId() {
        return "";
    }

    public void setLoginuserId(String loginuserId) {

    }

    public static ChatModel getInstance(Context context) {

        if (chatModel == null) {
            chatModel = new ChatModel(context);
        }
        return chatModel;
    }

    /**
     * 是否登录设备
     *
     * @return
     */
    public boolean hasLogin() {
        return isLogin;
    }

    public synchronized void setLogin(boolean login) {
        this.isLogin = login;
    }

    /**
     * @param message
     * @param callBack
     */
    public void sendMessage(Message message,
                            RongIMClient.SendMessageCallback callBack) {
        client.sendMessage(message, "您有一条新消息", "", callBack);
    }

    /**
     * 重新算所有未读消息条数
     */
    public void resetUnReadMessage() {
        // 获取所有未读消息条数
    }

    /**
     * 重新算所有未读消息条数
     */
    public void recountUnReadMessage() {
        // 获取所有未读消息条数
        // client.setMessageReceivedStatus(messageId, receivedStatus, callback);
    }


    public void getMessagesFromConversation(Conversation con, int lastIndex,
                                            int count, RongIMClient.ResultCallback<List<Message>> callBack) {
        if (con != null) {
            client.getHistoryMessages(Conversation.ConversationType.PRIVATE,
                    con.getTargetId(), lastIndex, count, callBack);
        }
    }

    // --------------不包含oldmessageId那个消息
    public io.rong.imlib.model.Message getLastMessage(Conversation con) {
        List<io.rong.imlib.model.Message> list = client.getLatestMessages(
                Conversation.ConversationType.PRIVATE, con.getTargetId(), 1);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public List<io.rong.imlib.model.Message> getLastMessages(Conversation con) {
        List<io.rong.imlib.model.Message> list = client.getLatestMessages(
                Conversation.ConversationType.PRIVATE, con.getTargetId(), 10);
        return list;
    }

    public void setMessageStatus() {
    }

    /**
     * 删除消息
     *
     * @param messageId
     */
    public void deleteMessage(int[] messageId,
                              RongIMClient.ResultCallback<Boolean> callback) {
        client.deleteMessages(messageId, callback);
    }

    /**
     * 清空聊天记录
     *
     * @param targetId
     * @param callback
     */
    public void clearConversationMessages(String targetId,
                                          RongIMClient.ResultCallback<Boolean> callback) {
        client.removeConversation(Conversation.ConversationType.PRIVATE,
                targetId, callback);
    }

    /**
     * 获取所有会话
     *
     * @return
     */
    public List<Conversation> getAllConversations() {
        List<Conversation> list = client.getConversationList();

        if (list != null) {
            return list;
        } else {
            list = new ArrayList<Conversation>();
        }
        return list;
    }


    public Conversation getConversation(String target) {
        return client.getConversation(Conversation.ConversationType.PRIVATE, target);
    }

    /**
     * 未读消息个数
     *
     * @param conversation
     * @return
     */
    public int unreadCount(Conversation conversation) {
        return conversation.getUnreadMessageCount();
    }

    /**
     * @param targetId
     * @param callback
     */
    public void deleteConversation(String targetId,
                                   RongIMClient.ResultCallback<Boolean> callback) {
        client.removeConversation(Conversation.ConversationType.PRIVATE,
                targetId, callback);
    }

    /**
     * @param target
     * @param callback
     */
    public void clearUnread(String target,
                            RongIMClient.ResultCallback<Boolean> callback) {
        client.clearMessagesUnreadStatus(Conversation.ConversationType.PRIVATE,
                target, callback);
    }

    /**
     * 获取所有未读消息条数
     *
     * @param userId 用户id
     * @return
     */
    public int getAllUnreadMessageCount(String userId) {
        return client.getUnreadCount(Conversation.ConversationType.PRIVATE,
                userId);
    }

    /**
     * 删除消息
     */
    public void delMessage(int[] messageIds,
                           RongIMClient.ResultCallback<Boolean> callback) {
        client.deleteMessages(messageIds, callback);
    }


    public Message createSendMessage(String sendUser, String targetUser,
                                     MessageContent content, String type) {
        Message msg = new Message();
        msg.setContent(content);
        msg.setConversationType(Conversation.ConversationType.PRIVATE);
        msg.setMessageDirection(Message.MessageDirection.SEND);
        msg.setObjectName(type);
        msg.setSenderUserId(sendUser);
        msg.setTargetId(targetUser);
        msg.setSentStatus(Message.SentStatus.SENDING);
        return msg;
    }


    /**
     * 同意添加好友请求
     **/
    public void applyRequst() {

    }

    /**
     * 拒绝好友请求
     **/
    public void rejectRequst() {

    }

    public void addFriend() {

    }

    public void deleteFriend() {

    }


    public List<Conversation> loadDebugConversations() {
        ArrayList<Conversation> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Conversation con = new Conversation();
            con.setTargetId("");
            list.add(con);
        }
        return list;
    }

    public List<Message> loadDebugChatMessages() {
        ArrayList<Message> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Message msg = new Message();
            list.add(msg);
        }
        return list;
    }
}
