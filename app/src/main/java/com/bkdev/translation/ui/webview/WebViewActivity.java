package com.bkdev.translation.ui.webview;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.bkdev.translation.R;
import com.bkdev.translation.ui.BaseActivity;
import com.bkdev.translation.widget.CustomWebView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

/**
 * Code: ID0007-01
 * Screen: WebView
 *
 * @author Binc
 */
@EActivity(R.layout.activity_web_view)
public class WebViewActivity extends BaseActivity {
    @ViewById(R.id.webContent)
    CustomWebView mWebContent;
    @ViewById(R.id.progressBar)
    ProgressBar mProgressBar;
    @Extra
    String mWebUrl;

    @Override
    protected void init() {

        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressBar.setVisibility(View.GONE);

            }
        };
        mWebContent.setWebViewClient(webViewClient);
        mWebContent.loadUrl(mWebUrl);
    }

    @Override
    public void onBackPressed() {
        if (mWebContent == null) {
            super.onBackPressed();
        }
        if (mWebContent.canGoBack()) {
            mWebContent.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
