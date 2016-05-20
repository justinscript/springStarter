/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package org.springframework.boot.context.embedded.netty;

import static com.google.common.base.Preconditions.checkNotNull;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * {@link ChannelInitializer} for {@link org.springframework.boot.context.embedded.netty.NettyEmbeddedServletContainer}.
 *
 * @author zxc Mar 1, 2016 10:31:17 AM
 */
class NettyEmbeddedServletInitializer extends ChannelInitializer<SocketChannel> {

    private final EventExecutorGroup       servletExecutor;
    private final RequestDispatcherHandler requestDispatcherHandler;
    private final NettyEmbeddedContext     servletContext;

    NettyEmbeddedServletInitializer(EventExecutorGroup servletExecutor, NettyEmbeddedContext servletContext) {
        this.servletContext = servletContext;
        this.servletExecutor = checkNotNull(servletExecutor);
        requestDispatcherHandler = new RequestDispatcherHandler(servletContext);
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        p.addLast("codec", new HttpServerCodec(4096, 8192, 8192, false));
        p.addLast(new HttpObjectAggregator(65536));
        p.addLast(new ChunkedWriteHandler());
        p.addLast("servletInput", new ServletContentHandler(servletContext));
        p.addLast(servletExecutor, "filterChain", requestDispatcherHandler);
    }
}
