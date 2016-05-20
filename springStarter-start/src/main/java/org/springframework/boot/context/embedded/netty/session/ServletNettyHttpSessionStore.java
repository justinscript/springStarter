/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package org.springframework.boot.context.embedded.netty.session;

import org.springframework.boot.context.embedded.netty.NettyHttpSession;

/**
 * @author zxc Mar 14, 2016 5:17:05 PM
 */
public interface ServletNettyHttpSessionStore {

    NettyHttpSession findSession(String sessionId);

    NettyHttpSession createSession();

    void destroySession(String sessionId);

    void destroyInactiveSessions();
}
