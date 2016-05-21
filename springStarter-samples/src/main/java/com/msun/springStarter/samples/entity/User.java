/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.springStarter.samples.entity;

import java.util.Date;

/**
 * @author zxc Apr 28, 2016 12:05:36 PM
 */
public class User {

    private long   id;
    /** 数据记录创建时间 */
    private Date   createTime;
    /** 数据记录更新时间 */
    private Date   updateTime;
    /** 用户名称昵称 */
    private String name;
    /** 用户密码 */
    private String passwd;
    /** 用户权限角色 */
    private String role;
    /** 用户手机号(要唯一) */
    private String phone;
    /** 用户邮箱 */
    private String email;
    /** 用户QQ */
    private String qq;
    /** 用户身份证ID */
    private String idCard;
    /** 状态: 0=未审核,1=正常,2=停止 */
    private int    state;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
