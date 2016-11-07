package com.younchen.chat.ui.components;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by Administrator on 2015/9/9.
 */
public class MyRecyclerView extends RecyclerView {

    private Context context;
    private boolean isLoading;
    private boolean isLoadableTop;
    private boolean isLoadableBottom;
    private LinearLayoutManager mLayoutManager;

    public MyRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public MyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.setHasFixedSize(true);
        this.context = context;
        mLayoutManager = new LinearLayoutManager(context);
        this.setLayoutManager(mLayoutManager);
        isLoading = false;
    }

    public void setLoadTopEnable(boolean enable) {
        isLoadableTop = enable;
    }

    public void setLoadableBottomEnable(boolean enable) {
        isLoadableBottom = enable;
    }

    public void setOnLoadMore(final LoadMoreListener listener) {


        this.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = (mLayoutManager).findLastVisibleItemPosition();
                int firstVisibleItem = (mLayoutManager).findFirstVisibleItemPosition();
                int totalItemCount = getAdapter().getItemCount();
                // dy>0 表示向下滑动
                if (lastVisibleItem == totalItemCount - 1 && dy > 0) {
                    if (isLoading) {
                        Log.d("test", "ignore manually update!");
                    } else {
                        if (isLoadableBottom) {
                            setUnableToRefreshBottom();
                            setUnableToRefreshTop();
                            isLoading = true;
                            listener.onLoadMore();
                            isLoading = false;
                            setAbleToRefreshTop();
                            setAbleToRefreshButtom();
                        }

                    }
                } else if (firstVisibleItem == 0 && dy < 0) {
                    if (isLoading) {

                    } else {
                        if (isLoadableTop) {
                            setUnableToRefreshBottom();
                            setUnableToRefreshTop();
                            isLoading = true;
                            listener.onLoadTopMore();
                            isLoading = false;
                            setAbleToRefreshTop();
                            setAbleToRefreshButtom();
                        }

                    }
                }
            }
        });
    }

    private void setUnableToRefreshBottom() {
        isLoadableBottom = false;
    }

    private void setAbleToRefreshButtom() {
        isLoadableBottom = true;
    }

    private void setUnableToRefreshTop() {
        isLoadableTop = false;
    }

    private void setAbleToRefreshTop() {
        isLoadableTop = true;
    }

    public interface LoadMoreListener {
        public void onLoadMore();

        public void onLoadTopMore();
    }
}
