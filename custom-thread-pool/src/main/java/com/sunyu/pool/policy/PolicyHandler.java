package com.sunyu.pool.policy;

import com.sunyu.pool.CustomThreadPoolExecutor;

/**
 * @author yu 2020/11/14.
 */
public interface PolicyHandler {

    void rejected(Runnable task, CustomThreadPoolExecutor executor);
}
