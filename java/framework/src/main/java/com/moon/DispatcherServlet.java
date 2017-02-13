package com.moon;

import com.moon.annotation.Controller;
import com.moon.bean.*;
import com.moon.helper.*;
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
import java.util.*;
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

        UploadHelper.init(servletContext);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletHelper.init(req,resp);
        try{
            // 获取请求方法与请求路径
            String requestMethod=req.getMethod().toLowerCase();
            String requestPath=req.getPathInfo();
            if(requestPath.equals("/favicon.ico"))
                return;
            // 获取Action处理器
            Handler handler= ControllerHelper.getHandler(requestMethod,requestPath);
            if(Objects.nonNull(handler)){
                // 获取Controller类及其Bean实例
                Class<?> controllerClass=handler.getControllerClass();
                Object controllerBean= BeanHelper.getBean(controllerClass);
                // 创建请求参数对象
                Param param;
                if(UploadHelper.isMultipart(req)){
                    param=UploadHelper.createParam(req);
                }else{
                    param= RequestHelper.createParam(req);
                }
                Object result;
                Method actionMethod=handler.getActionMethod();
                if(param.isEmpty()){
                    result=ReflectionUtil.invokeMethod(controllerBean,actionMethod);
                }else{
                    result=ReflectionUtil.invokeMethod(controllerBean,actionMethod,param);
                }

                // 处理Action方法返回值
                if(result instanceof View){
                    handleViewRequeset((View)result,req,resp);
                }else if(result instanceof Data){
                    handleDataResult((Data)result,resp);
                }
            }
        }finally {
            ServletHelper.destroy();
        }

    }

    private void handleViewRequeset(View view,HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
        String path=view.getPath();
        if(StringUtils.isNotBlank(path)){
            if(path.startsWith("/"))
                response.sendRedirect(request.getContextPath()+path);
            else{
                Map<String,Object> model=view.getModel();
                model.entrySet().forEach(entry->{
                    request.setAttribute(entry.getKey(),entry.getValue());
                });
                String appJspPath="";
                request.getRequestDispatcher(appJspPath+path).forward(request,response);
            }
        }
    }

    private void handleDataResult(Data data,HttpServletResponse response)throws IOException{
        Object model=data.getModel();
        if(Objects.nonNull(model)){
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer=response.getWriter();
            String json=JsonUtil.toJson(model);
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }
}
