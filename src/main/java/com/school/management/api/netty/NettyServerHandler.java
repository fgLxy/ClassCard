package com.school.management.api.netty;

import com.school.management.api.results.ResultCode;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Title: NettyServerHandler.
 * Description: 服务端业务逻辑.
 *
 * @author pancm
 * Version:1.0.0.
 * @date 2017年10月26日
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private static final AtomicInteger RESPONSE = new AtomicInteger();
    private static final Logger logger = Logger.getLogger(NettyServerHandler.class);

    protected ChannelHandlerContext ctx;
    protected static final List<ChannelHandlerContext> ctxList = new LinkedList<>();

    protected static final Map<Object, Object> nettyMap = new LinkedHashMap<>();

    protected static final Map<Object, ChannelId> CODE_CHANNELID = new LinkedHashMap<>();

    public static final List<String> PHONENUM = new LinkedList<>();

    protected static final Map<String, Object> CLASSCODE_IP = new HashMap<>();

    /**
     * 用于在传送课程信息时，方便获取终端IP
     */
    protected static final Set<String> IpList = new HashSet<>();

    /**
     * IP地址—班级课程
     */
    protected static final Map<String, Object> IP_SCHEDULE = new HashMap<>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ctx.writeAndFlush(Unpooled.copiedBuffer(msg.toString(), CharsetUtil.UTF_8));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        this.ctx = ctx;
        ctxList.add(ctx);
        NettyChannelHandlerPool.channelGroup.add(ctx.channel());
        ctx.writeAndFlush(Unpooled.copiedBuffer(ResultCode.SUCCESS.toString(), CharsetUtil.UTF_8));
        logger.info(ctx.channel().remoteAddress() + "\t：已连接；通道ID：" + ctx.channel().id());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        String ip = ctx.channel().remoteAddress().toString();
        nettyMap.remove(ip.substring(1, ip.indexOf(":")));
        ChannelGroups.discard(ctx.channel());
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error(cause.getMessage(), cause);
        ChannelGroups.discard(ctx.channel());
        RESPONSE.decrementAndGet();
    }

    /**
     * 收到信息后，群发给所有小伙伴
     * 服务端因编码器问题   只能以HTTP形式发送给客户端
     * StringDecoder疑似没有生效，问题留待解决
     */
    protected void sendAll(ChannelHandlerContext ctx, Object sendMSG) {
        try {
            ByteBuf buf = Unpooled.copiedBuffer(sendMSG.toString(), CharsetUtil.UTF_8);
            NettyChannelHandlerPool.channelGroup.writeAndFlush(buf);
        } catch (Exception e) {
            ctx.channel().closeFuture();
            e.printStackTrace();
            logger.error("发送异常", e);
        }
    }

}
