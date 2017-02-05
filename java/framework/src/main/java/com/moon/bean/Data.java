package com.moon.bean;

/**
 * 返回数据对象封装了一个Object类型的模型数据，
 * 框架会将改对象写入HttpServletResponse对象中，从而直接输出至浏览器
 * Created by Paul on 2017/2/3.
 */
public class Data {
    // 模型数据
    private Object model;

    public Data(Object model) {
        this.model = model;
    }

    public Object getModel(){
        return model;
    }
}
