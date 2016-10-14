package com.vison.androidutils.common.utils;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by vison on 16/7/12.
 */
public class StringUtils {

    //字符串加密
    public static String Encrypt(String strSrc, String encName) {
        MessageDigest md = null;
        String strDes = null;

        byte[] bt = strSrc.getBytes();
        try {
            if (encName == null || encName.equals("")) {
                encName = "HmacSHA256";
            }
            md = MessageDigest.getInstance(encName);
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    public static String ToBase64StringForUrl(byte[] token) {
        try {
            return Base64.encodeToString(token, Base64.URL_SAFE).replace('+', '-').replace('/', '_');
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String toMobileNumber(String mobileNo) {
        StringBuilder stringBuilder = new StringBuilder(mobileNo);
        stringBuilder.insert(3,"-");
        stringBuilder.insert(8,"-");
        return stringBuilder.toString();
    }
}
