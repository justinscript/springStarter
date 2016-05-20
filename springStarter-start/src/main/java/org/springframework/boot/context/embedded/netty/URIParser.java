/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package org.springframework.boot.context.embedded.netty;

/**
 * @author zxc Mar 17, 2016 11:21:46 AM
 */
public class URIParser {

    private NettyEmbeddedContext context;

    private String               servletPath;

    private String               requestUri;

    private String               pathInfo;

    private String               queryString;

    public URIParser(NettyEmbeddedContext context) {
        this.context = context;
    }

    public void parse(String uri) {
        int indx = uri.indexOf('?');
        this.servletPath = this.context.getMatchingUrlPattern(uri);
        if (!this.servletPath.startsWith("/")) this.servletPath = "/" + this.servletPath;

        if (indx != -1) {
            this.pathInfo = uri.substring(servletPath.length(), indx);
            this.queryString = uri.substring(indx + 1);
            this.requestUri = uri.substring(0, indx);
        } else {
            this.pathInfo = uri.substring(servletPath.length());
            this.requestUri = uri;
        }

        if (this.requestUri.endsWith("/")) this.requestUri.substring(0, this.requestUri.length() - 1);

        if (this.pathInfo.equals("")) this.pathInfo = null;
        else if (!this.pathInfo.startsWith("/")) this.pathInfo = "/" + this.pathInfo;
    }

    public String getServletPath() {
        return servletPath;
    }

    public String getQueryString() {
        return queryString;
    }

    public String getPathInfo() {
        return this.pathInfo;
    }

    public String getRequestUri() {
        return requestUri;
    }
}
