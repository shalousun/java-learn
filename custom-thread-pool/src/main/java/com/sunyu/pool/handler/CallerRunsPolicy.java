package com.sunyu.pool.handler;

import com.sunyu.pool.CustomThreadPool;

/**
 * @author yu 2020/11/14.
 */
public class CallerRunsPolicy implements RejectedHandler {

    @Override
    public void rejected(Runnable r, CustomThreadPool pool) {
        if (r != null)
            r.run();
    }
}
