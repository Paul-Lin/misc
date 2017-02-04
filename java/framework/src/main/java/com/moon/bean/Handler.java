package com.moon.bean;

import java.lang.reflect.Method;

/**
 * Created by Paul on 2017/2/3.
 */
public class Handler {
    // Controller 类
    private Class<?> controllerClass;
    // Action方法
    private Method actionMethod;

    public Handler(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}
