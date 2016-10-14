package com.vison.androidutils.common.utils;

import android.content.Context;
import android.widget.Toast;

/**
 *
 * Created by vison on 2016/10/14.
 */

public class ToastUtil {
    public static void showLongToast(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_LONG).show();
    }

    public static void showShortToast(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }
}
