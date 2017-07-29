package com.bkdev.translation.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bkdev.translation.R;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * BaseListLoadMoreFragment.
 *
 * @author BiNC
 */
@EFragment
public abstract class BaseListLoadMoreFragment<A extends BaseAdapter> extends BaseFragment {

    @ViewById(R.id.recyclerView)
    protected RecyclerView mRecyclerView;
    private A mAdapter;
    protected int mTotalPage;
    protected int mCurrentPage;

    protected abstract void initView();

    protected abstract A initAdapter();

    protected abstract void initData();

    protected abstract boolean checkLoadMore();

    @Override
    protected void init() {
        mAdapter = initAdapter();
        initView();
        initRecyclerView();
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        getAdapter().notifyDataSetChanged();
    }

    protected A getAdapter() {
        return mAdapter;
    }

    private void initRecyclerView() {
        final LinearLayoutManager linearLayoutManager;
        linearLayoutManager = new LinearLayoutManagerWithSmoothScroller(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        if (checkLoadMore()) {
            mRecyclerView.addOnScrollListener(new BaseScrollListener(linearLayoutManager) {
                @Override
                public void onLoadMore() {
                    if (getAdapter() instanceof LoadMoreAdapter && ((LoadMoreAdapter) getAdapter()).isProgressBarVisible()) {
                        return;
                    }
                    if (mCurrentPage < mTotalPage) {
                        displayLoadMore(true);
                        initData();
                    }
                }
            });
        }
    }

    /**
     * This method is used to add progressbar in bottom when load more data
     */
    protected void displayLoadMore(boolean display) {
        if (getAdapter() instanceof LoadMoreAdapter) {
            ((LoadMoreAdapter) getAdapter()).displayLoadMore(display);
        }
    }

}
