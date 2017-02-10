package com.moon.annotation;

import com.moon.proxy.ProxyChain;

/**
 * 代理接口
 * Created by Paul on 2017/2/7.
 */
public interface Proxy {
    Object proxy(ProxyChain proxyChain)throws Throwable;
}
