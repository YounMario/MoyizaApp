package com.younchen.chat.ui.controller;

import android.view.View;
import android.widget.TextView;

import com.younchen.chat.R;
import com.younchen.chat.ui.components.button.IconTextButton;
import com.younchen.chat.ui.view.IconTextView;

/**
 * Created by pc on 2016/3/23.
 */
public class PariseAndCommentsController extends BaseController {


    private TextView btnParise;
    private IconTextButton btnComments;
    private ClickListener clickListener;


    public PariseAndCommentsController(View root, ClickListener clickListener) {
        this(root);
        this.clickListener = clickListener;
    }

    public PariseAndCommentsController(View root) {
        super(root);
    }

    @Override
    protected void initView() {
        btnParise = (TextView) root.findViewById(R.id.btn_parise);
        btnParise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onPariseClicked();
            }
        });
        btnComments = (IconTextButton) root.findViewById(R.id.btn_comment);
        btnComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onCommentsClicked();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    public static interface ClickListener {

        public void onPariseClicked();

        public void onCommentsClicked();
    }

}
