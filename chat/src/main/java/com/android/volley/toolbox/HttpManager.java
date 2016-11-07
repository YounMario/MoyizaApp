package com.android.volley.toolbox;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

/**
 * Created by longquan on 2015/12/15.
 */
public class HttpManager {


    //请求队列
    private RequestQueue mRequestQueue;
    //
    private static HttpManager httpManager;
    private ImageLoader mImageLoader;
    private static Context mCtx;


    //自定义的时候可以考虑这个，比如缓存路径设置等。
    public void initRequestQueue(Context context) {
        /**  缓存 设置
         // Set up the network to use HttpURLConnection as the HTTP client.
         Network network = new BasicNetwork(new HurlStack());
         Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
         mRequestQueue = new RequestQueue(cache, network);
         // Start the queue
         mRequestQueue.start();
         详细的见  newRequestQueue 的实现
         **/
        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public static void init(Context context) {
        mCtx = context;
    }

    private HttpManager() {
        mRequestQueue = getRequestQueue();
        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static synchronized HttpManager getInstance() {
        if (httpManager == null) {
            httpManager = new HttpManager();
        }
        return httpManager;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    /**
     * 添加请求
     *
     * @param req 请求实体
     * @param tag 请求标签，可传递空，取消请求的时候用
     * @param <T>
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        if (tag != null) {
            req.setTag(tag);
        }
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public void cancel(String tag) {
        getRequestQueue().cancelAll(tag);
    }
}
