package com.younchen.chat.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.younchen.chat.R;
import com.younchen.chat.model.TestFileModel;
import com.younchen.chat.ui.PlayeMusicIconView;
import com.younchen.chat.widget.PullUpLayout;

public class SettingFragment extends BaseFragment implements View.OnClickListener {

    private View pressToSpeak;
    private ImageView imgView;
    private AnimationDrawable anim;

    private PlayeMusicIconView myDrawble;

    private Button play, stop, next,btnUnzip;
    private PullUpLayout pullUpLayout;

    private TestFileModel testFileModel;

    private Context context;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initView(View view) {

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
    }

    private void stopAnimation() {
    }

    private void playAnimation() {

    }



}
