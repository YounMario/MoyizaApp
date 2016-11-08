package com.younchen.chat.widget.gallery.activitys;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.younchen.chat.R;
import com.younchen.chat.model.NotificationModel;
import com.younchen.chat.community.ui.PostArticleActivity;
import com.younchen.chat.widget.gallery.adapter.ImageGalleryAdapter;
import com.younchen.chat.widget.gallery.enums.PaletteColorType;
import com.younchen.chat.widget.gallery.items.ImageItem;
import com.younchen.chat.widget.gallery.util.ImageGalleryUtils;
import com.younchen.chat.widget.gallery.view.GridSpacesItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ImageGalleryActivity extends AppCompatActivity implements ImageGalleryAdapter.OnImageClickListener {

    // region Member Variables
    private ArrayList<ImageItem> mImages;
    private PaletteColorType mPaletteColorType;
    private ContentResolver mContentResolver;
    private RecyclerView mRecyclerView;
    private Button btnOk;
    private ImageGalleryAdapter imageGalleryAdapter;
    private Intent intent;
    private boolean openNew;
    // endregion


    public static void invoke(Context context, boolean openNew) {
        Intent intent = new Intent(context, ImageGalleryActivity.class);
        intent.putExtra("openNew", openNew);
        context.startActivity(intent);

    }

    // region Lifecycle Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallery);
        initData();
        bindViews();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initData() {
        intent = getIntent();
        openNew= intent.getBooleanExtra("openNew", false);
        mContentResolver = getContentResolver();
        mImages = new ArrayList<>();
        getThumbnail(mImages);
    }
    // endregion

    private void getThumbnail(List<ImageItem> imgs) {
        Cursor mCursor = mContentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.ImageColumns.DATA,MediaStore.Images.ImageColumns.ORIENTATION,MediaStore.Images.ImageColumns.WIDTH,MediaStore.Images.ImageColumns.HEIGHT}, "", null,
                MediaStore.MediaColumns.DATE_ADDED + " DESC");
        // Log.e("TAG", mCursor.getCount() + "");
        if (mCursor.moveToFirst()) {
            int _date = mCursor.getColumnIndex(MediaStore.Images.Media.DATA);
            int _orientation=mCursor.getColumnIndex(MediaStore.Images.ImageColumns.ORIENTATION);
            int _width=mCursor.getColumnIndex(MediaStore.Images.ImageColumns.WIDTH);
            int _height=mCursor.getColumnIndex(MediaStore.Images.ImageColumns.HEIGHT);
            do {
                // 获取图片的路径
                String path = mCursor.getString(_date);
                int orientation=mCursor.getInt(_orientation);
                int width=mCursor.getInt(_width);
                int height=mCursor.getInt(_height);
                ImageItem imageItem=new ImageItem();
                imageItem.setUrl(path);
                imageItem.setOrientation(orientation);
                imageItem.setWidth(width);
                imageItem.setHeight(height);
                imgs.add(imageItem);
            } while (mCursor.moveToNext());
        }
        mCursor.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setUpRecyclerView();
    }

    // region ImageGalleryAdapter.OnImageClickListener Methods
    @Override
    public void onImageClick(int position) {
        Intent intent = new Intent(ImageGalleryActivity.this, FullScreenImageGalleryActivity.class);

        //intent.putStringArrayListExtra("images", mImages);
        intent.putExtra("position", position);
        if (mPaletteColorType != null) {
            intent.putExtra("palette_color_type", mPaletteColorType);
        }

        startActivity(intent);
    }
    // endregion

    // region Helper Methods
    private void bindViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        btnOk = (Button) findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<Integer, ImageItem> images = imageGalleryAdapter.getSelectedItem();
                if (openNew) {
                    PostArticleActivity.invoke(ImageGalleryActivity.this, images);
                } else {
                    NotificationModel.getInstance().postNotificationName(NotificationModel.reciveImageResult, images);
                }
                ImageGalleryActivity.this.finish();
            }
        });
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        int numOfColumns;
        if (ImageGalleryUtils.isInLandscapeMode(this)) {
            numOfColumns = 4;
        } else {
            numOfColumns = 3;
        }

        mRecyclerView.setLayoutManager(new GridLayoutManager(ImageGalleryActivity.this, numOfColumns));
        mRecyclerView.addItemDecoration(new GridSpacesItemDecoration(ImageGalleryUtils.dp2px(this, 2), numOfColumns));
        imageGalleryAdapter = new ImageGalleryAdapter(mImages);
        imageGalleryAdapter.setOnImageClickListener(this);
        mRecyclerView.setAdapter(imageGalleryAdapter);
    }
    // endregion
}
