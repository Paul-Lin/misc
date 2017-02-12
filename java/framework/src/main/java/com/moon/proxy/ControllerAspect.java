package com.moon.proxy;

import com.moon.annotation.Aspect;
import com.moon.annotation.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 拦截Controller所有方法
 * Created by Paul on 2017/2/12.
 */
@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy{
    private static final Logger LOGGER= LoggerFactory.getLogger(ControllerAspect.class);
    private long begin;

    @Override
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
        LOGGER.debug("----------- begin -----------");
        LOGGER.debug(String.format("class: %s",cls.getName()));
        LOGGER.debug(String.format("method: %s",method.getName()));
        super.before(cls, method, params);
        begin=System.currentTimeMillis();

    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {
        LOGGER.debug(String.format("time: %dms"),System.currentTimeMillis()-begin);

        super.after(cls, method, params, result);
        LOGGER.debug("----------- end --------------");
    }
}
