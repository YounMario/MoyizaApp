package com.younchen.chat.community.adapter;

import android.content.Context;
import android.view.View;

import com.younchen.chat.R;
import com.younchen.chat.community.ui.CommentsDetailActivity;
import com.younchen.chat.community.ui.PostArticleActivity;
import com.younchen.chat.entity.FriendArticle;
import com.younchen.chat.ui.LocationActivity;
import com.younchen.chat.ui.adapter.base.MulitTypeAdapter;
import com.younchen.chat.ui.adapter.base.MulitTypeAdapterSupport;
import com.younchen.chat.ui.adapter.base.ViewHolder;
import com.younchen.chat.ui.view.ImageArea;
import com.younchen.chat.widget.gallery.activitys.ImageGalleryActivity;

import java.util.Random;

/**
 * Created by longquan on 2016/1/3.
 */
public class FriendCircleAdapter extends MulitTypeAdapter<FriendArticle> {

    private static final int HEAD_ITEM_VIEW_TYPE = 0;
    private static final int ITEM_VIEW_TYPE = 1;

    private Context context;

    public FriendCircleAdapter(Context context) {
        super(context, new MulitTypeAdapterSupport() {
            @Override
            public int getType(int position) {
                if (position == 0) {
                    return HEAD_ITEM_VIEW_TYPE;
                } else {
                    return ITEM_VIEW_TYPE;
                }
            }

            @Override
            public int layoutId(int type) {
                if (type == HEAD_ITEM_VIEW_TYPE) {
                    return R.layout.item_post_article;
                } else {
                    return R.layout.item_friends_circle;
                }
            }

            @Override
            public int getHeaderItemPosition() {
                return 0;
            }

            @Override
            public boolean headItemUseDataSource() {
                return false;
            }

            @Override
            public boolean hasHeader() {
                return true;
            }
        });
        this.context = context;
    }


    @Override
    public void covert(ViewHolder holder, int type, FriendArticle item) {
        View itemView = holder.getItemView();
        Random random = new Random(5);
        int imageCount = random.nextInt() + 1;
        ImageArea one = new ImageArea(itemView, imageCount);
        one.setImage("http://farm5.static.flickr.com/4132/5096797696_6910f34a5b_z.jpg", "http://korea.people.com.cn/mediafile/201401/08/F201401081631301409726378.jpg", "http://pic.to8to.com/attch/day_160218/20160218_d968438a2434b62ba59dH7q5KEzTS6OH.png"
                , "http://www.sinaimg.cn/dy/slidenews/8_img/2013_01/34780_205039_339865.jpg");

        holder.getView(R.id.btn_parise).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.getView(R.id.btn_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentsDetailActivity.invoke(context);
            }
        });
    }


    @Override
    protected void coverHeadItem(ViewHolder holder, int position) {
        holder.getView(R.id.btn_article).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostArticleActivity.invoke(context);
            }
        });

        holder.getView(R.id.img_head).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageGalleryActivity.invoke(context, true);
            }
        });

        holder.getView(R.id.btn_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationActivity.invoke(context, true);
            }
        });
    }

}
