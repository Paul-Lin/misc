package com.moon;

import com.moon.annotation.Controller;
import com.moon.bean.Data;
import com.moon.bean.Handler;
import com.moon.bean.Param;
import com.moon.bean.View;
import com.moon.helper.BeanHelper;
import com.moon.helper.ControllerHelper;
import com.moon.helper.HelperLoader;
import com.moon.util.CodecUtil;
import com.moon.util.JsonUtil;
import com.moon.util.ReflectionUtil;
import com.moon.util.StreamUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

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
            String body= CodecUtil.decodeURL(StreamUtil.getString(req.getInputStream()));
            if(StringUtils.isNotBlank(body)){
                String[] params=body.split("&");
                Stream.of(params).forEach(x->{
                    String[] array=x.split("=");
                    if(ArrayUtils.isNotEmpty(array)&&array.length==2){
                        String paramName=array[0];
                        String paramValue=array[1];
                        paramMap.put(paramName,paramValue);
                    }
                });
            }
            Param param=new Param(paramMap);
            // 调用Action方法
            Method actionMethod=handler.getActionMethod();
            Object result= ReflectionUtil.invokeMethod(controllerBean,actionMethod,param);
            // 处理Action方法返回值
            if(result instanceof View){
                // 返回JSP页面
                View view= (View) result;
                String path=view.getPath();
                if(StringUtils.isNotBlank(path)){
                    if(path.startsWith("/")){
                        resp.sendRedirect(req.getContextPath()+path);
                    }else{
                        Map<String,Object> model=view.getModel();
                        model.entrySet().forEach(x->{
                            req.setAttribute(x.getKey(),x.getValue());
                        });
                        String appJspPath="";
                        req.getRequestDispatcher(appJspPath+path).forward(req,resp);
                    }
                }
            }else if(result instanceof Data){
                // 返回JSON数据
                Data data=(Data) result;
                Object model=data.getModel();
                if(Objects.nonNull(model)){
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    PrintWriter writer=resp.getWriter();
                    String json= JsonUtil.toJson(model);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }
            }
        }
    }
}
