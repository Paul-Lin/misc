package com.moon.protocol;

import java.util.Date;

/**
 * Created by Paul on 2017/2/24.
 */
public class BinHeader {
    private String sid;
    private Integer length;
    private Date sentTime;
    private MessageType type;
    private Integer targetUserId;
    private Integer targetRoomId;
    private Integer memId;
    public static int HEADER_LENGTH=36;

    public BinHeader() {
    }

    public BinHeader(String sid,MessageType type,  Integer targetUserId, Integer targetRoomId, Integer memId, Integer length) {
        this.sid = sid;
        this.length = HEADER_LENGTH+length;
        this.sentTime = new Date();
        this.type = type;
        this.targetUserId = targetUserId;
        this.targetRoomId = targetRoomId;
        this.memId = memId;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Date getSentTime() {
        return sentTime;
    }

    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Integer getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(Integer targetUserId) {
        this.targetUserId = targetUserId;
    }

    public Integer getTargetRoomId() {
        return targetRoomId;
    }

    public void setTargetRoomId(Integer targetRoomId) {
        this.targetRoomId = targetRoomId;
    }

    public Integer getMemId() {
        return memId;
    }

    public void setMemId(Integer memId) {
        this.memId = memId;
    }

    @Override
    public String toString() {
        return "BinHeader{" +
                "sid='" + sid + '\'' +
                ", length=" + length +
                ", sentTime=" + sentTime +
                ", type=" + type +
                ", targetUserId=" + targetUserId +
                ", targetRoomId=" + targetRoomId +
                ", memId=" + memId +
                '}';
    }
}
