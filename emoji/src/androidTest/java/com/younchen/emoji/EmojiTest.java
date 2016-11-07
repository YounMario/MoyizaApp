package com.younchen.emoji;

import android.content.Context;

import com.younchen.emoji.bean.EmojiCategory;
import com.younchen.emoji.bean.EmojiIcon;
import com.younchen.emoji.core.EmojiConfigure;
import com.younchen.emoji.core.EmojiLoaderManager;
import com.younchen.emoji.core.EmojiRuleCreator;
import com.younchen.emoji.core.IImageLoader;
import com.younchen.emoji.utils.FileUtil;

import junit.framework.Assert;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by 龙泉 on 2016/7/22.
 */
public class EmojiTest extends ApplicationTest {

    BlockingDeque<String> queue = new LinkedBlockingDeque<>();


    @Override
    protected void tearDown() throws Exception {
        File dir = FileUtil.getEmojiRootPath(getContext());
        if (dir.exists()) {
            FileUtil.removeDir(dir);
        }
    }

    public void testHey() {
        queue.add("string");
        Assert.assertEquals(queue.contains("string"), true);
        queue.add("string");
        queue.add("string");
        queue.add("string");
        queue.add("string");
        Assert.assertEquals(queue.size(), 5);
    }

    public void testB() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(2);
        final Context context = getContext();
        final EmojiCategory[] emojiCategory = new EmojiCategory[1];

        EmojiLoaderManager.getInstance().init(new EmojiConfigure() {
            @Override
            public Context getContext() {
                return context;
            }

            @Override
            public IImageLoader getImageLoader() {
                return null;
            }
        });
        EmojiLoaderManager.getInstance().getCategory(new EmojiLoaderManager.CategoryLoadListener() {
            @Override
            public void onLoadSuccess(List<EmojiCategory> categories) {
                Assert.assertNotSame(categories.size(), 0);
                emojiCategory[0] = categories.get(0);
            }

            @Override
            public void onLoadFailed(String msg) {
                Assert.assertNotNull(msg);
            }
        });

        String url = emojiCategory[0].getIconUrl();
        Assert.assertNotNull(url);
        File file = new File(url);
        Assert.assertEquals(true, file.exists());

        EmojiLoaderManager.getInstance().getEmojiIcons(emojiCategory[0], new EmojiLoaderManager.EmojiLoadListener() {
            @Override
            public void onLoadSuccess(List<EmojiIcon> faceIcons) {
                Assert.assertNotSame(faceIcons.size(), 0);
            }

            @Override
            public void onLoadFailed(String msg) {
                Assert.assertNotNull(msg);
            }
        });


    }

    public void testReadConfigure() {
        InputStream in = null;
        File unziped = null;
        String targetDir = getContext().getFilesDir().getAbsolutePath();
        String configureAfterUnzipPath = targetDir + File.separator + "configure.txt";

        try {
            in = getContext().getAssets().open("configure.zip");
            FileUtil.unzip(in, new File(targetDir));
            unziped = new File(configureAfterUnzipPath);
            Assert.assertEquals(true, unziped.exists());
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap<String, String> map = EmojiRuleCreator.createRuleMap(configureAfterUnzipPath);
        Assert.assertNotSame(map.size(), 0);
    }
}
