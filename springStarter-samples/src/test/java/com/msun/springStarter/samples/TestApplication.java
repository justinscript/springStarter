/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.springStarter.samples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.*;
import org.springframework.boot.context.embedded.netty.NettyEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * @author zxc Aug 28, 2015 11:46:11 AM
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
// @SpringBootApplication
public class TestApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(TestApplication.class, args);
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {

        return new EmbeddedServletContainerCustomizer() {

            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                container.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html"),
                                        new ErrorPage(HttpStatus.NOT_FOUND, "/404.html"),
                                        new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html"));
            }
        };
    }

    // 使用内置netty驱动
    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        return new NettyEmbeddedServletContainerFactory();
    }
}
