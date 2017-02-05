package com.moon.helper;

import com.moon.ClassUtil;

import java.util.stream.Stream;

/**
 * 加载相应的Helper类
 * Created by Paul on 2017/2/3.
 */
public class HelperLoader {
    public static void init(){
        Class<?>[] classList={
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        Stream.of(classList).forEach(x-> ClassUtil.loadClass(x.getName(),false));
    }
}
