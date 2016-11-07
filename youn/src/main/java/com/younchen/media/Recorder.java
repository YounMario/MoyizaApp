package com.younchen.media;

import android.annotation.SuppressLint;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.util.UUID;


public class Recorder {

	private static Recorder recoder;

	private MediaRecorder mediaRecorder;
	// 是否录音
	private boolean isRecoreding;
	private int rankDefualt = 9;
	private int recordTime;
	private RecordHandler recordHandler;
	private String currentFileName;
	private AudioPrepareListenter audioPrepareListenter;

	private VoiceRankListener voiRankListnner;

	private long recordStartTime, recoredEndTime;
	private Thread voiceRankThread;
	private String audioFilePath;
	private String outPath;

	private boolean init;

	@SuppressLint("HandlerLeak")
	private Recorder() {
		init = false;
		recordHandler = new RecordHandler();
	}

	public void init(String outPath) {
		this.outPath = outPath;
		init = true;
		// 这两步是为了触发手机安全软件提示用户允许弹框的展现， 否则第一次使用语音时，权限弹框会影响录音弹框导致第一次录音失败。
		startAudio();
		stop();
	}

	public static Recorder getInstance() {

		if (recoder == null) {
			recoder = new Recorder();
		}
		return recoder;
	}

	public void setVoiceRankListener(VoiceRankListener voiceRankListnner) {
		this.voiRankListnner = voiceRankListnner;
	}

	/**
	 * 录音文件的输出文件，只有当开始录音后才能获取，否则返回空值
	 * 
	 * @return
	 */
	public String getOutputFileName() {
		return audioFilePath;
	}


	/**
	 *
	 * @return
	 */
	public String startAudio() {

		if (isRecoreding) {
			stop();
		}
		recordStartTime = System.currentTimeMillis();
		// 创建录音频文件
		isRecoreding = false;
		// 这种创建方式生成的文件名是随机的
		mediaRecorder = new MediaRecorder();
		// 设置录音的来源为麦克风
		/* ②setAudioSource/setVedioSource */
		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置麦克风
		/*
		 * ②设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default THREE_GPP(3gp格式
		 * ，H263视频/ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
		 */
		mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
		// mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
		/* ②设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default 声音的（波形）的采样 */
		mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
		/* ③准备 */

		File file = new File(outPath);
		if (!file.exists()) {
			file.mkdir();
		}
		audioFilePath = outPath + "/" + UUID.randomUUID().toString() + ".arm";
		if (!init)
			mediaRecorder.setOutputFile(audioFilePath);
		else {
			mediaRecorder.setOutputFile(outPath + "/temp.arm");
		}
		try {
			mediaRecorder.prepare();
			mediaRecorder.start();
			updateMicStatus();
			if (audioPrepareListenter != null) {
				audioPrepareListenter.onPrepareSuccess();
			}
			isRecoreding = true;
			updateMicStatus();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			audioFilePath = null;
			isRecoreding = false;
		}
		return audioFilePath;
	}

	/**
	 * 
	 * @return 返回录音的长度，单位秒
	 */
	public int getRecordTime() {
		if (isRecoreding) {
			return (int) (System.currentTimeMillis() - recordStartTime) / 1000;
		} else {
			return 0;
		}
	}

	/**
	 * 取消录音
	 */
	public void cancel() {
		stop();
		if (currentFileName != null) {
			File f = new File(currentFileName);
			if (f.exists()) {
				f.delete();
			}
		}
		isRecoreding = false;
	}

	/**
	 * 停止录音
	 */
	public void stop() {
		// TODO Auto-generated method stub
		try {
			if (null != mediaRecorder) {
				// 停止录音
				mediaRecorder.stop();
				mediaRecorder.release();
				mediaRecorder = null;
			}
			// 停止 音量监听线程
			if (voiceRankThread != null && !voiceRankThread.isInterrupted()) {
				voiceRankThread.interrupt();
				voiceRankThread = null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();

		} finally {
			isRecoreding = false;
			if (init) {
				init = false;
			}
		}
	}

	public boolean isRecording() {
		return isRecoreding;
	}

	/**
	 * 更新话筒状态 分贝是也就是相对响度 分贝的计算公式K=20lg(Vo/Vi) Vo当前振幅值 Vi基准值为600：我是怎么制定基准值的呢？ 当20
	 * * Math.log10(mediaRecorder.getMaxAmplitude() / Vi)==0的时候vi就是我所需要的基准值
	 * 当我不对着麦克风说任何话的时候，测试获得的mediaRecorder.getMaxAmplitude()值即为基准值。 Log.i("mic_",
	 * "麦克风的基准值：" + mediaRecorder.getMaxAmplitude());前提时不对麦克风说任何话
	 */

	/** 更新声音大小监听 **/
	private void updateMicStatus() {
		if (!isRecoreding)
			return;
		voiceRankThread = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (voiceRankThread != null
						&& !voiceRankThread.isInterrupted()) {
					try {
						Thread.sleep(200);
						int rank = rankDefualt
								* mediaRecorder.getMaxAmplitude() / 32768 + 1;
						recordHandler.sendEmptyMessage(rank);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		voiceRankThread.start();
	}

	public AudioPrepareListenter getAudioPrepareListenter() {
		return audioPrepareListenter;
	}

	public void setAudioPrepareListenter(
			AudioPrepareListenter audioPrepareListenter) {
		this.audioPrepareListenter = audioPrepareListenter;
	}

	@SuppressLint("HandlerLeak")
	private class RecordHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if (voiRankListnner != null)
				voiRankListnner.dealVoiceRank(msg.what);

		}
	}

	/**
	 * 麦克风音量监听类
	 * 
	 * @author admin
	 *
	 */
	public interface VoiceRankListener {

		public void dealVoiceRank(int rank);
	}

	public interface AudioPrepareListenter {

		public void onPrepareSuccess();
	}

}
