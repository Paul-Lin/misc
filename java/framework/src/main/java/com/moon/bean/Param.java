package com.moon.bean;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求参数对象
 * Created by Paul on 2017/2/3.
 */
public class Param {
    private List<FormParam> formParamList;
    private List<FileParam> fileParamList;

    public Param(List<FormParam> formParamList) {
        this.formParamList = formParamList;
    }

    public Param(List<FormParam> formParamList, List<FileParam> fileParamList) {
        this.formParamList = formParamList;
        this.fileParamList = fileParamList;
    }

    public Map<String,Object> getFieldMap(){
        Map<String,Object> fieldMap=new HashMap<>();
        final String SEPARATOR=String.valueOf((char)29);
        if(CollectionUtils.isNotEmpty(formParamList)){
            formParamList.stream().forEach(formParam -> {
                String fieldName=formParam.getFieldName();
                Object fieldValue=formParam.getFieldValue();
                if(fieldMap.containsKey(fieldName)){
                    fieldValue=fieldMap.get(fieldName)+SEPARATOR+fieldValue;
                }
                fieldMap.put(fieldName,fieldValue);
            });
        }
        return fieldMap;
    }

    public Map<String,List<FileParam>> getFileMap(){
        Map<String,List<FileParam>> fileMap=new HashMap<>();
        if(CollectionUtils.isNotEmpty(fileParamList)){
            fileParamList.stream().forEach(fileParam->{
                String fileName=fileParam.getFieldName();
                List<FileParam> fileParamList1;
                if(fileMap.containsKey(fileName)){
                    fileParamList1=fileMap.get(fileName);

                }else{
                    fileParamList1=new ArrayList<>();

                }
                fileParamList1.add(fileParam);
                fileMap.put(fileName,fileParamList1);
            });
        }
        return fileMap;
    }

    /**
     * 获取所有上传的文件
     * @param fieldName
     * @return
     */
    public List<FileParam> getFieldList(String fieldName){
        return getFileMap().get(fieldName);
    }

    /**
     * 获取唯一上传文件
     * @param fieldName
     * @return
     */
    public FileParam getFile(String fieldName){
        List<FileParam> fileParamList=getFieldList(fieldName);
        if(CollectionUtils.isNotEmpty(fileParamList)&&fileParamList.size()==1){
            return fileParamList.get(0);
        }
        return null;
    }

    /**
     * 验证参数是否为空
     * @return
     */
    public boolean isEmpty(){
        return CollectionUtils.isEmpty(fileParamList)&&CollectionUtils.isEmpty(formParamList);
    }

    /**
     * 根据参数名获取String型参数值
     * @param name
     * @return
     */
    public String getString(String name){
        return getFileMap().get(name).toString();
    }
    /**
     * 根据参数名获取double型参数值
     * @param name
     * @return
     */
    public double getDouble(String name){
        return (double) getFieldMap().get(name);
    }
    /**
     * 根据参数名获取long型参数值
     * @param name
     * @return
     */
    public long getLong(String name){
        return (long) getFieldMap().get(name);
    }
    /**
     * 根据参数名获取int型参数值
     * @param name
     * @return
     */
    public int getInt(String name){
        return (int) getFieldMap().get(name);
    }

    /**
     * 根据参数名获取boolean型参数值
     * @param name
     * @return
     */
    public boolean getBoolean(String name){
        return (boolean) getFieldMap().get(name);
    }

}
