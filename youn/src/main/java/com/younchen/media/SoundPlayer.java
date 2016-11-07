package com.younchen.media;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;

/**
 * @author kang
 * @date 2015年4月15日
 * @description
 */
public class SoundPlayer {

	private int SPACE = 100;// 间隔取样时间

	private MediaPlayer mPlayer;

	private static SoundPlayer soundPlayer;

	private boolean isplaying;
	private OnCompletionListener onCompletionListener;
	private String currntPlayFilePath;

	private SoundPlayer() {

	}

	public static SoundPlayer getInstance() {

		if (soundPlayer == null) {
			soundPlayer = new SoundPlayer();
		}
		return soundPlayer;
	}

	/**
	 * 播放录音
	 * 
	 * @param path
	 * @return
	 */
	public synchronized boolean playFile(String path) {
		try {
			if (mPlayer != null) {
				mPlayer.release();
				mPlayer = null;
			}

			mPlayer = new MediaPlayer();
			// 设置要播放的文件
			mPlayer.setDataSource(path);
			if (onCompletionListener != null) {
				mPlayer.setOnCompletionListener(onCompletionListener);
			}
			mPlayer.prepare();
			// 播放

			isplaying = true;
			currntPlayFilePath = path;
			mPlayer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean isPlaying(String path) {
		if (currntPlayFilePath != null && currntPlayFilePath.equals(path)) {
			return isplaying;
		} else {
			return false;
		}
	}

	/** 播放录音更新事件 可以播放动画等 **/
	private void playRecordUpdate() {

		if (mPlayer != null) {

			pHandler.postDelayed(playRecorderStatusTimer, SPACE);
		}

	}

	/**
	 * 结束播放后的回调
	 * 
	 * @param onCompletionListener
	 */
	public void setOnPlayCompleteListenner(
			OnCompletionListener onCompletionListener) {
		this.onCompletionListener = onCompletionListener;
	}

	/**
	 * 停止播放
	 * 
	 * @return
	 */
	public void stopPlay() {
		try {
			isplaying = false;
			if (mPlayer != null) {

				mPlayer.stop();
				mPlayer.release();
				mPlayer = null;
				currntPlayFilePath=null;
			}
		} catch (Exception ex) {

			ex.printStackTrace();
		}

	}

	public synchronized void playRes(AssetManager am, String name) {
		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}

		mPlayer = new MediaPlayer();
		try {
			// 设置要播放的文件
			isplaying = true;
			AssetFileDescriptor fileDescriptor = am.openFd(name);
			mPlayer = new MediaPlayer();
			mPlayer.setDataSource(fileDescriptor.getFileDescriptor(),
					fileDescriptor.getStartOffset(), fileDescriptor.getLength());
			mPlayer.prepare();
			mPlayer.start();

		} catch (Exception ex) {

			ex.printStackTrace();
		}

		// 播放

	}

	private final Handler pHandler = new Handler();
	private Runnable playRecorderStatusTimer = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			playRecordUpdate();
		}
	};
}
