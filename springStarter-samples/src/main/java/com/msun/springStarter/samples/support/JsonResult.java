/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.springStarter.samples.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author zxc Apr 28, 2016 11:57:42 AM
 */
public class JsonResult {

    private Boolean           status;
    private String            message;
    private Object            data;

    private static JsonResult success = new JsonResult(true, "成功");
    private static JsonResult fail    = new JsonResult(false, "失败");

    public JsonResult() {
    }

    public JsonResult(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public JsonResult(boolean status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static JsonResult success() {
        return success;
    }

    public static JsonResult fail() {
        return fail;
    }

    public static JsonResult success(String message) {
        JsonResult result = new JsonResult();
        result.setStatus(true);
        result.setMessage(message);
        return result;
    }

    public static JsonResult fail(String message) {
        JsonResult result = new JsonResult();
        result.setStatus(false);
        result.setMessage(message);
        return result;
    }

    public static JsonResult success(Object data) {
        JsonResult result = new JsonResult();
        result.setStatus(true);
        result.setData(data);
        return result;
    }

    public static JsonResult fail(Object data) {
        JsonResult result = new JsonResult();
        result.setStatus(false);
        result.setData(data);
        return result;
    }

    public Boolean getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return super.toString();
        }
    }
}
