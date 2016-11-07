package com.younchen.chat.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.younchen.chat.R;
import com.younchen.chat.ui.adapter.base.BaseAdapter;
import com.younchen.chat.ui.adapter.base.ViewHolder;
import com.younchen.chat.ui.adapter.listener.ItemClickListener;
import com.younchen.chat.ui.components.DynamicHeightImageView;
import com.younchen.chat.ui.components.button.IconTextButton;
import com.younchen.chat.ui.image.CustromTransformation;
import com.younchen.chat.utils.YounLog;
import com.younchen.chat.widget.gallery.items.ImageItem;

import java.io.File;

/**
 * Created by longquan on 2016/3/25.
 */
public class PickedImageAdapter extends BaseAdapter<ImageItem> {

    private final int MAX_HEIGHT = 720;
    private final int MAX_WIDTH = 720;

    public PickedImageAdapter(Context context) {
        super(context,R.layout.item_picked_image);
    }

    @Override
    public void covert(ViewHolder holder, int type, ImageItem item) {
        final DynamicHeightImageView view = (DynamicHeightImageView) holder.getView(R.id.picked_imgView);
        final IconTextButton iconTextButton = (IconTextButton) holder.getView(R.id.btn_cancle);
        int[] size = dealImageQulity(item);
        view.setHeightRatio(size[1] * 1.0f / size[0]);
        Picasso.with(view.getContext())
                .load(new File(item.getUrl())).resize(size[0], size[1]).transform(new CustromTransformation(item.getUrl())).error(R.drawable.image_error).placeholder(R.drawable.image_loading).
                into(view);
    }


    private int[] dealImageQulity(ImageItem item) {
        int size[] = new int[2];
        int orginWidth = item.getWidth();
        int orginHeight = item.getHeight();
        YounLog.i("longquan", "pickedImage before resize width:" + orginWidth + " height:" + orginHeight);
        if (orginWidth > orginHeight) {
            size[0] = MAX_WIDTH;
            size[1] = (int) (MAX_WIDTH * 1.0 / orginWidth * orginHeight);
        } else {
            size[0] = (int) (MAX_HEIGHT * 1.0 / orginHeight * orginWidth);
            size[1] = MAX_HEIGHT;
        }
        YounLog.i("longquan", "pickedImage after resize width:" + size[0] + " height:" + size[1]);
        return size;
    }

}
