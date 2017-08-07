package com.bingkun.weixin.qyh.constants;

/**
 * Created by pengjikun on 2017/8/4.
 */
public class WxUrlConstant {
    /**
     * 获取应用套件令牌
     */
    public static final String GET_SUITE_TOKEN = "https://qyapi.weixin.qq.com/cgi-bin/service/get_suite_token";
    /**
     * 获取预授权码
     */
    public static final String GET_PRE_AUTH_CODE = "https://qyapi.weixin.qq.com/cgi-bin/service/get_pre_auth_code?suite_access_token=";
    /**
     *
     */
    public static final String LOGIN_PAGE = "https://qy.weixin.qq.com/cgi-bin/loginpage?";
    /**
     * 设置授权配置
     */
    public static final String SET_SESSION_INFO = "https://qyapi.weixin.qq.com/cgi-bin/service/set_session_info?suite_access_token=";
    /**
     * 获取企业access_token
     */
    public static final String GET_CORP_TOKEN = "https://qyapi.weixin.qq.com/cgi-bin/service/get_corp_token?suite_access_token=";
    /**
     * 获取企业永久授权码和企业信息
     */
    public static final String GET_PERMANENT_CODE = "https://qyapi.weixin.qq.com/cgi-bin/service/get_permanent_code?suite_access_token=";
    /**
     * 获取企业授权信息
     */
    public static final String GET_AUTH_INFO = "https://qyapi.weixin.qq.com/cgi-bin/service/get_auth_info?suite_access_token=";
}
