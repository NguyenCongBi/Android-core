package com.bkdev.translation.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

/**
 * TouchUtil.
 *
 * @author BiNC
 */

public final class TouchUtil {

    private TouchUtil() {
    }

    public static void disableTouchOnScreen(Context context, ProgressBar progressBar, boolean isDisable) {
        if (context == null) {
            return;
        }
        if (isDisable) {
            if (progressBar != null) {
                progressBar.setVisibility(View.VISIBLE);
            }
            ((Activity) context).getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } else {
            if (progressBar != null) {
                progressBar.setVisibility(View.GONE);
            }
            ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }
}
