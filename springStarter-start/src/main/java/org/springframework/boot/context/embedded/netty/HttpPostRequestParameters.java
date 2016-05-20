/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package org.springframework.boot.context.embedded.netty;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * @author zxc Mar 15, 2016 11:25:32 AM
 */
public class HttpPostRequestParameters {

    private final HttpRequest               request;
    private final ByteBuf                   content;

    private final Map<String, List<String>> params  = Maps.newHashMap();
    private byte[]                          body;
    private boolean                         _parsed = false;

    public HttpPostRequestParameters(HttpRequest request, ByteBuf content) {
        this.request = request;
        this.content = content;
    }

    byte[] getHttpRequestBodyAsBytes() {
        if (body != null) {
            return body;
        }
        if (content == null) {
            return null;
        }

        content.readerIndex(0);
        int len = content.readableBytes();
        body = new byte[len];
        content.readBytes(body);

        return body;
    }

    Map<String, List<String>> getHttpRequestParameters() {
        if (_parsed) {
            return params;
        }

        byte[] body = getHttpRequestBodyAsBytes();
        if (body == null) {
            _parsed = true;
            return params;
        }

        QueryStringDecoder decoder = new QueryStringDecoder("?" + new String(body));
        params.putAll(decoder.parameters());
        _parsed = true;
        return params;
    }
}
