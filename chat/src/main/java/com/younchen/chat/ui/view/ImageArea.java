package com.younchen.chat.ui.view;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpManager;
import com.android.volley.toolbox.ImageLoader;
import com.younchen.chat.R;
import com.younchen.chat.utils.DimenUtils;

import java.util.ArrayList;

/**
 * Created by younchen on 2016/1/22.
 */
public class ImageArea {

    private View parent;
    private int imageCount;
    private int imageMargin;
    private ImageView view;
    private int commonMargin;
    private int imageAreaHeight;
    private int parentW;
    private ViewGroup container;
    private ArrayList<ImageView> list;

    private static int twoImageHeight = (DimenUtils.getWindowWidth() - DimenUtils.dp2px(16) * 2 - DimenUtils.dp2px(8)) / 2;
    private static int threeImageHeight = (DimenUtils.getWindowWidth() - DimenUtils.dp2px(16) * 2 - DimenUtils.dp2px(8)) / 3;

    public ImageArea(View parent, int imageCount) {
        this.parent = parent;
        container = (RelativeLayout) parent.findViewById(R.id.img_area);
        this.imageCount = imageCount;
        imageMargin = DimenUtils.dp2px(8);

        DimenUtils.getRealHeight();
        InitView();
    }

    public void InitView() {
        parentW = parent.getMeasuredWidth();
        view = new ImageView(parent.getContext());
        view.setBackgroundColor(Color.GRAY);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageAreaHeight = DimenUtils.dp2px(200);
        list = new ArrayList<>();
        if (imageCount == 1) {
            initOneImageView();
        } else if (imageCount == 2) {
            initTwoImageView();
        } else if (imageCount == 3) {
            initThreeImageView();
        } else {
            initMoreImageView();
        }

    }

    private void initOneImageView() {
        ImageView imageView = new ImageView(parent.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        imageView.setLayoutParams(lp);
        list.add(imageView);
        container.addView(imageView);
    }

    private void initTwoImageView() {

        LinearLayout linearLayout = new LinearLayout(parent.getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setWeightSum(2);

        container.addView(linearLayout);
        for (int i = 0; i < 2; i++) {
            ImageView imageView = new ImageView(parent.getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.weight = 1;
            layoutParams.height = twoImageHeight;
            if (i != 1)
                layoutParams.rightMargin = imageMargin;
            imageView.setLayoutParams(layoutParams);
            linearLayout.addView(imageView);
            list.add(imageView);
        }
    }

    private void initThreeImageView() {
        LinearLayout linearLayout = new LinearLayout(parent.getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setWeightSum(3);

        container.addView(linearLayout);
        for (int i = 0; i < 3; i++) {
            ImageView imageView = new ImageView(parent.getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.weight = 1;
            layoutParams.height = threeImageHeight;
            if (i != 2)
                layoutParams.rightMargin = imageMargin;
            imageView.setLayoutParams(layoutParams);
            linearLayout.addView(imageView);
            list.add(imageView);
        }
    }

    private void initMoreImageView() {
        LinearLayout linearLayout = new LinearLayout(parent.getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        ImageView big = new ImageView(parent.getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.height = twoImageHeight;
        layoutParams.bottomMargin = imageMargin;
        big.setScaleType(ImageView.ScaleType.CENTER_CROP);
        big.setLayoutParams(layoutParams);
        list.add(big);
        linearLayout.addView(big);

        LinearLayout subLinearLayout = new LinearLayout(parent.getContext());
        subLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        subLinearLayout.setWeightSum(3);
        for (int i = 0; i < 3; i++) {
            ImageView imageView = new ImageView(parent.getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            LinearLayout.LayoutParams mlayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            mlayoutParams.weight = 1;
            mlayoutParams.height = threeImageHeight;
            if (i != 2)
                layoutParams.rightMargin = imageMargin;
            imageView.setLayoutParams(mlayoutParams);
            subLinearLayout.addView(imageView);
            list.add(imageView);
        }
        linearLayout.addView(subLinearLayout);
        container.addView(linearLayout);
    }

    public void setImage(String... url) {
        for (int i = 0; i < list.size(); i++) {
            final ImageView view = list.get(i);

            HttpManager.getInstance().getImageLoader().get(url[i], new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    Bitmap bitmap = response.getBitmap();
                    view.setImageBitmap(bitmap);
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
    }

}
