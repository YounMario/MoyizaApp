package com.younchen.chat.ui.components.button;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.younchen.chat.R;
import com.younchen.chat.ui.dialog.MicDialog;
import com.younchen.chat.utils.ToastUtil;
import com.younchen.media.Recorder;
import com.younchen.utils.LogUtils;


/**
 * @author longquan
 * @date 2015年9月24日
 * @description
 */
public class RecordButton extends Button implements Recorder.AudioPrepareListenter {

    /**
     * 录音开始
     **/

    private final int PREPARE_SUCCESS = 0;
    private final int IS_RECODING = 1;

    private final int DISTANCE = 50;

    private Recorder recoder;
    private String path;
    private MicDialog micDialog;
    private String PRESS_TO_SPEAK;
    private String RELESE_TO_END;

    private RecordFinshListener recordFinshListener;

    @SuppressWarnings("unused")
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case PREPARE_SUCCESS:
                    // micDialog.showDialog();
                    LogUtils.e("============录音开始==============");
                    if (recoder.isRecording()) {
                        micDialog.showDialog();
                        ToastUtil.show("开始录音");
                    }
                    break;
                default:
                    break;
            }
        }
    };

    public RecordButton(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public RecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        recoder = Recorder.getInstance();
        micDialog =new MicDialog(context);
        PRESS_TO_SPEAK = getResources().getString(R.string.press_to_speak);
        RELESE_TO_END = getResources().getString(R.string.relase_to_finish);
        reset();
        recoder.setVoiceRankListener(new Recorder.VoiceRankListener() {

            @Override
            public void dealVoiceRank(int rank) {
                // TODO Auto-generated method stub
                System.out.println(rank);
                micDialog.setVoiceRank(rank);
            }
        });
        recoder.setAudioPrepareListenter(this);
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // TODO Auto-generated method stub
                path = recoder.startAudio();
                return false;
            }
        });
    }

    public RecordButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub

        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                setText(RELESE_TO_END);
                break;
            case MotionEvent.ACTION_UP:
                setText(PRESS_TO_SPEAK);
                if (!recoder.isRecording())
                    break;
                if (inCancelArea(x, y)) {

                } else {
                    if (recoder.getRecordTime() < 1) {
                        recoder.cancel();
                        ToastUtil.show("录音时间太短");
                    } else {
                        if (recordFinshListener != null)
                            recordFinshListener.onRecordFinshed(
                                    recoder.getOutputFileName(),
                                    recoder.getRecordTime());
                        recoder.stop();
                    }
                }
                micDialog.dismiss();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!recoder.isRecording())
                    break;
                if (inCancelArea(x, y)) {
                    micDialog.setCancel();
                } else {
                    micDialog.setIsRecording();
                }
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private void reset() {
        setText(PRESS_TO_SPEAK);
    }

    private boolean inCancelArea(float x, float y) {
        if (x > getWidth() || x < 0)
            return true;
        if (y < -DISTANCE || y > getHeight() + DISTANCE)
            return true;
        return false;
    }

    @Override
    public void onPrepareSuccess() {
        // TODO Auto-generated method stub
        handler.sendEmptyMessage(PREPARE_SUCCESS);
    }

    public RecordFinshListener getRecordFinshListener() {
        return recordFinshListener;
    }

    public void setRecordFinshListener(RecordFinshListener recordFinshListener) {
        this.recordFinshListener = recordFinshListener;
    }

    public static interface RecordFinshListener {

        public void onRecordFinshed(String recordPath, int recordTime);
    }
}
