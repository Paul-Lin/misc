package com.moon.helper;

import com.moon.annotation.Aspect;
import com.moon.annotation.Service;
import com.moon.proxy.AspectProxy;
import com.moon.proxy.Proxy;
import com.moon.proxy.ProxyManager;
import com.moon.proxy.TransactionProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * Created by Paul on 2017/2/12.
 */
public final class AopHelper {
    private static final Logger LOGGER= LoggerFactory.getLogger(AopHelper.class);
    static{
        try{
            Map<Class<?>,Set<Class<?>>> proxyMap=createProxyMap();
            Map<Class<?>,List<Proxy>> targetMap=createTargetMap(proxyMap);
            targetMap.entrySet().stream().forEach(targetEntry->{
                Class<?> targetClass=targetEntry.getKey();
                List<Proxy> proxyList=targetEntry.getValue();
                Object proxy= ProxyManager.createProxy(targetClass,proxyList);
                BeanHelper.setBean(targetClass,proxy);
            });
        }catch(Exception e){
            LOGGER.error("aop failure",e);
        }
    }

    private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception {
        Set<Class<?>> targetClassSet = new HashSet<>();
        Class<? extends Annotation> annotation = aspect.value();
        if (Objects.nonNull(annotation) && !annotation.equals(Aspect.class)) {
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return targetClassSet;
    }

    private static void addAspectProxy(Map<Class<?>,Set<Class<?>>> proxyMap){
        Set<Class<?>> proxyClassSet=ClassHelper.getClassSetBySuper(AspectProxy.class);
        proxyClassSet.stream().filter(proxyClass->proxyClass.isAnnotationPresent(Aspect.class)).forEach(proxyClass->{
            Aspect aspect=proxyClass.getAnnotation(Aspect.class);
            try {
                Set<Class<?>> targetClassSet=createTargetClassSet(aspect);
                proxyMap.put(proxyClass,targetClassSet);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    private static void addTransactionProxy(Map<Class<?>,Set<Class<?>>> proxyMap){
        Set<Class<?>> serviceClassSet=ClassHelper.getClassSetByAnnotation(Service.class);
        proxyMap.put(TransactionProxy.class,serviceClassSet);
    }
    private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        addAspectProxy(proxyMap);
        addTransactionProxy(proxyMap);
        return proxyMap;
    }

    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
        proxyMap.entrySet().forEach(proxyEntry -> {
            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            targetClassSet.forEach(targetClass -> {
                try {
                    Proxy proxy = (Proxy) proxyClass.newInstance();
                    if (targetMap.containsKey(targetClass)) {
                        targetMap.get(targetClass).add(proxy);
                    } else {
                        List<Proxy> proxyList = new ArrayList<>();
                        proxyList.add(proxy);
                        targetMap.put(targetClass, proxyList);
                    }
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        });
        return targetMap;
    }


}
