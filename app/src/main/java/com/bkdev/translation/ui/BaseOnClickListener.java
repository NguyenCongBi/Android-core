package com.bkdev.translation.ui;

import android.view.View;

/**
 * This interface is used to avoid clicking many times.
 *
 * @author Binc
 */
public abstract class BaseOnClickListener implements View.OnClickListener {
    private static final int MIN_CLICK_INTERVAL = 500;

    private static long sLastClickTime;

    public abstract void onSingleClick(View v);

    @Override
    public void onClick(View v) {
        if (isBlockingClick()) {
            return;
        }
        onSingleClick(v);
    }

    public static boolean isBlockingClick() {
        return isBlockingClick(MIN_CLICK_INTERVAL);
    }

    public static boolean isBlockingClick(long minClickInterval) {
        boolean isBlocking;
        long currentTime = System.currentTimeMillis();
        isBlocking = Math.abs(currentTime - sLastClickTime) < minClickInterval;
        if (!isBlocking) {
            sLastClickTime = currentTime;
        }
        return isBlocking;
    }
}
