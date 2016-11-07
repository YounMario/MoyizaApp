package com.younchen.chat.ui.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.younchen.chat.R;
import com.younchen.chat.entity.User;
import com.younchen.chat.ui.adapter.base.BaseAdapter;
import com.younchen.chat.ui.adapter.base.ViewHolder;

/**
 * Created by admin on 2015/11/11.
 */
public class UserAdapter extends BaseAdapter<User> {

    public UserAdapter(Context context) {
        super(context,R.layout.item_user_list);
    }


    @Override
    public void covert(ViewHolder holder, int type, User user) {
        holder.setText(R.id.txt_user_name,user.getNickName() );
        holder.setText(R.id.txt_user_moto,user.getMoto() == null ? "" : user.getMoto());
    }

}
