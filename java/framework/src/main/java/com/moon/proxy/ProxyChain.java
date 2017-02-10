package com.moon.proxy;

import com.moon.annotation.Proxy;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 代理链
 * Created by Paul on 2017/2/7.
 */
public class ProxyChain {
    private final Class<?> targetClass;
    private final Object targetObject;
    private final Method targetMethod;
    private final MethodProxy methodProxy;
    private final Object[] methodParams;
    private List<Proxy> proxyList=new ArrayList<>();
    private int proxyIndex=0;

    public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod, MethodProxy methodProxy, Object[] methodParams, List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.proxyList = proxyList;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Object proxyChain() throws Throwable {
        Object methodResult;
        if(proxyIndex<proxyList.size()){
            methodResult=proxyList.get(proxyIndex++).proxy(this);
        }else{
            methodResult=methodProxy.invokeSuper(targetObject,methodParams);
        }
        return methodResult;
    }
}
