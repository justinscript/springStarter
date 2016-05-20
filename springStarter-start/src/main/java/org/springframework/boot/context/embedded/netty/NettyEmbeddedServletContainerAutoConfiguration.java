/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package org.springframework.boot.context.embedded.netty;

import io.netty.bootstrap.Bootstrap;

import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration.EmbeddedServletContainerCustomizerBeanPostProcessorRegistrar;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * {@link org.springframework.boot.autoconfigure.EnableAutoConfiguration Auto-configuration} for an embedded servlet
 * containers.
 *
 * @author zxc Mar 1, 2016 10:31:47 AM
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@ConditionalOnWebApplication
@Import(EmbeddedServletContainerCustomizerBeanPostProcessorRegistrar.class)
public class NettyEmbeddedServletContainerAutoConfiguration {

    @Configuration
    @ConditionalOnClass({ Bootstrap.class })
    @ConditionalOnMissingBean(value = EmbeddedServletContainerFactory.class, search = SearchStrategy.CURRENT)
    public static class EmbeddedNetty {

        @Bean
        public NettyEmbeddedServletContainerFactory nettyEmbeddedServletContainerFactory() {
            return new NettyEmbeddedServletContainerFactory();
        }
    }
}
