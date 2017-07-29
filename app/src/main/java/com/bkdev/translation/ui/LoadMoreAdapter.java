package com.bkdev.translation.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bkdev.translation.R;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * LoadMoreAdapter.
 *
 * @author BiNC
 */
public class LoadMoreAdapter extends BaseAdapter {
    private final BaseAdapter mAdapter;
    @Accessors(prefix = "m")
    @Getter
    private boolean mProgressBarVisible;

    public LoadMoreAdapter(@NonNull Context context, @NonNull BaseAdapter adapter) {
        super(context);
        mAdapter = adapter;
    }

    public void displayLoadMore(boolean progressBarVisible) {
        mProgressBarVisible = progressBarVisible;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load_more, parent, false);
            return new LoadMoreHolder(view);
        }
        return mAdapter.onCreateViewHolder(parent, viewType - 1);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == 0) {
            // type load more
            onBindLoadMoreHolder((LoadMoreHolder) holder);
        } else {
            mAdapter.onBindViewHolder(holder, position);
        }
    }

    private void onBindLoadMoreHolder(LoadMoreHolder holder) {
        holder.mProgressBar.setVisibility(mProgressBarVisible ? View.VISIBLE : View.GONE);
    }

    /**
     * Count + 1 for add mView at bottom
     */
    @Override
    public int getItemCount() {
        return mAdapter.getItemCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            // last item
            return 0;
        }
        return mAdapter.getItemViewType(position) + 1;
    }

    public BaseAdapter getBaseAdapter() {
        return mAdapter;
    }

    /**
     * A holder describes an item mView and metadata about its place within the RecyclerView.
     */
    class LoadMoreHolder extends RecyclerView.ViewHolder {

        private final ProgressBar mProgressBar;

        public LoadMoreHolder(View itemView) {
            super(itemView);
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }

}
