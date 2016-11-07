package com.younchen.utils;

import android.util.Log;

/**
 * 控制日志显示
 * 
 * 开发时，可以将LOG_LEVEL设为 0，显示所有级别的信息； 程序发布时，可以将LOG_LEVEL设为 6，不显示任何信息
 * 
 */
public class Logger {

	/**
	 * 冗长信息
	 */
	private static final int VERBOSE = 1;
	/**
	 * 调试信息
	 */
	private static final int DEBUG = 2;
	/**
	 * 普通信息
	 */
	private static final int INFO = 3;
	/**
	 * 警告信息
	 */
	private static final int WARN = 4;
	/**
	 * 错误信息
	 */
	private static final int ERROR = 5;


	/**
	 * 当前需要显示的日志级别
	 */
	private static final int LOG_LEVEL = 1;

	public static void v(String tag, String msg) {
		if (LOG_LEVEL <= VERBOSE) {
			Log.v(tag, msg);
		}
	}

	public static void d(String tag, String msg) {
		if (LOG_LEVEL <= DEBUG) {
			Log.d(tag, msg);
		}
	}

	public static void i(String tag, String msg) {
		if (LOG_LEVEL <= INFO) {
			Log.i(tag, msg);
		}
	}

	public static void w(String tag, String msg) {
		if (LOG_LEVEL <= WARN) {
			Log.w(tag, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (LOG_LEVEL <= ERROR) {
			Log.e(tag, msg);
		}
	}
}
