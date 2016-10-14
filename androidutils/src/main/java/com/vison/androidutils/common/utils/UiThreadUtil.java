package com.vison.androidutils.common.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;

/**
 * Created by geweimin on 16/9/1.
 */
public class UiThreadUtil {
    @Nullable
    private static Handler sMainHandler;

    public static void runOnUiThread(Runnable runnable) {
        synchronized (UiThreadUtil.class) {
            if (sMainHandler == null) {
                sMainHandler = new Handler(Looper.getMainLooper());
            }
        }
        sMainHandler.post(runnable);
    }
}
