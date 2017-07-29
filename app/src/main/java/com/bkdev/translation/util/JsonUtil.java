package com.bkdev.translation.util;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * JsonUtil.
 *
 * @author BiNC
 */
public final class JsonUtil {
    private JsonUtil() {
    }

    private static String loadJSONFromAsset(Context context, String fileNameJson) {
        String json;
        try {
            InputStream is = context.getAssets().open(fileNameJson);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            return null;
        }
        return json;

    }

    public static <E> E[] getListFrom(Class<E[]> clazz, Context context, String fileNameJson) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(loadJSONFromAsset(context, fileNameJson), clazz);
    }

    public static <E> E[] getListNoticeMemoFromJson(Class<E[]> clazz, String json) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, clazz);
    }

    public static <E> E getListReminderFromJson(Class<E> clazz, String json) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, clazz);
    }

    public static <E> String convertObjToJson(E obj) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(obj);
    }
}
