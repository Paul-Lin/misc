package com.moon.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Paul on 2017/2/13.
 */
public final class ServletHelper {
    private static final Logger LOGGER= LoggerFactory.getLogger(ServletHelper.class);
    private static final ThreadLocal<ServletHelper> SERVLET_HELPER_HOLDER=new ThreadLocal<>();

    private HttpServletRequest req;
    private HttpServletResponse resp;

    public ServletHelper(HttpServletRequest req, HttpServletResponse resp) {
        this.req = req;
        this.resp = resp;
    }

    /**
     * 初始化
     * @param req
     * @param resp
     */
    public static void init(HttpServletRequest req,HttpServletResponse resp){
        SERVLET_HELPER_HOLDER.set(new ServletHelper(req,resp));
    }

    /**
     * 销毁
     */
    public static void destroy(){
        SERVLET_HELPER_HOLDER.remove();
    }

    /**
     * 获取Request对象
     * @return
     */
    private static HttpServletRequest getRequest(){
        return SERVLET_HELPER_HOLDER.get().req;
    }

    /**
     * 获取Response对象
     * @return
     */
    private static HttpServletResponse getResponse(){
        return SERVLET_HELPER_HOLDER.get().resp;
    }

    /**
     * 获取session对象
     * @return
     */
    private static HttpSession getSession(){
        return getRequest().getSession();
    }

    /**
     * 获取ServletContext对象
     * @return
     */
    private static ServletContext getServletContext(){
        return getSession().getServletContext();
    }

    /**
     * 将属性放入request中
     * @param key
     * @param value
     */
    public static void setRequestAttribute(String key,Object value){
        getRequest().setAttribute(key,value);
    }

    /**
     * 从Request中获取属性
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T getRequestAttribute(String key){
        return (T) getRequest().getAttribute(key);
    }

    /**
     * 从Request中移除属性
     * @param key
     */
    public static void remoteRequestAttribute(String key){
        getRequest().removeAttribute(key);
    }

    /**
     * 发送重定向响应
     * @param location
     */
    public static void sendRedirect(String location){
        try{
            getResponse().sendRedirect(getRequest().getContextPath()+location);
        } catch (IOException e) {
            LOGGER.error("redirect failure ",e);
        }
    }

    /**
     * 将属性放入Session中
     * @param key
     * @param value
     */
    public static void setSessionAttribute(String key,Object value){
        getSession().setAttribute(key,value);
    }

    /**
     * 从Session中获取属性
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T getSessionAttribute(String key){
        return (T)getRequest().getSession().getAttribute(key);
    }

    /**
     * 从Session中移除属性
     * @param key
     */
    public static void removeSessionAttribute(String key){
        getRequest().getSession().removeAttribute(key);
    }

    /**
     * 使Session失效
     */
    public static void invalidateSession(){
        getRequest().getSession().invalidate();
    }
}
