package com.sunyu.pool.handler;

import com.sunyu.pool.CustomThreadPool;

/**
 * @author yu 2020/11/14.
 */
public interface RejectedHandler {
    void rejected(Runnable r, CustomThreadPool pool);
}
