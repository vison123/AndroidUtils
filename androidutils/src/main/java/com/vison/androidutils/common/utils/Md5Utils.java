package com.vison.androidutils.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by vison.
 * on 16/8/9.
 */
public class Md5Utils {
    //将图片的url转化为md5,作为图片的key
    public static String getMd5String(String key) {
        String md5Key;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            md5Key = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            md5Key = String.valueOf(key.hashCode());
        }
        return md5Key;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xFF & aByte);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
