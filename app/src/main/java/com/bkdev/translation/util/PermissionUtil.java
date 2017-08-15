package com.bkdev.translation.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Copyright@ AsianTech.Inc
 * Created by Ly Ho V. on 15/08/2017
 */
public final class PermissionUtil {
    private static final String TAG = PermissionUtil.class.getSimpleName();

    private PermissionUtil() {
        // No-op
    }

    public static void onCheckPermission(@NonNull Activity activity, int requestCode, @NonNull String permissionName) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        if (ContextCompat.checkSelfPermission(activity, permissionName) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{permissionName}, requestCode);
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionName)) {
                Snackbar.make(activity.findViewById(android.R.id.content),
                        "Please Grant Permissions",
                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        v -> ActivityCompat.requestPermissions(activity, new String[]{permissionName}, requestCode)).show();
                Log.d(TAG, "onCheckPermission: ");
                Toast.makeText(activity, "shut show", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{permissionName}, requestCode);
                Toast.makeText(activity, "can't not  show", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
