package com.bkdev.translation.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Base Holder.
 *
 * @author Binc.
 */
public abstract class BaseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public abstract void onSingleClick(View v);

    public BaseHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onClick(View v) {
        if (BaseOnClickListener.isBlockingClick()) {
            return;
        }
        onSingleClick(v);
    }
}
