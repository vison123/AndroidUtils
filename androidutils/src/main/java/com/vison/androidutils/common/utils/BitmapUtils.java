package com.vison.androidutils.common.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.Toast;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by liuqiang on 2016/8/11.
 */
public class BitmapUtils {
    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    // 如果是放大图片，filter决定是否平滑，如果是缩小图片，filter无影响
    private static Bitmap createScaleBitmap(Bitmap src, int dstWidth,
                                            int dstHeight) {
        Bitmap dst = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, false);
        if (src != dst) { // 如果没有缩放，那么不回收
            src.recycle(); // 释放Bitmap的native像素数组
        }
        return dst;
    }

    // 从Resources中加载图片
    public static Bitmap decodeSampledBitmapFromResource(Resources res,
                                                         int resId, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options); // 读取图片长款
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight); // 计算inSampleSize
        options.inJustDecodeBounds = false;
        Bitmap src = BitmapFactory.decodeResource(res, resId, options); // 载入一个稍大的缩略图
        return createScaleBitmap(src, reqWidth, reqHeight); // 进一步得到目标大小的缩略图
    }

    // 从sd卡上加载缩略图
    public static Bitmap decodeSampledBitmapFromFd(String pathName,
                                                   int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap src = BitmapFactory.decodeFile(pathName, options);
        return createScaleBitmap(src, reqWidth, reqHeight);
    }

    //压缩图片并将压缩后的大图转成输入流，传递给DiskLru缓存到真正的缓存目录中
    public static InputStream saveBitmap(InputStream inputStream){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        options.inJustDecodeBounds = false;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            byte[] data=readStream(inputStream);
            Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length,options);
            bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
            bm.recycle();
        }catch (Exception e){

        }
        InputStream inputimagenext = new ByteArrayInputStream(baos.toByteArray());
        return inputimagenext;
    }

    // 获取压缩后大图的SD卡存储路径，用来上传给服务器
    public static String getTempImage(InputStream inputStream,String filename) {
        String tempImage =filename.split("/")[filename.split("/").length-1];
        if(getSDPath()==null) return filename;

        File fdir = new File(getSDPath()+"/DCIM/fas/");
        if(!fdir.exists()) {
            fdir.mkdir();
        }
        File f = new File(getSDPath()+"/DCIM/fas/", tempImage);
        if (f.exists()) {
            f.delete();
        }
        try {
            OutputStream os = new FileOutputStream(f);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            try {
                while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.close();
                inputStream.close();
            }catch (IOException e){

            }

        }catch (FileNotFoundException e){

        }
        return getSDPath()+"/DCIM/fas/"+tempImage;
    }
    public static String getSDPath(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if(sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }

    //获取本地图片的输入流
    public static InputStream getImageInputStram(String path){
        File file = new File(path);
        InputStream in=null;
        if(!file.exists())
        {
            return null;
        }
        //得到文件的输入流
        try {
            in = new FileInputStream(file);
        }catch (FileNotFoundException e){

        }
        return in;
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

    //保存bitmap到本地
    public static void saveMyBitmap(String bitName, Bitmap mBitmap, Context context){
        String tempImage =bitName.split("/")[bitName.split("/").length-1]+".jpg";

        if(getSDPath()==null){
            Toast.makeText(context,"图片保存失败，请检查SD卡是否存在？",Toast.LENGTH_SHORT).show();
            return;
        }

        File fdir = new File(getSDPath()+"/DCIM/fas/");
        if(!fdir.exists()) {
            fdir.mkdir();
        }
        File f = new File(getSDPath()+"/DCIM/fas/", tempImage);
        if (f.exists()) {
            f.delete();
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        Toast.makeText(context,"图片"+tempImage+"已经成功保存在"+getSDPath()+"/DCIM/fas/目录下",Toast.LENGTH_SHORT).show();

        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
