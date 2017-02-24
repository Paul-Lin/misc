package com.moon.protocol;

import java.util.Objects;

/**
 * Created by Paul on 2017/2/24.
 */
public enum MessageType {
    // 握手数据包
    HANDSHAKE("00"),
    // 结束会话数据包
    CLOSE("01"),
    ONE_TO_ONE("02"),
    ONE_TO_MANY("03"),
    // 服务器返回确认数据包
    RESPONSE_OK("04"),
    RESPONSE_ERROR("05");

    private String code;

    MessageType(String code) {
        this.code = code;
    }

    public String code(){
        return this.code;
    }

    public static MessageType fromCode(String code){
        switch (code){
            case "00":
                return HANDSHAKE;
            case "01":
                return CLOSE;
            case "02":
                return ONE_TO_ONE;
            case "03":
                return ONE_TO_MANY;
            case "04":
                return RESPONSE_OK;
            case "05":
                return RESPONSE_ERROR;
            default:return null;
        }
    }
}
