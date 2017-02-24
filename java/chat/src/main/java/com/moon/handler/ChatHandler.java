package com.moon.handler;

import com.moon.protocol.BinHeader;
import com.moon.protocol.BinProtocol;
import com.moon.protocol.MessageType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Paul on 2017/2/24.
 */
public class ChatHandler extends SimpleChannelInboundHandler<BinProtocol>{
    private static Logger log= LoggerFactory.getLogger(ChatHandler.class);


    @Override
    protected void messageReceived(ChannelHandlerContext ctx, BinProtocol msg) throws Exception {
        log.info("message = {}",msg);
        String body="OK";
        BinHeader header=new BinHeader(
          msg.getHeader().getSid(),
                MessageType.RESPONSE_OK,
                -1,
                -1,
                -1,
                BinHeader.HEADER_LENGTH+body.getBytes().length
        );
        BinProtocol resp=new BinProtocol(header,body);
        ctx.channel().writeAndFlush(resp).addListener(future -> {
            log.info("sent");
            ctx.close();
        });
    }
}
