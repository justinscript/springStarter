/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package org.springframework.boot.context.embedded.netty;

import io.netty.handler.codec.http.*;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * Sample benchmark to check that the Gradle configuration is working.
 * 
 * @author zxc Mar 1, 2016 10:34:03 AM
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class NettyHttpServletResponseBenchmark {

    private NettyHttpServletResponse response;

    @Setup
    public void setup() {
        StubChannelHandlerContext cxt = new StubChannelHandlerContext();
        HttpResponse httpResponse = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, false);
        NettyEmbeddedContext context = new NettyEmbeddedContext("/", Thread.currentThread().getContextClassLoader(),
                                                                "Server");
        response = new NettyHttpServletResponse(cxt, context, httpResponse);
    }

    @Benchmark
    public void setContentType() {
        response.setContentType("text/html");
    }

    @SuppressWarnings("deprecation")
    @Benchmark
    public void setContentTypeHeader() {
        response.setHeader(HttpHeaders.Names.CONTENT_TYPE, "text/html");
    }

    @Benchmark
    public CharSequence getFormattedDate() {
        return response.getFormattedDate();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().include(".*" + NettyHttpServletResponseBenchmark.class.getSimpleName()
                                                           + ".*").warmupIterations(5).measurementIterations(5).forks(1).build();

        new Runner(opt).run();
    }
}
