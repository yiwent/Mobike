package com.yiwen.mobike.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by zsh06
 * Created on 2016/11/5 9:22.
 */

public class DialogUtils {

    public static void showSimpleDialog(Activity context, String title, String msg, String posBtn,
                                        DialogInterface.OnClickListener posListener) {
        AlertDialog builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(posBtn, posListener)
                .setNegativeButton("返回", null)
                .show();
    }
}
