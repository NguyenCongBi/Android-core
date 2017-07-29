package com.bkdev.translation.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.WindowManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lombok.Builder;
import lombok.Data;

/**
 * Utilities for getting screen size or convert "dp" to "px".
 */
public final class ScreenUtil {
    private static final String TAG = ScreenUtil.class.getSimpleName();

    private ScreenUtil() {
        // no instance
    }

    /**
     * This method is used to get height of screen.
     *
     * @param context context
     * @return return height screen in pixel
     */
    public static int getHeightScreen(Context context) {
        return getScreenSize(context).getHeight();
    }

    /**
     * This method is used to get width of screen.
     *
     * @param context context
     * @return return width of screen in pixel
     */
    public static int getWidthScreen(Context context) {
        return getScreenSize(context).getWidth();
    }

    public static ScreenSize getScreenSize(Context context) {

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();

        // For JellyBean 4.2 (API 17) and onward
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            display.getRealMetrics(displayMetrics);
            return ScreenSize.builder()
                    .width(displayMetrics.widthPixels)
                    .height(displayMetrics.heightPixels)
                    .build();
        }

        Method getRawH;
        Method getRawW;
        try {
            getRawH = Display.class.getMethod("getRawHeight");
            getRawW = Display.class.getMethod("getRawWidth");
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "NoSuchMethodException error: ", e);
            return ScreenSize.builder()
                    .width(0)
                    .height(0)
                    .build();
        }

        try {
            return ScreenSize.builder()
                    .width((int) getRawW.invoke(display))
                    .height((int) getRawH.invoke(display))
                    .build();
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            Log.e(TAG, "error: ", e);
            return ScreenSize.builder()
                    .width(0)
                    .height(0)
                    .build();
        }
    }

    /**
     * @param spValue is value you want to convert for
     * @return return value in pixel
     */
    public static int convertSPtoPX(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return Math.round(spValue * fontScale);
    }

    /**
     * Convert dp to pixel
     *
     * @param dp dp value
     * @return return value in pixel
     */
    public static int convertDpToPixel(Context context, int dp) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return (int) (dp * (metrics.densityDpi / 160f));
    }

    /**
     * Convert pixel to dp
     *
     * @param px pixel value
     * @return return value in dp
     */
    public static int convertPixelToDp(Context context, int px) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return (int) (px / (metrics.densityDpi / 160f));
    }

    /**
     * Class that manage the size of screen.
     */
    @Data
    @Builder
    public static class ScreenSize {
        private int width;
        private int height;
    }

    /**
     * This method used to get navigation bar height
     */
    public static int getNavigationBarHeight(Context context) {
        if (isNavigationBarAvailable()) {
            Resources resources = context.getResources();
            int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId != 0) {
                return resources.getDimensionPixelSize(resourceId);
            }
        }
        return 0;
    }

    /**
     * Check navigation bar for device
     */
    public static boolean isNavigationBarAvailable() {
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        boolean hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME);
        return (!(hasBackKey && hasHomeKey));
    }

}
