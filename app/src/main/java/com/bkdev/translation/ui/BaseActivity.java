package com.bkdev.translation.ui;

import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * Base Activity.
 *
 * @author Binc
 */
@EActivity
public abstract class BaseActivity extends AppCompatActivity {

    @AfterViews
    protected abstract void init();

}
