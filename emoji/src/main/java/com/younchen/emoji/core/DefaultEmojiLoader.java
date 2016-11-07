package com.younchen.emoji.core;

import android.content.Context;
import android.text.TextUtils;

import com.younchen.emoji.bean.EmojiCategory;
import com.younchen.emoji.bean.EmojiIcon;
import com.younchen.emoji.utils.FileUtil;

import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by younchen on 2016/6/7.
 */
public class DefaultEmojiLoader implements IEmojiLoder {

    private static DefaultEmojiLoader instance;
    private EmojiFileFilter emojiFileFilter;
    private Context context;

    private File emojiRootDir;

    private final String DEFULT_EMOJI_FILE_NAME = "defaultEmoji.zip";

    private ArrayList<EmojiCategory> categoryCache = new ArrayList<>();


    public DefaultEmojiLoader(Context context) {
        this.context = context;
        emojiRootDir = FileUtil.getEmojiRootPath(context);
    }


    private HashMap<String, String> pareseEmojiRule(String configureFilePath) {
        if (!TextUtils.isEmpty(configureFilePath)) {
            return EmojiRuleCreator.createRuleMap(configureFilePath);
        }
        return null;
    }

    @Override
    public void getCategory(EmojiLoaderManager.CategoryLoadListener listener) {
        tryCreateEmojiRootDir();
        unzipDefaultEmojiIfNotExsit();
        getEmojiCategories(listener);
    }

    private void getEmojiCategories(EmojiLoaderManager.CategoryLoadListener listener) {
        String[] filenames = emojiRootDir.list();
        if (categoryCache.size() > 0) {
            listener.onLoadSuccess(categoryCache);
        } else {
            for (int i = 0; i < filenames.length; i++) {
                EmojiCategory category = new EmojiCategory();
                String configurePath = emojiRootDir.getAbsolutePath() + File.separator + filenames[i] + File.separator + "configure.txt";
                String path = emojiRootDir.getAbsolutePath() + File.separator + filenames[i];
                String iconUrl = path + File.separator + filenames[i] + ".png";
                category.setIconUrl(iconUrl);
                category.setId(i);
                category.setName(filenames[i]);
                category.setRuleMap(pareseEmojiRule(configurePath));
                category.setPath(path);
                categoryCache.add(category);
            }
            listener.onLoadSuccess(categoryCache);
        }
    }

    private void unzipDefaultEmojiIfNotExsit() {
        if (FileUtil.isEmptyDir(emojiRootDir)) {
            InputStream in = null;
            try {
                in = context.getAssets().open(DEFULT_EMOJI_FILE_NAME);
                FileUtil.unzip(in, emojiRootDir);
            } catch (Exception e) {
                throw new RuntimeException("unzip default emoji file failed");
            }
        }
    }

    private void tryCreateEmojiRootDir() {
        if (!emojiRootDir.exists()) {
            if (!emojiRootDir.mkdir()) {
                throw new RuntimeException("failed to create emoji root path");
            }
        }
    }

    @Override
    public void loadCategoryEmoji(EmojiCategory category, EmojiLoaderManager.EmojiLoadListener listener) {
        checkCategory(category);
        loadEmojis(category, listener);
    }

    private void loadEmojis(final EmojiCategory category, EmojiLoaderManager.EmojiLoadListener listener) {
        File categoryDir = new File(category.getPath());
        HashMap<String, String> ruleMap = category.getRuleMap();
        ArrayList<EmojiIcon> icons = new ArrayList<>();
        String[] list = categoryDir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                if (filename.contains("configure") || filename.contains(category.getName()))
                    return false;
                return true;
            }
        });
        for (String name : list) {
            EmojiIcon icon = new EmojiIcon();
            icon.setUrl(categoryDir + File.separator + name);
            icon.setValue(ruleMap.get(name));
            icons.add(icon);
        }
        listener.onLoadSuccess(icons);
    }

    private void checkCategory(EmojiCategory category) {
        String name = category.getName();
        if (TextUtils.isEmpty(name)) {
            throw new RuntimeException("category name is null");
        }
        String[] dirs = emojiRootDir.list();
        boolean hasCategory = false;
        for (String file : dirs) {
            if (file.equals(name)) {
                hasCategory = true;
                return;
            }
        }
        if (!hasCategory) {
            throw new RuntimeException("not found category");
        }
    }

    private class EmojiFileFilter implements FilenameFilter {

        private String configureFileName = "";

        @Override
        public boolean accept(File dir, String filename) {
            if (filename.endsWith(".txt")) {
                configureFileName = filename;
                return false;
            }
            return true;
        }

        public String getConfigureFileName() {
            return configureFileName;
        }
    }

}
