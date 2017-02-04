package com.moon;

import com.moon.annotation.Controller;
import com.moon.bean.Handler;
import com.moon.helper.BeanHelper;
import com.moon.helper.ControllerHelper;
import com.moon.helper.HelperLoader;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Paul on 2017/2/3.
 */
@WebServlet(urlPatterns = "/*",loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet{
    @Override
    public void init(ServletConfig config) throws ServletException {
        // 初始化相关的Helper类
        HelperLoader.init();
        // 获取ServletContext对象（用于注册Servlet）
        ServletContext servletContext= config.getServletContext();
        // 注册处理JSP的Servlet
        ServletRegistration jspServlet=servletContext.getServletRegistration("jsp");
        String appJspPath="";
        jspServlet.addMapping(appJspPath+"*");
        // 注册处理静态资源默认的Servlet
        ServletRegistration defaultServlet=servletContext.getServletRegistration("default");
        String appAssertPath="";
        defaultServlet.addMapping(appAssertPath+"*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取请求方法与请求路径
        String requestMethod=req.getMethod().toLowerCase();
        String requestPath=req.getPathInfo();
        // 获取Action处理器
        Handler handler= ControllerHelper.getHandler(requestMethod,requestPath);
        if(Objects.nonNull(handler)){
            // 获取Controller类及其Bean实例
            Class<?> controllerClass=handler.getControllerClass();
            Object controllerBean= BeanHelper.getBean(controllerClass);
            // 创建请求参数对象
            Map<String,Object> paramMap=new ConcurrentHashMap<>();
            Enumeration<String> paramNames=req.getParameterNames();
            while(paramNames.hasMoreElements()){
                String paramName=paramNames.nextElement();
                String paramValue=req.getParameter(paramName);
                paramMap.put(paramName,paramValue);
            }
//            String body=CodecUtil
        }
    }
}
