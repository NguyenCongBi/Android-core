package com.bkdev.translation.database;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmObject;

public final class RealmAutoIncrement {
    private static RealmAutoIncrement sInstance;

    private Map<Class<? extends RealmObject>, AtomicInteger> mModelMap = new HashMap<>();
    private Class<? extends RealmObject> mObj;

    private RealmAutoIncrement(Class<? extends RealmObject> obj) {
        mObj = obj;
        mModelMap.put(obj, new AtomicInteger(getLastIdFromModel(mObj)));
    }

    public static RealmAutoIncrement getInstance(Class<? extends RealmObject> obj) {
        if (sInstance == null) {
            sInstance = new RealmAutoIncrement(obj);
        }
        return sInstance;
    }

    private int getLastIdFromModel(Class<? extends RealmObject> clazz) {
        String primaryKeyColumnName = "id";
        Number lastId = Realm.getDefaultInstance().where(clazz).max(primaryKeyColumnName);
        return lastId == null ? 0 : lastId.intValue();
    }

    public Integer getNextIdFromModel() {
        if (isValidMethodCall()) {
            AtomicInteger modelId = mModelMap.get(mObj);
            if (modelId == null) {
                return 0;
            }
            return modelId.incrementAndGet();
        }
        return 0;
    }

    private boolean isValidMethodCall() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTraceElements) {

            if (stackTraceElement.getMethodName().equals("newInstance")) {
                return false;
            }
        }
        return true;
    }

}