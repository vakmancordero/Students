package com.kaizensoftware.students.util;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.ProgressBar;

/**
 * Created by Arturo Cordero
 */
public class AlertUtil {

    public static AlertDialog informationDialog(String title, Context context) {

        return new AlertDialog.Builder(context)
                .setTitle(title)
                .setView(new ProgressBar(context))
                .setCancelable(false).create();

    }

}
