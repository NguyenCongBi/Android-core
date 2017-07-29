package com.bkdev.translation.api.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.util.Map;

/**
 * Header store.
 *
 * @author BiNC
 */
public class HeaderStore {
    private static final String HEADER_STORE_NAME = "HEADER_STORE_NAME";
    private static HeaderStore sSessionStore;
    private static SharedPreferences sSharedPreferences;

    public static synchronized HeaderStore getInstance(Context context) {
        sSharedPreferences = context.getSharedPreferences(HEADER_STORE_NAME, Context.MODE_PRIVATE);
        if (sSessionStore == null) {
            sSessionStore = new HeaderStore();
        }
        return sSessionStore;
    }

    public void addHeader(String key, String value) {
        sSharedPreferences.edit().putString(key, value).apply();
    }

    public void removeHeader(String key) {
        sSharedPreferences.edit().remove(key).apply();
    }

    public void clearHeader() {
        sSharedPreferences.edit().clear().apply();
    }

    public void addAuth(String name, String pass) {
        String userCredentials = String.format("%s:%s", name, pass);
        String basicAuth = String.format("%s%s", "Basic ", Base64.encodeToString(userCredentials.getBytes(), Base64.DEFAULT));
        sSharedPreferences.edit().putString("Authorization", basicAuth).apply();
    }

    public Map getHeader() {
        return sSharedPreferences.getAll();
    }
}
