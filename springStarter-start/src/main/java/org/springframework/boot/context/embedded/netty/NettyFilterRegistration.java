/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package org.springframework.boot.context.embedded.netty;

import java.util.Collection;
import java.util.EnumSet;

import javax.servlet.*;

/**
 * A {@link org.springframework.boot.context.embedded.netty.NettyEmbeddedContext}
 * {@link javax.servlet.FilterRegistration}.
 *
 * @author zxc Mar 1, 2016 10:31:03 AM
 */
class NettyFilterRegistration extends AbstractNettyRegistration implements FilterRegistration.Dynamic {

    private volatile boolean initialised;
    private Filter           filter;

    NettyFilterRegistration(NettyEmbeddedContext context, String filterName, String className, Filter filter) {
        super(filterName, className, context);
        this.filter = filter;
    }

    public Filter getFilter() throws ServletException {
        if (!initialised) {
            synchronized (this) {
                if (!initialised) {
                    if (null == filter) {
                        try {
                            filter = (Filter) Class.forName(getClassName()).newInstance();
                        } catch (Exception e) {
                            throw new ServletException(e);
                        }
                    }
                    filter.init(this);
                    initialised = true;
                }
            }
        }
        return filter;
    }

    @Override
    public void addMappingForServletNames(EnumSet<DispatcherType> dispatcherTypes, boolean isMatchAfter,
                                          String... servletNames) {

    }

    @Override
    public Collection<String> getServletNameMappings() {
        return null;
    }

    @Override
    public void addMappingForUrlPatterns(EnumSet<DispatcherType> dispatcherTypes, boolean isMatchAfter,
                                         String... urlPatterns) {
        NettyEmbeddedContext context = getNettyContext();
        for (String urlPattern : urlPatterns) {
            context.addFilterMapping(dispatcherTypes, isMatchAfter, urlPattern);
        }
    }

    @Override
    public Collection<String> getUrlPatternMappings() {
        return null;
    }
}
