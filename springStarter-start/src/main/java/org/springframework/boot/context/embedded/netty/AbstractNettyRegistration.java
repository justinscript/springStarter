/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package org.springframework.boot.context.embedded.netty;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.*;

import javax.servlet.*;

/**
 * Abstract class for those classes implementing {@link javax.servlet.Registration} classes and {@link ServletConfig}/
 * {@link FilterConfig}.
 *
 * @author zxc Mar 1, 2016 10:33:42 AM
 */
class AbstractNettyRegistration implements Registration, Registration.Dynamic, ServletConfig, FilterConfig {

    private final String               name;
    private final String               className;
    private final NettyEmbeddedContext context;
    protected boolean                  asyncSupported;

    protected AbstractNettyRegistration(String name, String className, NettyEmbeddedContext context) {
        this.name = checkNotNull(name);
        this.className = checkNotNull(className);
        this.context = checkNotNull(context);
    }

    @Override
    public void setAsyncSupported(boolean isAsyncSupported) {
        asyncSupported = isAsyncSupported;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public boolean setInitParameter(String name, String value) {
        checkArgument(name != null, "name may not be null");
        checkArgument(value != null, "value may not be null");
        return false;
    }

    @Override
    public String getFilterName() {
        return name;
    }

    @Override
    public String getServletName() {
        return name;
    }

    @Override
    public ServletContext getServletContext() {
        return context;
    }

    protected NettyEmbeddedContext getNettyContext() {
        return context;
    }

    @Override
    public String getInitParameter(String name) {
        return null;
    }

    @Override
    public Enumeration<String> getInitParameterNames() {
        return Collections.emptyEnumeration();
    }

    @Override
    public Set<String> setInitParameters(Map<String, String> initParameters) {
        return Collections.emptySet();
    }

    @Override
    public Map<String, String> getInitParameters() {
        return Collections.emptyMap();
    }
}
