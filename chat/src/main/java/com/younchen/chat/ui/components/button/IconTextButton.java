package com.younchen.chat.ui.components.button;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import com.younchen.chat.R;
import com.younchen.chat.ui.view.TextureManager;
import com.younchen.chat.utils.DimenUtils;
import com.younchen.utils.DimenUtil;

/**
 * Created by longquan on 2016/3/24.
 */
public class IconTextButton extends TextView {


    private int textColor;
    private String iconText;
    private Rect textBounds = new Rect();


    public IconTextButton(Context context) {
        this(context, null);
    }

    private static final int LEFT = 0;
    private final int offset = DimenUtils.dp2px(2);


    public IconTextButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconTextButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // Pre-allocate objects for layout measuring
        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.IconTextButton);
        textColor = array.getColor(R.styleable.IconTextButton_text_color, -1);
        iconText = array.getString(R.styleable.IconTextButton_icon_text);
        array.recycle();
        setTypeface("fonts/Black.ttf", this);
        if (textColor != -1) {
            setTextColor(textColor);
        }
        Typeface typeface = TextureManager.getInstance().getTypeFace(getContext(), "fonts/icon.ttf");
        if (iconText != null) {
            TextDrawable textDrawable = new TextDrawable(iconText, textColor, DimenUtil.dip2px(getContext(), 16), typeface);
            setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            setCompoundDrawables(textDrawable, null, null, null);
        } else {
            setGravity(Gravity.CENTER);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!changed) return;
        final int width = getWidth() - (getPaddingLeft() + getPaddingRight());
        Rect drawableBounds = new Rect();
        final CharSequence text = getText();
        if (!TextUtils.isEmpty(text)) {
            TextPaint textPaint = getPaint();
            textPaint.getTextBounds(text.toString(), 0, text.length(), textBounds);
        } else {
            textBounds.setEmpty();
        }

        final Drawable[] drawables = getCompoundDrawables();
        if (drawables[LEFT] != null) {
            int textHeight = textBounds.height() + offset;
            int drawPaddingLeft = width / 2 - textHeight;
            int drawTop = textBounds.top - offset;

            drawableBounds.set(drawPaddingLeft, drawTop, drawPaddingLeft + textHeight, drawTop + textHeight);
            setPadding(0, 0, width / 2 - textBounds.width(), 0);
            drawables[LEFT].setBounds(drawableBounds);
        }
    }

    public void setTypeface(String typefaceName, TextView view) {
        final Typeface typeFace = TextureManager.getInstance().getTypeFace(getContext(), typefaceName);
        if (typeFace != null) {
            view.setTypeface(typeFace);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    class TextDrawable extends Drawable {
        private final Paint textPaint;
        private final String text;
        private final int color;
        private final int fontSize;

        private int height;
        private int width;


        private TextDrawable(String txt, int textColor, int textSize, Typeface typeface) {

            // shape properties
            this.color = textColor;

            // text and color
            text = txt;
            fontSize = textSize;
            textPaint = new Paint();
            textPaint.setColor(color);
            textPaint.setAntiAlias(true);
            textPaint.setStyle(Paint.Style.FILL);
            textPaint.setTypeface(typeface);
            textPaint.setTextAlign(Paint.Align.CENTER);


            // drawable paint color
            Paint paint = getPaint();
            paint.setColor(textColor);
        }


        @Override
        public void draw(Canvas canvas) {
            Rect r = getBounds();
            // draw text
            width = r.width();
            height = r.height();
            int fontSize = this.fontSize < 0 ? (Math.min(width, height) / 2) : this.fontSize;
            textPaint.setTextSize(fontSize);
            canvas.drawText(text, r.left + width / 2 - offset, height / 2, textPaint);
//            canvas.drawRect(r,textPaint);
        }


        @Override
        public void setAlpha(int alpha) {
            textPaint.setAlpha(alpha);
        }


        @Override
        public void setColorFilter(ColorFilter cf) {
            textPaint.setColorFilter(cf);
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }

        @Override
        public int getIntrinsicWidth() {
            return width;
        }

        @Override
        public int getIntrinsicHeight() {
            return height;
        }
    }

}
