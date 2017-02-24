package com.moon.codec;

import com.moon.protocol.BinProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by Paul on 2017/2/24.
 */
public class BinProtocolEncoder extends MessageToByteEncoder<BinProtocol>{
    @Override
    protected void encode(ChannelHandlerContext ctx, BinProtocol msg, ByteBuf out) throws Exception {
        out.writeBytes(msg.getHeader().getSid().getBytes());
        out.writeInt(msg.getLength());
        out.writeLong(msg.getHeader().getSentTime().getTime());
        out.writeBytes(msg.getHeader().getType().code().getBytes());
        out.writeInt(msg.getHeader().getTargetUserId());
        out.writeInt(msg.getHeader().getTargetRoomId());
        out.writeInt(msg.getHeader().getMemId());
        out.writeBytes(msg.getBody().getBytes());
    }
}
