/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.springStarter.samples.support;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.lamfire.json.JSON;
import com.lamfire.json.JSONArray;

/**
 * @author zxc Apr 28, 2016 10:30:24 AM
 */
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

    @Value("${userSet}")
    private String userSet;

    @Bean
    protected UsernamePasswordAuthenticationExtendFilter captchaFilter() throws Exception {
        UsernamePasswordAuthenticationExtendFilter filter = new UsernamePasswordAuthenticationExtendFilter();
        filter.setAuthenticationManager(authenticationManager());
        filter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
        filter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler("/login"));
        filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/login?error"));
        filter.setUsernameParameter("username");
        filter.setPasswordParameter("password");
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()//
        .addFilterBefore(captchaFilter(), UsernamePasswordAuthenticationFilter.class)//
        .authorizeRequests().anyRequest().fullyAuthenticated()//
        .and().formLogin().defaultSuccessUrl("/d3").loginPage("/login").failureUrl("/login?error").permitAll()//
        .and().logout().deleteCookies("JSESSIONID").permitAll()//
        .and().rememberMe().key("93a63e5e-8cd7-452c-856e-477b08f1223e").tokenValiditySeconds(2592000)//
        .and().exceptionHandling().accessDeniedPage("/login?error")//
        .and().sessionManagement().maximumSessions(1).expiredUrl("/expired");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/verify/code", "/register", "/js/**", "/css/**", "/images/**",
                                   "/**/favicon.ico");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        JSONArray userArray = JSONArray.fromJSONString(userSet);
        for (Object json : userArray.asList()) {
            JSON user = (JSON) json;
            auth.inMemoryAuthentication()//
            .withUser(user.getString("name"))//
            .password(user.getString("passwd"))//
            .roles(user.getJSONArray("role").toArray(new String[] {}));
        }
    }
}
