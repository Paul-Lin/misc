package com.moon.protocol;

/**
 * Created by Paul on 2017/2/24.
 */
public class BinProtocol {
    private BinHeader header;
    private String body;

    public BinProtocol() {
    }

    public BinProtocol(BinHeader header, String body) {
        this.header = header;
        this.body = body;
    }
    public int getLength(){
        return body.getBytes().length+BinHeader.HEADER_LENGTH;
    }

    public BinHeader getHeader() {
        return header;
    }

    public void setHeader(BinHeader header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        final StringBuilder buf=new StringBuilder("BinProtocol{");
        buf.append("header=").append(header);
        buf.append(",body=").append(body).append('\'');
        buf.append('}');
        return buf.toString();
    }
}
