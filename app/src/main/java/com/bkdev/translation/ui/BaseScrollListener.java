package com.bkdev.translation.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * BaseScrollListener.
 *
 * @author BiNC
 */
public abstract class BaseScrollListener extends RecyclerView.OnScrollListener {
    // Load before 2 item .
    private static final int OFFSET = 2;
    private static final int NUMBER_ARRAY = 2;

    private final RecyclerView.LayoutManager mLayoutManager;

    public BaseScrollListener(@NonNull RecyclerView.LayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    public abstract void onLoadMore();

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = mLayoutManager.getItemCount();
        int firstVisibleItem = 0;

        if (mLayoutManager instanceof GridLayoutManager) {
            firstVisibleItem = ((GridLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
        } else if (mLayoutManager instanceof LinearLayoutManager) {
            firstVisibleItem = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
        } else if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            // handler crash app when don't get findFirstVisibleItemPositions
            int[] firstVisibleItemPositions = new int[NUMBER_ARRAY];
            try {
                firstVisibleItem = ((StaggeredGridLayoutManager) mLayoutManager).findFirstVisibleItemPositions(firstVisibleItemPositions)[0];
            } catch (NullPointerException ex) {
                // workaround for RecyclerView bug. assume not laid out, so no items visible
                firstVisibleItemPositions[0] = RecyclerView.NO_POSITION;
                firstVisibleItemPositions[1] = RecyclerView.NO_POSITION;
            }
        }

        if (firstVisibleItem + visibleItemCount + OFFSET >= totalItemCount) {
            onLoadMore();
        }
    }
}
