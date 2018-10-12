package com.school.management.api.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Title: NettyServer
 * Description: Netty服务端  Http测试
 * Version:1.0.0
 *
 * @author pancm
 * @date 2017年10月26日
 */
@Component
public class NettyServer {

    private static Logger logger = Logger.getLogger(NettyServer.class);

    /**
     * 启动socket 服务器
     *
     * @param port 端口号
     */
    public static void start(int port) {

        // 通过nio方式来接收连接和处理连接
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        try {
            serverBootstrap.group(boss, worker);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 10 * 1024 * 1024);
            serverBootstrap.option(ChannelOption.TCP_NODELAY, true);
            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            //设置过滤器
            serverBootstrap.childHandler(new NettyServerFilter());
            // 服务器绑定端口监听

            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            if (channelFuture.isDone()) {
                logger.info(String.format("服务器绑定端口号 %s 成功", port));
            }
            Channel channel = channelFuture.channel();
            /*
             * CloseFuture异步方式关闭
             */
            channel.closeFuture().sync();

        } catch (InterruptedException e) {
            logger.warn("服务器出现异常", e);
        } finally {
            //关闭EventLoopGroup，释放掉所有资源包括创建的线程
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }


}
