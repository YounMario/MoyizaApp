package com.younchen.chat.widget.gallery.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.graphics.Palette;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.younchen.chat.R;
import com.younchen.chat.widget.gallery.enums.PaletteColorType;
import com.younchen.chat.widget.gallery.util.ImageGalleryUtils;
import com.younchen.chat.widget.gallery.view.PaletteTransformation;

import java.io.File;
import java.util.List;

/**
 * Created by etiennelawlor on 8/20/15.
 */
public class FullScreenImageGalleryAdapter extends PagerAdapter {

    // region Member Variables
    private final List<String> mImages;
    private final PaletteColorType mPaletteColorType;
    // endregion

    // region Constructors
    public FullScreenImageGalleryAdapter(List<String> images, PaletteColorType paletteColorType) {
        mImages = images;
        mPaletteColorType = paletteColorType;
    }
    // endregion

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) container.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.fullscreen_image, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.iv);
        final LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ll);

        String image = mImages.get(position);

        Context context = imageView.getContext();
        int width = ImageGalleryUtils.getScreenWidth(context);

        if (!TextUtils.isEmpty(image)) {
            Picasso.with(imageView.getContext())
                    .load(new File(image))
                    .resize(width, 0).transform(PaletteTransformation.instance())
                    .into(imageView, new PaletteTransformation.PaletteCallback(imageView) {
                        @Override
                        public void onError() {

                        }

                        @Override
                        public void onSuccess(Palette palette) {
                        }
                    });


        } else {
            imageView.setImageDrawable(null);
        }


        container.addView(view, 0);

        return view;
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    // endregion
}
