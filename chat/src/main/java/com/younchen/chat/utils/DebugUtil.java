package com.younchen.chat.utils;

import com.younchen.chat.BuildConfigure;

/**
 * Created by younchen on 2016/1/19.
 */
public class DebugUtil {

    private static DebugUtil getInst;

    private DebugUtil(){
    }

    public static DebugUtil getInstance(){
        if(getInst==null){
            synchronized (DebugUtil.class){
                getInst=new DebugUtil();
            }
        }
        return getInst;
    }

    public boolean isDebug(){
        return BuildConfigure.DEBUG;
    }

}
