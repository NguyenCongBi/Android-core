package com.bkdev.translation.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * A simple adapter with one list.
 * <p>
 * Reference {@link android.widget.ArrayAdapter}
 *
 * @param <VH> is a type extend from {@link RecyclerView.ViewHolder}
 * @param <T>  is a model
 * @author Binc
 */
public abstract class BaseSimpleAdapter<T, VH extends BaseHolder> extends BaseAdapter<VH> {
    /**
     * Main DataSet.
     * <p>
     * Getting item of list by {@link BaseSimpleAdapter#getItem(int)}
     */
    private final List<T> mObjects;

    public BaseSimpleAdapter(@NonNull Context context, @NonNull List<T> ts) {
        super(context);
        mObjects = ts;
    }

    @Override
    public int getItemCount() {
        return mObjects.size();
    }

    protected T getItem(int index) {
        return mObjects.get(index);
    }

    @NonNull
    protected List<T> getDataList() {
        return mObjects;
    }
}
