package com.vison.androidutils;

import android.content.Context;
import android.widget.Toast;

/**
 *
 * Created by vison on 2016/10/14.
 */

public class ToastUtil {
    public static void showToast(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_LONG).show();
    }
}
