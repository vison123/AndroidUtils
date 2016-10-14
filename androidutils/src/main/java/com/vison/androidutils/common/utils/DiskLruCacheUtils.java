package com.vison.androidutils.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.jakewharton.disklrucache.DiskLruCache;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by vison.
 * on 16/8/9.
 */
public class DiskLruCacheUtils {
    private static DiskLruCache mDiskLruCache = null;

    //每当appVersionCode改变，缓存路径下存储的所有数据都会被清除掉
    public static DiskLruCache getDiskLruCache(Context context) {
        int appVersionCode = AppInfoUtils.getAppVersionCode(context);
        try {
            File cacheDir = getDiskCacheDir(context, "bitmap");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            if (mDiskLruCache == null){
                mDiskLruCache = DiskLruCache.open(cacheDir, appVersionCode, 1, 100 * 1024 * 1024);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mDiskLruCache;
    }

    //DiskLruCache的缓存地址
    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            if (context.getExternalCacheDir() != null) {
                cachePath = context.getExternalCacheDir().getPath();
            }
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }



    //下载图片,根据url的md5作为key缓存图片
    public static void cacheFileFromUrl(String imageUrl, Context context) {
        String key = Md5Utils.getMd5String(imageUrl);
        OutputStream outputStream = null;
        DiskLruCache.Editor editor = null;
        try {
            editor = getDiskLruCache(context).edit(key);
            if (editor != null) {
                outputStream = editor.newOutputStream(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        final OutputStream finalOutputStream = outputStream;
        final DiskLruCache.Editor finalEditor = editor;
        OkHttpUtils.get()
                .url(imageUrl)
                .build()
                .execute(new com.zhy.http.okhttp.callback.Callback() {
                    @Override
                    public Object parseNetworkResponse(okhttp3.Response response) throws Exception {
                        InputStream inputStream = response.body().byteStream();
                        BufferedInputStream in = new BufferedInputStream(inputStream, 8 * 1024);
                        BufferedOutputStream out = null;
                        if (finalOutputStream != null) {
                            out = new BufferedOutputStream(finalOutputStream, 8 * 1024);
                        }
                        int b;
                        while ((b = in.read()) != -1) {
                            out.write(b);
                        }
                        if (out != null) {
                            out.close();
                        }
                        if (in != null) {
                            in.close();
                        }
                        return "";
                    }

                    @Override
                    public void onError(okhttp3.Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(Object o) {
                        if (finalEditor != null) {
                            try {
                                finalEditor.commit();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    //将本地图片压缩到缓存目录,根据url的md5作为key缓存图片
    public static void cacheFileFromBitmap(String imageUrl, Context context, InputStream bitmapInputStream) {
        String key = Md5Utils.getMd5String(imageUrl);
        OutputStream outputStream = null;
        DiskLruCache.Editor editor = null;
        try {
            editor = getDiskLruCache(context).edit(key);
            if (editor != null) {
                outputStream = editor.newOutputStream(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        OutputStream finalOutputStream = outputStream;
        DiskLruCache.Editor finalEditor = editor;
        InputStream inputStream = bitmapInputStream;
        BufferedInputStream in = new BufferedInputStream(inputStream, 8 * 1024);
        BufferedOutputStream out = null;
            if (finalOutputStream != null) {
                out = new BufferedOutputStream(finalOutputStream, 8 * 1024);
                        }
                try {
                    int b;
                    while ((b = in.read()) != -1) {
                        out.write(b);
                    }
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                }catch (IOException e){

                }

        if (finalEditor != null) {
            try {
                finalEditor.commit();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

                    }


    //根据url作为key查询缓存的bitmap
    public static Bitmap getDiskLruCacheBitMap(String imageUrl, Context context) {
        String key = Md5Utils.getMd5String(imageUrl);
        try {
            DiskLruCache.Snapshot snapShot = getDiskLruCache(context).get(key);
            if (snapShot != null) {
                InputStream is = snapShot.getInputStream(0);
                byte[] data=readStream(is);
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                //并将Bitmap添加到LruCache中
                LruCacheUtils.addBitmapLruCache(key,bitmap);
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static byte[] readStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1){
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }
}
