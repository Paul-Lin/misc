package com.moon.helper;

import com.moon.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Bean助手类
 * Created by Paul on 2017/2/3.
 */
public final class BeanHelper {
    // 定义Bean映射（用于存放Bean类与Bean实例的映射关系）
    private static final Map<Class<?>,Object> BEAN_MAP=new ConcurrentHashMap<>();

    static{
        Set<Class<?>> beanClassSet=ClassHelper.getBeanClassSet();
        beanClassSet.forEach(x->BEAN_MAP.put(x, ReflectionUtil.newInstance(x)));
    }

    /**
     * 获取Bean映射
     * @return
     */
    public static Map<Class<?>,Object> getBeanMap(){
        return BEAN_MAP;
    }

    /**
     * 获取Bean实例
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> cls){
        if(!BEAN_MAP.containsKey(cls))
            throw new RuntimeException("can not get bean by class: "+cls);
        return (T) BEAN_MAP.get(cls);
    }

    /**
     * 设置Bean实例
     * @param cls
     * @param obj
     */
    public static void setBean(Class<?> cls,Object obj){
        BEAN_MAP.put(cls,obj);
    }
}
