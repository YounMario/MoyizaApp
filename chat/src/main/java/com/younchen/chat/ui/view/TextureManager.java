package com.younchen.chat.ui.view;

import android.content.Context;
import android.graphics.Typeface;

import java.util.LinkedHashMap;

/**
 * Created by pc on 2016/3/25.
 */
public class TextureManager {

    public static TextureManager instance;
    private LinkedHashMap<String, Typeface> cache;

    public TextureManager() {
        cache = new LinkedHashMap<>();
    }

    public static TextureManager getInstance() {
        if (instance == null) {
            synchronized (TextureManager.class) {
                instance = new TextureManager();
            }
        }
        return instance;
    }

    public Typeface getTypeFace(Context context, String typeface) {
        if (cache.containsKey(typeface))
            return cache.get(typeface);
        else {
            final Typeface typeFace = Typeface.createFromAsset(context.getAssets(), typeface);
            if (typeFace == null) return null;
            cache.put(typeface, typeFace);
            return typeFace;
        }
    }
}
