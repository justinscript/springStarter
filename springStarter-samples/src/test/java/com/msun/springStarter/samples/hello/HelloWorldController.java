/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.springStarter.samples.hello;

import java.io.IOException;
import java.util.concurrent.Callable;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zxc Mar 1, 2016 5:55:50 PM
 */
@Controller
public class HelloWorldController {

    private static final String           MESSAGE          = "Hello, World!你好!";
    private static final Callable<String> MESSAGE_CALLABLE = new Callable<String>() {

                                                               @Override
                                                               public String call() throws Exception {
                                                                   return MESSAGE;
                                                               }
                                                           };

    @RequestMapping(value = "/", produces = "application/json")
    @ResponseBody
    public Message index() {
        return new Message("now time:" + System.currentTimeMillis());
    }

    @RequestMapping(value = "/plaintext", produces = "text/plain")
    @ResponseBody
    public String plaintext() {
        return MESSAGE;
    }

    @RequestMapping(value = "/async", produces = "text/plain")
    @ResponseBody
    public Callable<String> async() {
        return MESSAGE_CALLABLE;
    }

    @RequestMapping(value = "/json", produces = "application/json")
    @ResponseBody
    public Message json() {
        return new Message(MESSAGE);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(HttpServletRequest request) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        int total = 0;
        while (true) {
            byte[] bytes = new byte[8192];
            int read = inputStream.read(bytes);
            if (read == -1) {
                break;
            }
            total += read;
        }
        return "Total bytes received: " + total;
    }

    @RequestMapping("/sleepy")
    @ResponseBody
    public String sleepy() throws InterruptedException {
        int millis = 500;
        Thread.sleep(millis);
        return "Yawn! I slept for " + millis + "ms";
    }

    private static class Message {

        private final String message;

        public Message(String message) {
            this.message = message;
        }

        @SuppressWarnings("unused")
        public String getMessage() {
            return message;
        }
    }

    @SuppressWarnings("serial")
    private class NullHttpServlet extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        }
    }

    @Bean
    public ServletRegistrationBean nullServletRegistration() {
        return new ServletRegistrationBean(new NullHttpServlet(), "/null");
    }
}
