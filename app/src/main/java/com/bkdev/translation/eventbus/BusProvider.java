package com.bkdev.translation.eventbus;

import com.squareup.otto.Bus;

/**
 * Bus provider
 *
 * @author BiNC
 */
public final class BusProvider {
    private static Bus sBus;

    private BusProvider() {
    }

    public static synchronized Bus getInstance() {
        if (sBus == null) {
            sBus = new Bus();
        }
        return sBus;
    }
}
