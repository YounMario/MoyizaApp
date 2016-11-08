package com.younchen.chat;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Created by 龙泉 on 2016/11/8.
 */
@RunWith(AndroidJUnit4.class)
public class DbTest {

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        System.out.println("appName:" + appContext.getPackageName());
    }
}
