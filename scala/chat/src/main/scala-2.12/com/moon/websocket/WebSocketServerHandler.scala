package com.moon.websocket

import io.netty.channel.{ChannelHandlerContext, SimpleChannelInboundHandler}

/**
  * Created by Paul on 2017/2/24.
  */
class WebSocketServerHandler extends SimpleChannelInboundHandler[Object]{
  //val log:Logger=LoggerFactory.getLogger(classOf[WebSocketServerHandler])
  override def messageReceived(ctx: ChannelHandlerContext, msg: Object): Unit = ???
}
