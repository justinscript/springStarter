/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package org.springframework.boot.context.embedded.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * Benchmarks for {@link HttpResponseOutputStream}.
 * 
 * @author zxc Mar 1, 2016 10:34:44 AM
 */
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
public class HttpResponseOutputStreamBenchmark {

    private HttpResponseOutputStream stream;
    private byte[]                   input;

    @Param({ "1024", "2048", "4096", "8192", "16384", "32768" })
    public int                       size;

    @Setup
    public void setup() {
        HttpResponse httpResponse = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, false);
        ChannelHandlerContext ctx = new StubChannelHandlerContext();
        NettyEmbeddedContext context = new NettyEmbeddedContext("/", Thread.currentThread().getContextClassLoader(),
                                                                "Server");
        NettyHttpServletResponse servletResponse = new NettyHttpServletResponse(ctx, context, httpResponse);
        stream = new HttpResponseOutputStream(ctx, servletResponse);
        input = new byte[size];
    }

    @Benchmark
    public void writeByte() throws IOException {
        for (int i = 0; i < size; i++) {
            stream.write(0);
        }
    }

    @Benchmark
    public void writeBytes() throws IOException {
        stream.write(input);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().include(".*" + HttpResponseOutputStreamBenchmark.class.getSimpleName()
                                                           + ".*").warmupIterations(5).measurementIterations(5).forks(1).build();

        new Runner(opt).run();
    }
}
