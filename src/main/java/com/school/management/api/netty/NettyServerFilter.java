package com.school.management.api.netty;

import com.school.management.api.controller.ClassController;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.apache.log4j.Logger;


/**
 * Title: NettyServerFilter
 * Description: Netty 服务端过滤器
 * Version:1.0.0
 *
 * @author pancm
 * @date 2017年10月26日
 */
public class NettyServerFilter extends ChannelInitializer<SocketChannel> {

    private Logger logger = Logger.getLogger(NettyServerFilter.class);

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        ChannelPipeline pipeline = socketChannel.pipeline();
        //处理http服务的关键handler
//        pipeline.addLast("aggregator", new HttpObjectAggregator(10 * 1024 * 1024));

        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());
        // 服务端业务逻辑
        pipeline.addLast("handler", new ClassController());
    }
}
