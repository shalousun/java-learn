package com.sunyu.pool;

/**
 * @author yu 2020/11/14.
 */
public abstract class CustomAbstractThreadPool implements CustomThreadPool{

    /**
     * 处理提交的任务
     * 这里可以对task做一些扩展，例如增加个返回值
     * 我这里就不扩展了，直接让返回类型是void
     * @param task
     */
    @Override
    public void submit(Runnable task) {
        if (task == null) throw new NullPointerException();
        execute(task);
    }
}
