package com.younchen.chat.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.younchen.chat.R;


/**
 * @author longquan
 * @date 2015年9月25日
 * @description
 */
public class MicDialog {

	private ImageView micImage, micCancle;
	private TextView recordingHint;
	private Drawable[] micImages;

	private Dialog dialog;

	private Context mContext;


	public MicDialog(Context context) {
		// TODO Auto-generated constructor stub
		mContext = context;
		micImages = new Drawable[] {

		};

	}

	public void showDialog() {
		if (dialog == null) {
			dialog = new Dialog(mContext, R.style.dialog_mic);
			LayoutInflater inflater = LayoutInflater.from(mContext);
			View view = inflater.inflate(R.layout.dialog_mic, null);
			dialog.setContentView(view);
			micImage = (ImageView) dialog.findViewById(R.id.mic_image);
			recordingHint = (TextView) dialog.findViewById(R.id.recording_hint);
			micCancle = (ImageView) dialog.findViewById(R.id.mic_cancel);
			
		}
		dialog.show();
	}

	public void dismiss() {
		if (dialog != null && dialog.isShowing())
			dialog.dismiss();
	}

	public void setCancel() {
		recordingHint.setText("松开手指取消发送");
		recordingHint.setTextColor(Color.parseColor("#F08A00"));
		micImage.setVisibility(View.GONE);
		micCancle.setVisibility(View.VISIBLE);
	}

	public void setIsRecording() {
		recordingHint.setText("手指上滑取消发送");
		recordingHint.setTextColor(Color.parseColor("#FFFFFF"));
		micImage.setVisibility(View.VISIBLE);
		micCancle.setVisibility(View.GONE);
	}
	
	public void setVoiceRank(int rank){
		if (micImage != null)
			micImage.setImageDrawable(micImages[rank]);
	}

}
