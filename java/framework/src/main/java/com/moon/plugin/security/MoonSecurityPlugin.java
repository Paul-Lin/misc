package com.moon.plugin.security;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

/**
 * Created by Paul on 2017/2/13.
 */
public class MoonSecurityPlugin implements ServletContainerInitializer{
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {

    }
}
