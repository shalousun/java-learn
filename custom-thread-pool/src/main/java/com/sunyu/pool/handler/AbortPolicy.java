package com.sunyu.pool.handler;

import com.sunyu.pool.CustomThreadPool;
import com.sunyu.pool.excption.RejectedException;

/**
 * @author yu 2020/11/14.
 */
public class AbortPolicy implements RejectedHandler {
    @Override
    public void rejected(Runnable r, CustomThreadPool pool) {
        //直接抛异常
        throw new RejectedException("Task is rejuectd");
    }
}
