package com.bkdev.translation.ui.welcome;

import android.Manifest;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bkdev.translation.R;
import com.bkdev.translation.ui.BaseActivity;
import com.bkdev.translation.util.PermissionUtil;

import org.androidannotations.annotations.EActivity;

import java.io.File;

/**
 * Created by congbi.nguyen on 7/29/2017.
 */
@EActivity(R.layout.activity_main)
public class LoginActivity extends BaseActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void init() {
        PermissionUtil.onCheckPermission(this, 3, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/aaaab");
        Log.d(TAG, "onCreate: " + file.isDirectory() + " - " + file.exists());
        if (!file.exists()) {
            file.mkdirs();
        }
        Log.d(TAG, "onCreate: " + file.isDirectory() + " - " + file.exists());
        Log.d(TAG, "onCreate: " + file.getAbsolutePath());

    }
}
