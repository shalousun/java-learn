package com.yusun.rpc.loadbalance;

import com.yusun.rpc.framework.URL;

import java.util.List;

/**
 * @author yu 2021/1/13.
 */
public interface ILoadBalance {

    URL random(List<URL> list);
}
