package com.vison.androidutils.common.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by vison.
 * on 16/8/9.
 */
public class LruCacheUtils {
    private static LruCache<String, Bitmap> mBitMapCache;

    public static LruCache getBitMapCache() {
        if (mBitMapCache == null) {
            mBitMapCache = new LruCache<>(10 * 1024 * 1024);
//            int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
//            Logger.d("maxMemory" + maxMemory);
//            int cacheSize = maxMemory / 8;
//            mBitMapCache = new LruCache<String, Bitmap>(cacheSize) {
//                @Override
//                protected int sizeOf(String key, Bitmap bitmap) {
//                    return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
//                }
//            };
        }
        return mBitMapCache;
    }

    public static void addBitmapLruCache(String key, Bitmap bitmap) {
        String cacheKey = Md5Utils.getMd5String(key);
        LruCache mBitMapCache = getBitMapCache();
        mBitMapCache.put(cacheKey, bitmap);
    }

    public static Bitmap getBitmapLruCache(String key) {
        String cacheKey = Md5Utils.getMd5String(key);
        LruCache mBitMapCache = getBitMapCache();
        return (Bitmap) mBitMapCache.get(cacheKey);
    }
}
