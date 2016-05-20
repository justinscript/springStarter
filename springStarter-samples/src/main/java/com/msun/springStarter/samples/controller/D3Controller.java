/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.springStarter.samples.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lamfire.json.JSON;
import com.msun.springStarter.samples.support.FileHelper;
import com.msun.springStarter.samples.support.FileHelper.IDo;

/**
 * D3 JS Force-Directed Graph(受力导向图)
 * 
 * @author zxc Apr 28, 2016 11:24:29 AM
 */
@Controller
@RequestMapping("/d3")
public class D3Controller {

    private static final Logger logger = LoggerFactory.getLogger(D3Controller.class);
    private static JSON         json   = new JSON();

    @RequestMapping()
    public ModelAndView view() {
        return new ModelAndView("d3/qq");
    }

    @RequestMapping(value = "data", produces = "application/json")
    @ResponseBody
    public JSON json() {
        try {
            new FileHelper().readSourceFile("qq.json", new IDo() {

                @Override
                public void work(String line) {
                    json = JSON.fromJSONString(line);
                }
            });
        } catch (Exception e) {
            logger.error("read file error!");
        }
        return json;
    }
}
