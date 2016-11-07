package com.younchen.chat.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by longquan on 2016/5/17.
 */
public class STextview extends TextView {

    private List<SpanModel> list;
    private String parent;
    private int color;

    public STextview(Context context) {
        this(context, null);
    }

    public STextview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public STextview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        list = new ArrayList<>();
        this.color = Color.RED;
    }

    public void setStyleColor(String color){
        try{
            this.color=  Color.parseColor(color);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    public void setSpecialTextStyle(String parent, String... subStrings) {
        this.parent = parent;
        list.clear();
        if (parent != null && subStrings != null) {
            for (String subString : subStrings
                    ) {
                checkPositionEndCreateSpanModel(parent, subString);
            }
        }
    }

    private void checkPositionEndCreateSpanModel(String parent, String sub) {
        Pattern p = Pattern.compile(sub);
        Matcher m = p.matcher(parent);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            int spanMode = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;
            SpanModel model = new SpanModel(start, end, spanMode);
            list.add(model);
        }
    }


    public void toStyleString() {
        SpannableString spannableString;
        if (!TextUtils.isEmpty(this.parent)) {
            spannableString = new SpannableString(this.parent);
        } else {
            spannableString = new SpannableString(getText());
        }

        for (SpanModel spanModel : list
                ) {
            spannableString.setSpan(new ForegroundColorSpan(color), spanModel.startInparent, spanModel.endInparent, spanModel.spanMode);
        }
        setText(spannableString);
    }

    class SpanModel {
        private int startInparent;
        private int endInparent;
        private String color;
        private int spanMode;

        public SpanModel(int startInparent, int endInparent, String color, int spanMode) {
            this.startInparent = startInparent;
            this.endInparent = endInparent;
            this.color = color;
            this.spanMode = spanMode;
        }

        public SpanModel(int startInparent, int endInparent, int spanMode) {
            this(startInparent, endInparent, null, spanMode);
        }

    }
}
