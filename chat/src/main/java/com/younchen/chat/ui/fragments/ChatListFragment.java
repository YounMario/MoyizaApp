package com.younchen.chat.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.younchen.chat.R;
import com.younchen.chat.model.ChatModel;
import com.younchen.chat.model.NotificationCenterDelegate;
import com.younchen.chat.model.NotificationModel;
import com.younchen.chat.ui.ChatActivity;
import com.younchen.chat.ui.adapter.ConversationAdapter;
import com.younchen.chat.ui.adapter.listener.ItemClickListener;
import com.younchen.chat.ui.components.MyRecyclerView;
import com.younchen.chat.utils.BuildUtils;

import java.util.List;

import io.rong.imlib.model.Conversation;

public class ChatListFragment extends BaseFragment implements NotificationCenterDelegate {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MyRecyclerView listView;
    private ConversationAdapter adapter;
    private SwipeRefreshLayout swipeLayout;
    private ChatModel chatModel;


    private boolean isLoadMore;
    private boolean isRefreshing;


    public ChatListFragment() {
        // Required empty public constructor
        isLoadMore = false;
        isRefreshing = false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_list, container, false);
    }


    @Override
    protected void initDate() {
        chatModel = ChatModel.getInstance(parentActivity);
        adapter = new ConversationAdapter(getActivity());
        listView.setAdapter(adapter);
        loadConversation();
        NotificationModel.getInstance().addObserver(this, NotificationModel.updateMessage);
    }

    @Override
    protected void initEvent() {
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(true);
                onRefreshing();
            }
        });

        listView.setOnLoadMore(new MyRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {

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


    public void loadConversation() {
        if (BuildUtils.isDebug) {
            List<Conversation> conversations = chatModel.loadDebugConversations();
            for (Conversation con :
                    conversations) {
                adapter.add(con);
            }
        } else {
            List<Conversation> conversations = chatModel.getAllConversations();
            for (Conversation con :
                    conversations) {
                adapter.add(con);
            }
        }

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void didReceivedNotification(int id, Object... args) {

        if (id == NotificationModel.updateMessage) {
            adapter.clear();
            loadConversation();
        }
    }

    public void onRefreshing() {
        swipeLayout.setRefreshing(false);
    }
}
