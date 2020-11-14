package com.sunyu.pool.policy;

import com.sunyu.pool.CustomThreadPoolExecutor;
import com.sunyu.pool.excption.PolicyException;

/**
 * @author yu 2020/11/14.
 */
public class DefaultPolicyHandler implements PolicyHandler {

    @Override
    public void rejected(Runnable task, CustomThreadPoolExecutor executor) {
        throw new PolicyException("任务已满");
    }
}
