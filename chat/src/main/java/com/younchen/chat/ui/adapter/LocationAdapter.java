package com.younchen.chat.ui.adapter;

import android.app.Activity;
import android.view.View;

import com.younchen.chat.R;
import com.younchen.chat.entity.LocationItem;
import com.younchen.chat.model.NotificationModel;
import com.younchen.chat.community.ui.PostArticleActivity;
import com.younchen.chat.ui.adapter.base.BaseAdapter;
import com.younchen.chat.ui.adapter.base.ViewHolder;
import com.younchen.chat.utils.IconUtils;

/**
 * Created by pc on 2016/3/29.
 */
public class LocationAdapter extends BaseAdapter<LocationItem> {


    private boolean openNew;

    public LocationAdapter(Activity context) {
        super(context, R.layout.item_near_locations);
    }

    @Override
    public void covert(ViewHolder holder, int type, LocationItem item) {
//        txtLocation = (MediumTextView) rootView.findViewById(R.id.txt_location);
//        iconText = (IconTextView) rootView.findViewById(R.id.icon_text);
//        txtDistance = (MediumTextView) rootView.findViewById(R.id.txt_distance);
        holder.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationItem item = new LocationItem();
                item.setDistance("200米");
                item.setLocName("北京二外");
                String txt = IconUtils.iconText(R.string.icon_add_location);
                item.setIconText(txt);
                if (openNew) {
                    PostArticleActivity.invoke(context, item);
                    ((Activity) context).finish();
                } else {
                    NotificationModel.getInstance().postNotificationName(NotificationModel.reciveLocationResult, item);
                    ((Activity) context).finish();
                }
            }
        });
    }

    public void setOpenNew(boolean openNew) {
        this.openNew = openNew;
    }
}
