package com.younchen.chat.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.younchen.chat.App;


/**
 * @author kang
 * @date 2014年11月26日
 * @description
 */
public class ToastUtil {
	private static Toast mToast = null;

	public static void show(int msg) {
		try {
			show(App.getAppContext().getResources().getString(msg));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void show(String msg) {

		try {
			if (TextUtils.isEmpty(msg)) {
				return;
			} else if (App.getAppContext() == null) {
				return;
			}
			if (mToast == null) {
				mToast = Toast.makeText(App.getAppContext(), msg,
						Toast.LENGTH_LONG);
			} else {
				mToast.cancel();
				mToast = Toast.makeText(App.getAppContext(), msg,
						Toast.LENGTH_LONG);
			}
			mToast.setText(msg);
			mToast.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	// *****************************************************************************

	public static void showToast(Context context, String msg, int duration) {
		try {
			if (TextUtils.isEmpty(msg)) {
				return;
			} else if (App.getAppContext() == null) {
				return;
			}
			if (mToast == null) {
				mToast = Toast.makeText(context, msg, duration);
			} else {
				mToast.cancel();
				mToast = Toast.makeText(context, msg, duration);
			}
			mToast.setText(msg);
			mToast.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void show(String msg, int duration) {
		try {
			if (TextUtils.isEmpty(msg)) {
				return;
			} else if (App.getAppContext() == null) {
				return;
			}
			if (mToast == null) {
				mToast = Toast.makeText(App.getAppContext(), msg,
						duration);
			} else {
				mToast.cancel();
				mToast = Toast.makeText(App.getAppContext(), msg,
						duration);
			}
			mToast.setText(msg);
			mToast.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void show(int msg, int duration) {
		try {
			if (mToast == null) {
				mToast = Toast.makeText(App.getAppContext(), msg,
						duration);
			} else {
				mToast.cancel();
				mToast = Toast.makeText(App.getAppContext(), msg,
						duration);
			}
			mToast.setText(msg);
			mToast.show();
		} catch (Exception ex) {

			ex.printStackTrace();
		}

	}
}
