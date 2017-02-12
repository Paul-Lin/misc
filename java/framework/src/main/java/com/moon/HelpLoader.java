package com.moon;

import com.moon.helper.*;

import java.util.stream.Stream;

/**
 * 加载相应的类
 * Created by Paul on 2017/2/12.
 */
public class HelpLoader {
    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        Stream.of(classList).forEach(cls->ClassUtil.loadClass(cls.getName(),true));
    }
}
