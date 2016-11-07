package com.younchen.chat.widget.gallery.items;

import java.io.Serializable;

/**
 * Created by pc on 2016/3/29.
 */
public class ImageItem implements Serializable {

    private String url;
    private String thumbUrl;
    private int position;
    private int width;
    private int height;
    private int orientation;
    private boolean isChecked;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public void copy(ImageItem item) {
        item.setThumbUrl(getThumbUrl());
        item.setPosition(getPosition());
        item.setHeight(getHeight());
        item.setOrientation(getOrientation());
        item.setWidth(getWidth());
        item.setUrl(getUrl());
        item.setIsChecked(isChecked());
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }
}
