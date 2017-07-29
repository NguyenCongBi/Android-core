package com.bkdev.translation.database;

import android.util.Log;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;

public class RealmUtil {
    private static RealmUtil sInstance;
    private static Realm sRealm;

    protected static RealmUtil getInstance() {
        if (sInstance == null) {
            sInstance = new RealmUtil();
            sRealm = Realm.getDefaultInstance();
        }
        return sInstance;
    }

    protected Realm getRealm() {
        return sRealm;
    }

    protected <E extends RealmObject> List<E> getList(Class<E> eClass) {
        return convertRealmToList(eClass, sRealm.where(eClass).findAll());
    }

    protected <E extends RealmObject> void saveData(E obj) {
        beginTransaction();
        sRealm.copyToRealmOrUpdate(obj);
        commitTransaction();
    }

    protected <E extends RealmObject> void saveListData(List<E> objs) {
        beginTransaction();
        sRealm.copyToRealmOrUpdate(objs);
        commitTransaction();
    }

    protected <E extends RealmObject> RealmQuery getList(Class<E> eClass, Map condition) {
        Log.i("TAG11",condition+"/////");
        RealmQuery realmQuery = sRealm.where(eClass);
        if (condition != null) {
            Iterator entries = condition.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry thisEntry = (Map.Entry) entries.next();
                Object key = thisEntry.getKey();
                Object value = thisEntry.getValue();
                if (value instanceof Long) {
                    realmQuery.equalTo((String) key, (Long) value);
                } else if (value instanceof String) {
                    realmQuery.equalTo((String) key, (String) value);
                } else if (value instanceof Integer) {
                    realmQuery.equalTo((String) key, (Integer) value);
                } else if (value instanceof Boolean) {
                    realmQuery.equalTo((String) key, (Boolean) value);
                }
            }
        }
        return realmQuery;
    }

    protected <E extends RealmObject> E getListFirst(Class<E> eClass, Map<String, String> condition) {
        return (E) getList(eClass, condition).findFirst();
    }

    protected <E extends RealmObject> List<E> getListData(Class<E> eClass, Map<String, String> condition) {
        return convertRealmToList(eClass, getList(eClass, condition).findAll());
    }

    protected <E extends RealmObject> void deleteObject(Class<E> eClass, Map<String, String> condition) {
        beginTransaction();
        Log.i("TAG11",getList(eClass, condition).findAll()+"//");
        getList(eClass, condition).findAll().deleteAllFromRealm();
        commitTransaction();
    }

    private <E extends RealmObject> List<E> convertRealmToList(Class<E> eClass, List<E> objSources) {
        return sRealm.copyFromRealm(objSources);
    }

    protected void beginTransaction() {
        if (sRealm.isInTransaction()) {
            sRealm.cancelTransaction();
        }
        sRealm.beginTransaction();
    }

    protected void commitTransaction() {
        sRealm.commitTransaction();
    }
}
