package com.moon.annotation;

import java.lang.annotation.*;

/**
 * 切面注解
 * Created by Paul on 2017/2/7.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    /**
     * 注解
     * @return
     */
    Class<? extends Annotation> value();
}
