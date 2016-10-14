package com.vison.androidutils.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by drew on 2016/7/5.
 */

public class HmacUtils {

    public static String hmacEncrypt(String secret, String message, String encodeType) {
        String result = "";
        try {
            Mac sha256_HMAC = Mac.getInstance(encodeType);
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), encodeType);
            sha256_HMAC.init(secret_key);
            result = getDigestStr(sha256_HMAC.doFinal(message.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String getDigestStr(byte[] origBytes) {
        String tempStr = null;
        StringBuilder stb = new StringBuilder();
        for (int i = 0; i < origBytes.length; i++) {
            tempStr = Integer.toHexString(origBytes[i] & 0xff);
            if (tempStr.length() == 1) {
                stb.append("0");
            }
            stb.append(tempStr);

        }
        return stb.toString();
    }
}
