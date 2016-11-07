package com.younchen.chat.ui.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.younchen.chat.R;
import com.younchen.chat.entity.FriendArticle;
import com.younchen.chat.ui.adapter.FriendCircleAdapter;
import com.younchen.chat.ui.adapter.listener.ItemClickListener;
import com.younchen.chat.ui.components.MyRecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsCircleFragment extends BaseFragment {

    private MyRecyclerView listView;
    private SwipeRefreshLayout swipeLayout;

    public FriendsCircleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friend_circle, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initDate();
        initEvent();
    }

    @Override
    protected void initDate() {


        FriendCircleAdapter adapter = new FriendCircleAdapter(getContext());

        for (int i = 0; i < 10; i++) {
            adapter.add(new FriendArticle());
        }
        listView.setAdapter(adapter);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initView(View view) {
        listView = (MyRecyclerView) view.findViewById(R.id.list_view);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
    }

    @Override
    public boolean onToucnEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onToucnEvent(event);
    }
}
