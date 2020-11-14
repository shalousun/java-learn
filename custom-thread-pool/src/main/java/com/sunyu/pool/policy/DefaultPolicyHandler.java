package com.sunyu.pool.policy;

import com.sunyu.pool.CustomThreadPoolExecutor;
import com.sunyu.pool.excption.RejectedException;

/**
 * @author yu 2020/11/14.
 */
public class DefaultPolicyHandler implements PolicyHandler {

    @Override
    public void rejected(Runnable task, CustomThreadPoolExecutor executor) {
        throw new RejectedException("任务已满");
    }
}
