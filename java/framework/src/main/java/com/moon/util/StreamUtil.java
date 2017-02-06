package com.moon.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
}
