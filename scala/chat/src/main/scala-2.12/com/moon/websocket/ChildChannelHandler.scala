package com.moon.websocket


import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.http.{HttpObjectAggregator, HttpServerCodec}
import io.netty.handler.stream.ChunkedWriteHandler

/**
  * Created by Paul on 2017/2/24.
  */
class ChildChannelHandler extends ChannelInitializer[SocketChannel]{
  override def initChannel(ch: SocketChannel): Unit = {
    ch.pipeline().addLast("http-codec",new HttpServerCodec)
    ch.pipeline().addLast("aggregator",new HttpObjectAggregator(65536))
    ch.pipeline().addLast("http-chunked",new ChunkedWriteHandler)
    ch.pipeline().addLast("handler",new WebSocketServerHandler)
  }
}
