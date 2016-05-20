/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package org.springframework.boot.context.embedded.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.http.DefaultHttpContent;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * Benchmarks for {@link HttpContentInputStream}.
 * 
 * @author zxc Mar 1, 2016 10:34:29 AM
 */
@State(Scope.Group)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class HttpContentInputStreamBenchmark {

    private HttpContentInputStream stream;
    private byte[]                 b;

    @Param("8192")
    private int                    size;

    @Setup
    public void setup() {
        stream = new HttpContentInputStream(new EmbeddedChannel());
        b = new byte[size];
    }

    @Benchmark
    @Group("handler")
    public void addContent() {
        stream.addContent(new DefaultHttpContent(Unpooled.buffer(size)));
    }

    @Benchmark
    @Group("handler")
    public int read() throws IOException {
        return stream.read(b);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().include(".*" + HttpContentInputStream.class.getSimpleName() + ".*").warmupIterations(5).measurementIterations(5).forks(0).build();

        new Runner(opt).run();
    }
}
