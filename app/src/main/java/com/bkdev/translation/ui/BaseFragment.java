package com.bkdev.translation.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

/**
 * Base Fragment.
 *
 * @author Binc
 */
@EFragment
public abstract class BaseFragment extends Fragment {

    @AfterViews
    protected abstract void init();

    public void replaceFragment(Fragment fragment, int idContainer, boolean addToBackStack) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.replace(idContainer, fragment, fragment.getClass().getSimpleName());
        if (!isRemoving()) {
            ft.commit();
        }
        fm.executePendingTransactions();
    }

}
