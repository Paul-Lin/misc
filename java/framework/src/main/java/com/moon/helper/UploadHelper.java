package com.moon.helper;



import com.moon.bean.FileParam;
import com.moon.bean.FormParam;
import com.moon.bean.Param;
import com.moon.util.FileUtil;
import com.moon.util.StreamUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * 文件上传助手类
 * Created by Paul on 2017/2/12.
 */
public final class UploadHelper {
    private static final Logger LOGGER= LoggerFactory.getLogger(UploadHelper.class);
    // commons提供的Servlet文件上传对象
    private static ServletFileUpload servletFileUpload;

    public static void init(ServletContext servletContext){
        File repository= (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        servletFileUpload=new ServletFileUpload(new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD,repository));
        int uploadLimit=1000;
        if(uploadLimit!=0){
            servletFileUpload.setFileSizeMax(uploadLimit*1024*1024);
        }
    }

    /**
     * 判断请求是否为multipart类型
     * @param request
     * @return
     */
    public static boolean isMultipart(HttpServletRequest request){
        return servletFileUpload.isMultipartContent(request);
    }

    public static Param createParam(HttpServletRequest request)throws IOException{
        List<FormParam> formParamList=new ArrayList<>();
        List<FileParam> fileParamList=new ArrayList<>();
        try{
            Map<String,List<FileItem>> fileItemListMap=servletFileUpload.parseParameterMap(request);
            if(MapUtils.isNotEmpty(fileItemListMap)){
                fileItemListMap.entrySet().stream().forEach(fileItemListEntry->{
                    String fieldName=fileItemListEntry.getKey();
                    List<FileItem> fileItemList=fileItemListEntry.getValue();
                    if(CollectionUtils.isNotEmpty(fileItemList)){
                        fileItemList.stream().forEach(fileItem->{
                            if(fileItem.isFormField()){
                                try {
                                    String fieldValue=fileItem.getString("UTF-8");
                                    formParamList.add(new FormParam(fieldName,fieldValue));
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }else{
                                try {
                                    String fileName= FileUtil.getRealFileName(new String(fileItem.getName().getBytes(),"UTF-8"));
                                    if(StringUtils.isNotEmpty(fileName)){
                                        long fileSize=fileItem.getSize();
                                        String contentType=fileItem.getContentType();
                                        InputStream inputStream=fileItem.getInputStream();
                                        fileParamList.add(new FileParam(fieldName,fileName,fileSize,contentType,inputStream));
                                    }
                                } catch (UnsupportedEncodingException e) {
                                    LOGGER.error("create param failure",e);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        });
                    }
                });
            }
        } catch (FileUploadException e) {
            LOGGER.error("create param failure",e);
            throw new RuntimeException(e);
        }
        return new Param(formParamList,fileParamList);
    }

    /**
     * 上传文件
     * @param basePath
     * @param fileParam
     */
    public static void uploadFile(String basePath,FileParam fileParam){
        if(Objects.nonNull(fileParam)){
            String filePath=basePath+fileParam.getFileName();
            FileUtil.createFile(filePath);
            try(InputStream inputStream=new BufferedInputStream(fileParam.getInputStream());OutputStream outputStream=new BufferedOutputStream(new FileOutputStream(filePath))){
                StreamUtil.copyStream(inputStream,outputStream);
            } catch (IOException e) {
                LOGGER.error("upload file failure",e);
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 批量上传文件
     * @param basePath
     * @param fileParamList
     */
    public static void uploadFile(String basePath,List<FileParam> fileParamList){
        try{
            if(CollectionUtils.isNotEmpty(fileParamList)){
                fileParamList.forEach(fileParam -> {
                    uploadFile(basePath,fileParam);
                });
            }
        }catch (Exception e){
            LOGGER.error("upload file failure",e);
            throw new RuntimeException(e);
        }
    }
}
