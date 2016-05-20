/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.springStarter.samples.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.msun.springStarter.samples.cons.Constant;
import com.msun.springStarter.samples.entity.User;
import com.msun.springStarter.samples.support.JsonResult;

/**
 * 登录注册
 * 
 * @author zxc Apr 27, 2016 3:51:36 PM
 */
@Controller
public class LoginController implements Constant {

    @RequestMapping(value = { "/", "/index" }, produces = "application/json")
    @ResponseBody
    public String index(HttpServletRequest request) {
        return "hello,now time " + System.currentTimeMillis();
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(HttpServletRequest request) {
        if (request.getRemoteUser() != null) {
            return new ModelAndView(new RedirectView("/d3", true, false));
        }
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult verifyPhone(String phone, HttpSession session) throws IOException {
        User user = getUserByName();// userMapper.getUserByPhone(phone);
        // TODO:query user by db
        if (user != null) return JsonResult.fail("手机号已被注册");
        String code = "";// smsHelper.sendCode(phone);
        // TODO:send code to phone
        session.setAttribute(SESSION_REGISTER_CODE,
                             HashBasedTable.create().put(phone, code, System.currentTimeMillis()));
        return JsonResult.success();
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
        return "register";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(String phone, String phoneCode, String passwd, HttpSession session) {
        Table<String, String, Long> code = (HashBasedTable<String, String, Long>) session.getAttribute(SESSION_REGISTER_CODE);
        session.removeAttribute(SESSION_REGISTER_CODE);
        if (code == null || code.row(phone) == null || !code.row(phone).containsKey(phoneCode)
            || System.currentTimeMillis() - code.row(phone).get(phoneCode) > PHONE_CODE_TIMEOUT) {
            return "redirect:/register?error=phonecode";
        }
        // TODO: add user
        return "redirect:/";
    }

    @RequestMapping(value = "/resetpwd", method = RequestMethod.GET)
    public String resetPasswoed() {
        return "resetpwd";
    }

    @RequestMapping(value = "/resetpwd", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult resetpwd(String username, HttpSession session) throws IOException {
        User user = getUserByName();// userMapper.getUserByName(username);
        // TODO:query user by db
        if (user == null) return JsonResult.fail("用户不存在");
        String code = "";// smsHelper.sendCode(user.getPhone());
        // TODO:send code to phone
        session.setAttribute(SESSION_RESET_PWD_CODE,
                             HashBasedTable.create().put(user.getPhone(), code, System.currentTimeMillis()));
        return JsonResult.success();
    }

    private User getUserByName() {
        // TODO:query user by db
        return null;
    }

    @Bean
    public ServletRegistrationBean validateCodeServletRegistration() {
        return new ServletRegistrationBean(new ValidateCodeServlet(), "/verify/code");
    }
}
