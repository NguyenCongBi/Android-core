package com.bkdev.translation.ui.welcome;

import android.Manifest;
import android.support.annotation.NonNull;

import com.bkdev.translation.R;
import com.bkdev.translation.ui.BaseActivity;
import com.bkdev.translation.util.PermissionUtil;

import org.androidannotations.annotations.EActivity;

/**
 * Created by congbi.nguyen on 7/29/2017.
 */
@EActivity(R.layout.activity_main)
public class LoginActivity extends BaseActivity {
    @Override
    protected void init() {
        PermissionUtil.onCheckPermission(this, 3, Manifest.permission.READ_CONTACTS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
