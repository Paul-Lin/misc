package com.moon.bean;

import java.util.Map;

/**
 * 请求参数对象
 * Created by Paul on 2017/2/3.
 */
public class Param {
    private Map<String,Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    /**
     * 根据参数名获取long型的参数值
     * @param name
     * @return
     */
    public long getLong(String name){
        return (long) paramMap.get(name);
    }

    /**
     * 获取所有字段信息
     * @return
     */
    public Map<String, Object> getMap() {
        return paramMap;
    }
}
