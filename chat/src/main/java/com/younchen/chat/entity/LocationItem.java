package com.younchen.chat.entity;

import java.io.Serializable;

/**
 * Created by pc on 2016/3/29.
 */
public class LocationItem implements Serializable {

    private String iconText;
    private String distance;
    private String locName;

    public String getIconText() {
        return iconText;
    }

    public void setIconText(String iconText) {
        this.iconText = iconText;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }
}
