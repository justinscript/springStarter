/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package org.springframework.boot.context.embedded.netty;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import javax.servlet.*;

/**
 * A {@link org.springframework.boot.context.embedded.netty.NettyEmbeddedContext}
 * {@link javax.servlet.ServletRegistration}.
 *
 * @author zxc Mar 1, 2016 10:28:58 AM
 */
class NettyServletRegistration extends AbstractNettyRegistration implements ServletRegistration.Dynamic {

    private volatile boolean initialised;
    private Servlet          servlet;

    NettyServletRegistration(NettyEmbeddedContext context, String servletName, String className, Servlet servlet) {
        super(servletName, className, context);
        this.servlet = servlet;
    }

    public Servlet getServlet() throws ServletException {
        if (!initialised) {
            synchronized (this) {
                if (!initialised) {
                    if (null == servlet) {
                        try {
                            servlet = (Servlet) Class.forName(getClassName()).newInstance();
                        } catch (Exception e) {
                            throw new ServletException(e);
                        }
                    }
                    servlet.init(this);
                    initialised = true;
                }
            }
        }
        return servlet;
    }

    @Override
    public void setLoadOnStartup(int loadOnStartup) {

    }

    @Override
    public Set<String> setServletSecurity(ServletSecurityElement constraint) {
        return null;
    }

    @Override
    public void setMultipartConfig(MultipartConfigElement multipartConfig) {

    }

    @Override
    public void setRunAsRole(String roleName) {

    }

    @Override
    public Set<String> addMapping(String... urlPatterns) {
        // TODO check for conflicts

        NettyEmbeddedContext context = getNettyContext();
        for (String urlPattern : urlPatterns) {
            context.addServletMapping(urlPattern, getName());
        }
        return Collections.emptySet();
    }

    @Override
    public Collection<String> getMappings() {
        return null;
    }

    @Override
    public String getRunAsRole() {
        return null;
    }
}
