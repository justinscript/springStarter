/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package org.springframework.boot.context.embedded.netty;

import io.netty.channel.Channel;

/**
 * @author zxc Mar 14, 2016 5:28:33 PM
 */
public class ChannelThreadLocal {

    public static final ThreadLocal<Channel> channelThreadLocal = new ThreadLocal<Channel>();

    public static void set(Channel channel) {
        channelThreadLocal.set(channel);
    }

    public static void unset() {
        channelThreadLocal.remove();
    }

    public static Channel get() {
        return channelThreadLocal.get();
    }
}
