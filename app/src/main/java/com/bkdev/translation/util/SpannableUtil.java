package com.bkdev.translation.util;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

/**
 * Spannable Util
 *
 * @author Binc
 */
public final class SpannableUtil {
    /**
     * Define interface for this class
     */
    public interface OnClickSpannableListener {
        void onClickSpannable(String tag);
    }

    private SpannableUtil() {
        // no-op
    }

    public static SpannableString setTextSpannable(final Context context, String text, int start, int end, final String tag, final OnClickSpannableListener listener, final int color) {
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                if (listener != null) {
                    listener.onClickSpannable(tag);
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
                ds.setColor(color);
            }
        };

        SpannableString ss = new SpannableString(text);
        ss.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    public static SpannableString getTextGraph(int color, String s) {
        SpannableString spannableString = new SpannableString(s);
        spannableString.setSpan(new AbsoluteSizeSpan(22, true), 11, s.length(), 0);
        spannableString.setSpan(new ForegroundColorSpan(color), 11, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
