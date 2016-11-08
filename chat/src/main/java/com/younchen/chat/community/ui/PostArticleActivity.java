package com.younchen.chat.community.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.younchen.chat.R;
import com.younchen.chat.entity.LocationItem;
import com.younchen.chat.model.NotificationCenterDelegate;
import com.younchen.chat.model.NotificationModel;
import com.younchen.chat.community.adapter.PickedImageAdapter;
import com.younchen.chat.ui.BaseActivity;
import com.younchen.chat.ui.LocationActivity;
import com.younchen.chat.ui.components.MyRecyclerView;
import com.younchen.chat.ui.view.IconTextView;
import com.younchen.chat.ui.view.MediumTextView;
import com.younchen.chat.utils.YounLog;
import com.younchen.chat.widget.gallery.activitys.ImageGalleryActivity;
import com.younchen.chat.widget.gallery.items.ImageItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class PostArticleActivity extends BaseActivity implements NotificationCenterDelegate {

    private MyRecyclerView recyclerView;
    private PickedImageAdapter pickedImageAdapter;
    private ArrayList<ImageItem> items;
    private final static Integer FROM_POST_ARTICLE = 0;
    private final static Integer FROM_IMAGE_GALLERY = 1;
    private final static Integer FROM_LOCATION_CHOOSE = 2;

    private IconTextView btnImg, btnLocation, txtIcon;
    private MediumTextView txtLocation, txtDistance;
    private LocationItem locationItem;

    private int from;

    public static void invoke(Context context) {
        Intent intent = new Intent(context, PostArticleActivity.class);
        intent.putExtra("from", FROM_POST_ARTICLE);
        context.startActivity(intent);
    }

    public static void invoke(Context context, HashMap<Integer, ImageItem> images) {
        Intent intent = new Intent(context, PostArticleActivity.class);
        intent.putExtra("from", FROM_IMAGE_GALLERY);
        intent.putExtra("images", getImageArray(images));
        context.startActivity(intent);
    }

    private static ArrayList<ImageItem> getImageArray(HashMap<Integer, ImageItem> map) {
        ArrayList<ImageItem> mImages = new ArrayList<>();
        if (map != null) {
            Set<Integer> keys = map.keySet();
            for (Integer key :
                    keys) {
                mImages.add(map.get(key));
            }
        }
        return mImages;
    }

    public static void invoke(Context context, LocationItem item) {
        Intent intent = new Intent(context, PostArticleActivity.class);
        intent.putExtra("from", FROM_LOCATION_CHOOSE);
        intent.putExtra("location", item);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_article);
        initData();
        initView();
        initEvent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post_article, menu);
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
        getRootView();
        pickedImageAdapter = new PickedImageAdapter(this);
        btnImg = (IconTextView) findViewById(R.id.btn_img);
        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageGalleryActivity.invoke(PostArticleActivity.this, false);
            }
        });

        btnLocation = (IconTextView) findViewById(R.id.btn_location);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationActivity.invoke(PostArticleActivity.this, false);
            }
        });

        recyclerView = (MyRecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setAdapter(pickedImageAdapter);

        txtDistance = (MediumTextView) findViewById(R.id.txt_distance);
        txtLocation = (MediumTextView) findViewById(R.id.txt_location);
        txtIcon = (IconTextView) findViewById(R.id.txt_icon);

        if (locationItem != null) {
            updateLocationInfo(locationItem);
        }
        if (items != null && items.size() > 0) {
            for (ImageItem item :
                    items) {
                pickedImageAdapter.add(item);
            }
        }
    }


    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {
        NotificationModel.getInstance().addObserver(this, NotificationModel.reciveImageResult);
        NotificationModel.getInstance().addObserver(this, NotificationModel.reciveLocationResult);
        from = getIntent().getIntExtra("from", -1);

        if (from == FROM_IMAGE_GALLERY) {
            items = (ArrayList<ImageItem>) getIntent().getSerializableExtra("images");
        } else if (from == FROM_LOCATION_CHOOSE) {
            items = new ArrayList<>();
            locationItem = (LocationItem) getIntent().getSerializableExtra("location");
        } else if (from == FROM_POST_ARTICLE) {
            items = new ArrayList<>();
        }
    }

    @Override
    protected void onDestroy() {
        NotificationModel.getInstance().removeObserver(this, NotificationModel.reciveImageResult);
        NotificationModel.getInstance().removeObserver(this, NotificationModel.reciveLocationResult);
        super.onDestroy();
    }

    @Override
    public void didReceivedNotification(int id, Object... args) {
        YounLog.i("longquan", " recived command");
        if (id == NotificationModel.reciveImageResult) {
            YounLog.i("longquan", " recived before add image");
            ArrayList<ImageItem> images = getImageArray((HashMap<Integer, ImageItem>) args[0]);
            if (pickedImageAdapter != null) {
                YounLog.i("longquan", " recived add image");
                for (ImageItem item :
                        images) {
                    pickedImageAdapter.add(item);
                }
            }
        } else if (id == NotificationModel.reciveLocationResult) {
            LocationItem item = (LocationItem) args[0];
            updateLocationInfo(item);
        }
    }

    private void updateLocationInfo(LocationItem item) {
        txtLocation.setText(item.getLocName());
        txtIcon.setText(item.getIconText());
        txtDistance.setText(item.getDistance());
    }
}
