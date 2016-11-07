package com.younchen.chat.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.younchen.chat.R;
import com.younchen.chat.model.ChatModel;
import com.younchen.chat.model.NotificationModel;
import com.younchen.chat.ui.adapter.ChatAdapter;
import com.younchen.chat.ui.adapter.listener.ItemClickListener;
import com.younchen.chat.ui.components.MyRecyclerView;
import com.younchen.chat.utils.BuildUtils;
import com.younchen.chat.utils.ToastUtil;
import com.younchen.emoji.bean.EmojiIcon;
import com.younchen.emoji.ui.EmojiPagerView;
import com.younchen.emoji.ui.FaceiconPage;
import com.younchen.media.Recorder;
import com.younchen.media.SoundPlayer;

import java.io.File;
import java.util.List;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.TextMessage;
/**
 * @author longquan
 * @date 2015年8月10日
 * @description
 */
public class ChatActivity extends SwipeBackActivity {

    public static final int REQUEST_CODE_CAMERA = 16;
    public static final int REQUEST_CODE_LOCAL = 17;

    private MyRecyclerView listView;
    private EditText editSendMessage;
    private InputMethodManager inputMethodManager;
    // 给谁发送消息


    private SwipeRefreshLayout swipeLayout;
    private PowerManager.WakeLock wakeLock;
    // 会话id
    private Button btn_more;

    // 页面头部显示的 聊天用户名
    private TextView name;
    private int lastIndex;
    private int tempIndex;

    //
    private boolean isTop;


    private boolean clicked_biaoqing;
    //表情
    EmojiPagerView emojiPagerView;

    private ChatAdapter adapter;
    private String targetId;
    private Conversation con;
    private ChatModel chatModel;

    public static void invoke(BaseActivity activity, String targetId) {
        Bundle bundle = new Bundle();
        bundle.putString("targetId", targetId);
        activity.goToActivity(ChatActivity.class, bundle);
    }


    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initView();
        initEvent();
        initData();
    }

    @Override
    public void initView() {
        listView = (MyRecyclerView) findViewById(R.id.list_view);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        editSendMessage = (EditText) findViewById(R.id.edit_message_content);

        name = (TextView) findViewById(R.id.txt_user_name);

        editSendMessage = (EditText) findViewById(R.id.edit_message_content);
        btn_more = (Button) findViewById(R.id.btn_more);

        emojiPagerView = (EmojiPagerView) findViewById(R.id.emoji_pager_view);
        emojiPagerView.setOnEmojiCliclickListener(new EmojiPagerView.EmojiClickListener() {
            @Override
            public void onEmojiClicked(EmojiIcon emoji) {
                Log.i("younchen",emoji.getUrl());
            }
        });

        inputMethodManager = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        wakeLock = ((PowerManager) this.getSystemService(Context.POWER_SERVICE))
                .newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "demo");


        adapter = new ChatAdapter(this);

        // 显示消息
        listView.setAdapter(adapter);
        listView.setLoadableBottomEnable(true);
        listView.setLoadTopEnable(true);

    }

    /**
     * 加载更多
     */
    private void loadMore() {
        swipeLayout.setRefreshing(true);
        if (BuildUtils.isDebug) {
            List<Message> msgList = chatModel.loadDebugChatMessages();
            for (Message msg :
                    msgList) {
                adapter.add(msg);
            }
        } else {
            chatModel.getMessagesFromConversation(con, lastIndex, 10, new RongIMClient.ResultCallback<List<Message>>() {
                @Override
                public void onSuccess(List<Message> messages) {

                    if (messages == null) {
                        //达到顶部了
                        isTop = true;
                        swipeLayout.setRefreshing(false);
                        return;
                    }

                    for (int i = messages.size() - 1; i >= 0; i--) {
                        adapter.addFirst(messages.get(i));
                    }
                    tempIndex = lastIndex;
                    //标记最顶上的消息
                    lastIndex = adapter.getItem(0).getMessageId();
                    swipeLayout.setRefreshing(false);
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    swipeLayout.setRefreshing(false);
                }
            });
        }
    }


    private void refresh() {
        if (BuildUtils.isDebug) {

        } else {
            swipeLayout.setRefreshing(true);
            adapter.clear();
            lastIndex = tempIndex = -1;
            isTop = false;
            chatModel.getMessagesFromConversation(con, lastIndex, 10, new RongIMClient.ResultCallback<List<Message>>() {
                @Override
                public void onSuccess(List<Message> messages) {

                    if (messages == null) {
                        swipeLayout.setRefreshing(false);
                        return;
                    }
                    for (int i = messages.size() - 1; i >= 0; i--) {
                        adapter.addFirst(messages.get(i));
                    }
                    tempIndex = lastIndex;
                    //标记最顶上的消息
                    lastIndex = adapter.getItem(0).getMessageId();
                    swipeLayout.setRefreshing(false);
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    swipeLayout.setRefreshing(false);
                }
            });
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void initEvent() {
        editSendMessage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // 监听文字框
        editSendMessage.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (!TextUtils.isEmpty(s)) {
                    btn_more.setVisibility(View.GONE);
                } else {
                    btn_more.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /**
         * 下拉刷新
         */
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                swipeLayout.setRefreshing(false);
            }
        });

        listView.setOnLoadMore(new MyRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                ToastUtil.show("loadMoreButtom");
            }

            @Override
            public void onLoadTopMore() {
                if (isTop) return;
                loadMore();
            }
        });

        editSendMessage.requestFocus();
    }

    @Override
    public void initData() {
        if (getParams() != null) {
            targetId = (String) getParams().get("targetId");
            name.setText(targetId);
        }
        isTop = false;
        chatModel = ChatModel.getInstance(this);
        con = chatModel.getConversation(targetId);
        clicked_biaoqing = false;
        lastIndex = -1;
        tempIndex = -1;

        if (con != null) {
            loadMore();
        }
        NotificationModel.getInstance().addObserver(this, NotificationModel.updateMessage);
    }


    /**
     * onActivityResult
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        try {

            if (requestCode == REQUEST_CODE_LOCAL) { // 发送本地图片
                if (data != null) {
                    Uri selectedImage = data.getData();

                    if (selectedImage != null) {
                        sendPicByUri(selectedImage);
                    }
                }
            } else if (requestCode == REQUEST_CODE_CAMERA) { // 发送照片

//                if (cameraFile != null && cameraFile.exists())
//                    sendPicture(cameraFile.getAbsolutePath());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void sendMessage(MessageContent message) {

        String userId = getMyInfo().getId().toString();
        final Message sendMsg = chatModel.createSendMessage(userId, targetId, message, chatModel.TEXT);
        adapter.add(sendMsg);
        editSendMessage.setText("");

        chatModel.sendMessage(sendMsg, new RongIMClient.SendMessageCallback() {
            @Override
            public void onError(Integer integer, RongIMClient.ErrorCode errorCode) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onSuccess(Integer integer) {
                sendMsg.setSentStatus(Message.SentStatus.SENT);
                adapter.notifyDataSetChanged();
            }
        });
    }


//        else if (id == R.id.layout_take_picture) {
//            selectPicFromCamera();// 点击照相图标
//        } else if (id == R.id.layout_picture) {
//            selectPicFromLocal(); // 点击图片图标
//        }




    /**
     * 跳转到设置页
     **/
    private void goToSettingPage() {
        // TODO Auto-generated method stub

        // ChatSettingFragment fragment = new ChatSettingFragment();
        // Bundle b = new Bundle();
        // b.putLong("conId", conId);
        // b.putSerializable("user", chatTarget);
        // fragment.setArguments(b);
        // mainActivity.appFM.addFragment(fragment, true);
    }

    /**
     * 照相获取图片
     */
//    public void selectPicFromCamera() {
//        if (!FileUtil.getSDCardMount()) {
//            ToastUtil.show("SD卡不存在，不能拍照");
//            return;
//        }
//        String tempImgDir = FileUtil.MSG_IMAGE_PATH + "/"
//                + new Date().getTime() + ".jpg";
//        cameraFile = new File(tempImgDir);
//        this.startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                        .putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile)),
//                REQUEST_CODE_CAMERA);
//    }

    /**
     * 从图库获取图片
     */
    public void selectPicFromLocal() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

        } else {
            intent = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        this.startActivityForResult(intent, REQUEST_CODE_LOCAL);
    }

    private void sendText(String content) {
        if (content == null || content.trim().equals("")) {
            ToastUtil.show("请输入内容");
            return;
        }
        MessageContent message = new TextMessage(content);
        sendMessage(message);
    }

    public void onClickSend(View view) {

        String s = editSendMessage.getText().toString();
        sendText(s);
    }

    public void onClickEmoji(View view) {
        emojiPagerView.setVisibility( emojiPagerView.getVisibility() == View.GONE ?View.VISIBLE:View.GONE);
    }

    public void onClickMore(View view) {

    }

    /**
     * 发送图片
     *
     * @param filePath
     */
    private void sendPicture(final String filePath) {

//        dialog = new PositiveNagativeAlertDialog(this, "确定", "取消", "确定要发送么?",
//                new onBtnClickListener() {
//
//                    @Override
//                    public void onPositiveBtnClicked() {
//                        // TODO Auto-generated method stub
//                        // 发送图片消息
//                        String newUrl = dealImageQulity(filePath);
//                        Message message = new Message();
//                        message.setType(Message.IMG);
//                        message.setUrl(newUrl);
//                        message.setLength(-1);
//                        sendMessage(message, false);
//                        dialog.showOrCloseDialog();
//                    }
//
//                    @Override
//                    public void onNagativeBtnClicked() {
//                        // TODO Auto-generated method stub
//                        dialog.showOrCloseDialog();
//                    }
//                });
//        dialog.showOrCloseDialog();
    }

    /**
     * 根据图库图片uri发送图片
     *
     * @param selectedImage
     */
    private void sendPicByUri(Uri selectedImage) {

        Cursor cursor = this.getContentResolver().query(selectedImage, null,
                null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex("_data");
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            cursor = null;

            if (picturePath == null || picturePath.equals("null")) {
                ToastUtil.show("找不到图片");
                return;
            }
            sendPicture(picturePath);
        } else {
            File file = new File(selectedImage.getPath());
            if (!file.exists()) {
                ToastUtil.show("找不到图片");
                return;
            }
            sendPicture(file.getAbsolutePath());
        }

    }

    // 降低图片质量
    private String dealImageQulity(String path) {
        try {
//            File file = new File(path);
//            if (file.exists()) {
//
//                // 获取图片的角度
//                int degree = ImageUtils.readPictureDegree(path);
//                // 图片压缩处理
//                Bitmap image = ImageUtils.revitionImageSize(path);
//                path = FileUtil.MSG_IMAGE_PATH + "/" + new Date().getTime()
//                        + path.substring(path.lastIndexOf('.'));
//                // 将相册图片备份到新的目录
//                Bitmap roateImage = ImageUtils.bitmapRotate(image, degree);
//                // 保存图片
//                FileUtil.saveBitmap(roateImage, path);
//                // 释放 图片
//                roateImage.recycle();
//                image.recycle();
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return path;
    }


    @Override
    public void onDestroy() {
        Recorder.getInstance().stop();
        SoundPlayer.getInstance().stopPlay();
        // 录制监听绑定了当前类的图片数组，导致推出当前activity时无法释放内存
        Recorder.getInstance().setVoiceRankListener(null);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (wakeLock != null && wakeLock.isHeld())
            wakeLock.release();
    }

    /**
     * 隐藏软键盘
     */
    private void hideKeyboard() {
        if (this.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (this.getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
    }

}

