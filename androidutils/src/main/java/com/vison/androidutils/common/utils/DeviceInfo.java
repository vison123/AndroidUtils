package com.vison.androidutils.common.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by vison on 16/7/26.
 */
public class DeviceInfo {

    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

}
