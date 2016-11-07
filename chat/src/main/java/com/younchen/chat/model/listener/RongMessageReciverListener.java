package com.younchen.chat.model.listener;

import android.content.Context;
import android.os.Handler;

import com.younchen.chat.model.NotificationModel;

import io.rong.imlib.RongIMClient.OnReceiveMessageListener;
import io.rong.imlib.model.Message;

/**
 * @author longquan
 * @date 2015年2月28日
 * @description
 */
public class RongMessageReciverListener implements OnReceiveMessageListener {

	private Context context;

	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {

			Message m = (Message) msg.obj;

			NotificationModel.getInstance().postNotificationName(
					NotificationModel.updateMessage, m);
		};
	};

	public RongMessageReciverListener(Context context) {
		this.context = context;
	}

	@Override
	public boolean onReceived(Message rongMsg, int arg1) {
		// TODO Auto-generated method stub
		android.os.Message msg = new android.os.Message();
		handler.getLooper();
		msg.arg1 = arg1;
		msg.obj = rongMsg;
		handler.sendMessage(msg);
		return true;
	}




}
