package com.younchen.chat.ui.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpManager;
import com.younchen.chat.R;
import com.younchen.chat.client.ClientApi;
import com.younchen.chat.entity.User;
import com.younchen.chat.entity.result.UsersResult;
import com.younchen.chat.ui.adapter.UserAdapter;
import com.younchen.chat.ui.components.MyRecyclerView;
import com.younchen.chat.utils.ToastUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserListFragment extends BaseFragment {

    private MyRecyclerView listView;
    private SwipeRefreshLayout swipeLayout;
    private UserAdapter adapter;
    private int page;
    private int count;
    private HttpManager httpManager;

    public UserListFragment() {
        // Required empty public constructor
        httpManager = HttpManager.getInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_list, container, false);
    }

    @Override
    protected void initDate() {
        page = 1;
        count = 10;
        adapter = new UserAdapter(getActivity());

        listView.setAdapter(adapter);
        requestDate(page, count);
    }

    @Override
    protected void initEvent() {
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(true);
                page = 1;
                requestDate(page, count);
            }
        });

        listView.setOnLoadMore(new MyRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                swipeLayout.setRefreshing(true);
                page++;
                requestDate(page, count);

            }

            @Override
            public void onLoadTopMore() {

            }
        });
    }

    @Override
    protected void initView(View view) {
        listView = (MyRecyclerView) view.findViewById(R.id.list_view);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initEvent();
        initDate();
    }


    public void requestDate(final int page, int count) {


        ClientApi.getUsers(page, count, new Response.Listener<UsersResult>() {
                    @Override
                    public void onResponse(UsersResult response) {
                        if (response.getCode() == 0) {

                            if (page == 1) {
                                adapter.clear();
                            }
                            UsersResult.UserData data = response.getData();
                            for (User u : data.getUsers()
                                    ) {
                                adapter.add(u);
                            }
                        }
                        swipeLayout.setRefreshing(false);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ToastUtil.show(error.getMessage());
                        swipeLayout.setRefreshing(false);
                    }
                }
        );
    }

}
