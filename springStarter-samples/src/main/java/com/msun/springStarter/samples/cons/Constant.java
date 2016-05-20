/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.springStarter.samples.cons;

/**
 * @author zxc Apr 28, 2016 11:46:14 AM
 */
public interface Constant {

    // page size
    public final static int    PAGE_SIZE                         = 30;

    // 导航
    public final static String NAV                               = "nav";

    // Session Key
    public final static String SESSION_REGISTER_CODE       = "SESSION_REGISTER_PHONE_CODE";
    public final static String SESSION_RESET_PWD_CODE      = "SESSION_RESET_PASSWORD_PHONE_CODE";

    // 短信验证码过期时间（毫秒）
    public final static long   PHONE_CODE_TIMEOUT                = 60000;
}
