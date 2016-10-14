package com.vison.androidutils.common.utils;

import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by vison on 16/7/27.
 *
 */
public class EditTextUtils {

    public static void setEditTextClear(final EditText edittext) {
        edittext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Drawable drawable = edittext.getCompoundDrawables()[2];
                if (drawable == null)
                    return false;
                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;
                if (event.getX() > edittext.getWidth() - edittext.getPaddingRight()
                        - drawable.getIntrinsicWidth()) {
                    edittext.setText("");
                }
                return false;
            }
        });
    }
    public static boolean clear(EditText editText,MotionEvent event) {
        Drawable drawable = editText.getCompoundDrawables()[2];
        if (drawable == null)
            return false;
        if (event.getAction() != MotionEvent.ACTION_UP)
            return false;
        if (event.getX() > editText.getWidth() - editText.getPaddingRight()
                - drawable.getIntrinsicWidth()) {
            editText.setText("");
        }
        return false;
    }
}
