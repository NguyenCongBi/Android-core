package com.bkdev.translation.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.androidannotations.annotations.EView;

/**
 * Custom WebView
 *
 * @author Binc
 */
@EView
public class CustomWebView extends WebView {

    public CustomWebView(Context context) {
        this(context, null);
    }

    public CustomWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setGeolocationEnabled(true);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }
}
