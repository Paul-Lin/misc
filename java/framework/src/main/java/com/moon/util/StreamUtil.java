package com.moon.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Objects;

/**
 * Created by Paul on 2017/2/6.
 */
public final class StreamUtil {
    private static final Logger LOGGER= LoggerFactory.getLogger(StreamUtil.class);

    /**
     * 从输入流中获取字符串
     * @param is
     * @return
     */
    public static String getString(InputStream is){
        StringBuilder buf=new StringBuilder();
        BufferedReader reader=new BufferedReader(new InputStreamReader(is));
        String line;
        try {
            while(Objects.nonNull((line=reader.readLine()))){
                buf.append(line);
            }
        } catch (IOException e) {
            LOGGER.error("get string failure ",e);
            throw new RuntimeException(e);
        }
        return buf.toString();
    }

    /**
     * 将输入流复制到输出流
     * @param inputStream
     * @param outputStream
     */
    public static void copyStream(InputStream inputStream, OutputStream outputStream){
        try(InputStream input=inputStream;OutputStream output=outputStream){
            int length;
            byte[] buffer=new byte[4*1024];
            while((length=input.read(buffer,0,buffer.length))!=-1)
                output.write(buffer,0,length);
            output.flush();
        } catch (IOException e) {
            LOGGER.error("copy stream failure",e);
            throw new RuntimeException(e);
        }
    }
}
