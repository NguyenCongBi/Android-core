package com.bkdev.translation.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Keyboard IME Util
 *
 * @author Binc
 */
public final class KeyboardUtil {
    private static final int KEYBOARD_VISIBLE_THRESHOLD_DP = 100;

    private KeyboardUtil() {
    }

    /**
     * Hide keyboard when touch outside
     */
    public static void touchHideKeyboard(final Activity activity, final View view) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText) || !view.isFocusable()) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View arg0, MotionEvent arg1) {
                    hideKeyboard(activity.getBaseContext(), view);
                    view.clearFocus();
                    return false;
                }
            });
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                touchHideKeyboard(activity, innerView);
            }
        }
    }

    public static void hideKeyboard(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    public static void showKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view.requestFocus()) {
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    /**
     * Set keyboard visibility change event listener.
     */
    public static void setEventListener(final Activity activity,
                                        final KeyboardVisibilityEventListener listener) {
        if (activity == null) {
            throw new NullPointerException("Parameter:activity must not be null");
        }
        if (listener == null) {
            throw new NullPointerException("Parameter:listener must not be null");
        }
        final View activityRoot = getActivityRoot(activity);

        final ViewTreeObserver.OnGlobalLayoutListener layoutListener =
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    private final Rect r = new Rect();
                    private final int visibleThreshold = Math.round(
                            ScreenUtil.convertDpToPixel(activity, KEYBOARD_VISIBLE_THRESHOLD_DP));
                    private boolean wasOpened = false;

                    @Override
                    public void onGlobalLayout() {
                        activityRoot.getWindowVisibleDisplayFrame(r);
                        int heightDiff = activityRoot.getRootView().getHeight() - r.height();
                        boolean isOpen = heightDiff > visibleThreshold;
                        if (isOpen == wasOpened) {
                            // keyboard state has not changed
                            return;
                        }
                        wasOpened = isOpen;
                        listener.onVisibilityChanged(isOpen);
                    }
                };
        activityRoot.getViewTreeObserver().addOnGlobalLayoutListener(layoutListener);
        activity.getApplication()
                .registerActivityLifecycleCallbacks(new AutoActivityLifecycleCallback(activity) {
                    @Override
                    protected void onTargetActivityDestroyed() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            activityRoot.getViewTreeObserver()
                                    .removeOnGlobalLayoutListener(layoutListener);
                        } else {
                            activityRoot.getViewTreeObserver()
                                    .removeGlobalOnLayoutListener(layoutListener);
                        }
                    }
                });
    }

    /**
     * Keyboard Visibility Event Listener
     */
    public interface KeyboardVisibilityEventListener {
        void onVisibilityChanged(boolean isOpen);
    }

    private static View getActivityRoot(Activity activity) {
        return ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
    }

    /**
     * Auto Activity Lifecycle Callback
     */
    abstract static class AutoActivityLifecycleCallback implements Application.ActivityLifecycleCallbacks {
        private final Activity mTargetActivity;

        AutoActivityLifecycleCallback(Activity targetActivity) {
            mTargetActivity = targetActivity;
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            if (activity == mTargetActivity) {
                mTargetActivity.getApplication().unregisterActivityLifecycleCallbacks(this);
                onTargetActivityDestroyed();
            }
        }

        protected abstract void onTargetActivityDestroyed();
    }
}
