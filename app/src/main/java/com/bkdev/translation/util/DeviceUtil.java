package com.bkdev.translation.util;

import android.content.Context;
import android.provider.Settings;

/**
 * DeviceUtil.
 *
 * @author BiNC
 */

public final class DeviceUtil {
    private DeviceUtil() {
    }

    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }
}
