package com.moon;

import com.moon.codec.BinProtocolDecoder;
import com.moon.codec.BinProtocolEncoder;
import com.moon.handler.ChatHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 */
public class App {
    public static Logger logger = LoggerFactory.getLogger(App.class);

    public static int DEFAULT_PORT = 7575;

    public static String DEFAULT_HOST = "localhost";

    public static void main(String[] args) {
        logger.info("starting server");
        long startTime = System.currentTimeMillis();

        EventLoopGroup masterGroup = new NioEventLoopGroup(1);
        EventLoopGroup slaveGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap boot = new ServerBootstrap();
            boot.group(masterGroup, slaveGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pip = ch.pipeline();
                            pip.addLast("decoder", new BinProtocolDecoder());
                            pip.addLast("handler", new ChatHandler());
                            pip.addLast("encoder", new BinProtocolEncoder());
                        }
                    });

            ChannelFuture f = boot.bind(DEFAULT_PORT).sync();
            logger.info("server is listening at {}:{}/{}", DEFAULT_HOST, DEFAULT_PORT, "chat");
            logger.info("time consume:{}ms", System.currentTimeMillis() - startTime);
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            logger.info("stopping server");
            masterGroup.shutdownGracefully();
            slaveGroup.shutdownGracefully();
            logger.info("server stopped");
        }
    }
}
