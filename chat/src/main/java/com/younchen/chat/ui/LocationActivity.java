package com.younchen.chat.ui;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.younchen.chat.R;
import com.younchen.chat.entity.LocationItem;
import com.younchen.chat.model.NotificationModel;
import com.younchen.chat.ui.adapter.LocationAdapter;
import com.younchen.chat.ui.adapter.listener.ItemClickListener;
import com.younchen.chat.ui.components.MyRecyclerView;
import com.younchen.chat.utils.IconUtils;

public class LocationActivity extends SwipeBackActivity {

    private MyRecyclerView recyclerView;
    private LocationAdapter adapter;

    private boolean openNew;

    public static void invoke(Context context, boolean openNew) {
        Intent intent = new Intent(context, LocationActivity.class);
        intent.putExtra("openNew", openNew);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        initData();
        initView();
        initEvent();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initView() {
        recyclerView = (MyRecyclerView) findViewById(R.id.rv);

        adapter = new LocationAdapter(this);

        recyclerView.setAdapter(adapter);
        for (int i = 0; i < 10; i++) {
            adapter.add(new LocationItem());
        }
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        openNew = intent.getBooleanExtra("openNew", false);

        adapter.setOpenNew(openNew);
    }
}
