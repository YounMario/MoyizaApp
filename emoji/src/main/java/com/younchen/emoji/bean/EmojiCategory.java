package com.younchen.emoji.bean;

import java.util.HashMap;

/**
 * Created by 龙泉 on 2016/7/20.
 */
public class EmojiCategory {

    private long id;
    private String name;
    private String iconUrl;
    private String path;
    private HashMap<String, String> ruleMap;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public void setRuleMap(HashMap<String, String> ruleMap) {
        this.ruleMap = ruleMap;
    }

    public HashMap<String, String> getRuleMap() {
        return this.ruleMap;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
