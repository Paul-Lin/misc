package com.moon.helper;

import com.moon.bean.FormParam;
import com.moon.bean.Param;
import org.apache.commons.lang3.ArrayUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Stream;

/**
 * 请求助手类
 * Created by Paul on 2017/2/12.
 */
public class RequestHelper {
    public static Param createParam(HttpServletRequest request)throws IOException{
        List<FormParam> formParamList=new ArrayList<>();
//        formParamList.addAll()
        return new Param(formParamList);
    }

    private static List<FormParam> parseParameterNames(HttpServletRequest request){
        List<FormParam> formParamList=new ArrayList<>();
        Enumeration<String> paramNames=request.getParameterNames();
        while(paramNames.hasMoreElements()){
            String fieldName=paramNames.nextElement();
            String[] fieldValues=request.getParameterValues(fieldName);
            if(ArrayUtils.isNotEmpty(fieldValues)){
                Object fieldValue;
                if(fieldValues.length==1){
                    fieldValue=fieldValues[0];
                }else{
                    StringBuilder buf=new StringBuilder("");
                    Stream.of(fieldValues).reduce((l,r)->l+)
                }
            }
        }
    }
}
