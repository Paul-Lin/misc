package com.moon.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 编码与解码操作工具类
 * Created by Paul on 2017/2/3.
 */
public final class CodecUtil {
    private static final Logger LOGGER= LoggerFactory.getLogger(CodecUtil.class);
    public static String encodeURL(String source){
        String target;
        try {
            target= URLEncoder.encode(source,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("encode url failure",e);
            throw new RuntimeException();
        }
        return target;
    }
}
