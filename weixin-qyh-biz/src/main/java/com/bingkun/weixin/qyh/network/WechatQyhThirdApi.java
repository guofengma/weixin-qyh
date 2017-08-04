package com.bingkun.weixin.qyh.network;

import com.bingkun.weixin.qyh.constants.Constants;
import com.bingkun.weixin.qyh.constants.WxUrlConstant;
import com.bingkun.weixin.qyh.network.httpRequest.HttpRequest;
import com.bingkun.weixin.qyh.service.WeixinQyhService;
import com.bingkun.weixin.qyh.util.CommonUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

@Component
@Log4j
public class WechatQyhThirdApi {
    @Autowired
    private WeixinQyhService weixinQyhService;

    /**
     * 调用接口获取SuiteAccessToken，用于刷新
     * @return
     */
    public String getSuiteAccessTokenApi() {
        try {
            String suiteTicket = weixinQyhService.getSuiteTicket(Constants.SUITE_ID);
            String reqUrl = WxUrlConstant.GET_SUITE_TOKEN;
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("suite_id", Constants.SUITE_ID);
            reqMap.put("suite_secret", Constants.SUITE_SECRET);
            reqMap.put("suite_ticket", suiteTicket);
            String req = CommonUtil.map2Json(reqMap);
            Map<String, Object> retMap = HttpRequest.sendRequest(reqUrl, "POST", req);
            if (retMap != null) {
                String errcode = CommonUtil.getString(retMap.get("errcode"));
                if ("".equals(errcode) || "0".equals(errcode)) {
                    log.info("获取SuiteAccessToken成功: " + retMap + " >>>url: " + reqUrl + " reqMap: " + reqMap);
                    String suiteAccessToken = (String) retMap.get("suite_access_token");
                    weixinQyhService.updateSuiteAccessToken(Constants.SUITE_ID, suiteAccessToken);
                    return suiteAccessToken;
                } else {
                    log.error("获取SuiteAccessToken失败: " + retMap + " >>>url: " + reqUrl + " reqMap: " + reqMap);
                }
            } else {
                log.error("获取SuiteAccessToken失败: 返回为空" + " >>>url: " + reqUrl + " reqMap: " + reqMap);
            }
        } catch (Exception e) {
            log.error("获取SuiteAccessToken失败: ", e);
        }
        return null;
    }

    /**
     * 获取预授权码
     * @return 预授权码
     */
    public String getPreAuthCode() {
        String reqUrl = WxUrlConstant.GET_PRE_AUTH_CODE;
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("suite_id", Constants.SUITE_ID);
        Map<String, Object> retMap = requestWechatThirdAPI(reqUrl, reqMap);
        String errcode = CommonUtil.getString(retMap.get("errcode"));
        String preAuthCode = null;
        if ("".equals(errcode) || "0".equals(errcode)) {
            preAuthCode = (String) retMap.get("pre_auth_code");
        } else {
            log.error("获取预授权码失败");
        }
        return preAuthCode;
    }

    /**
     * 用临时授权码换取公众号的接口调用凭据和授权信息
     * @param authCode 临时授权码
     * @return 公众号的接口调用凭据和授权信息
     */
    public Map<String, Object> getPermanentCode(String authCode) {
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("suite_id", Constants.SUITE_ID);
        reqMap.put("auth_code", authCode);
        Map<String, Object> retMap = requestWechatThirdAPI(WxUrlConstant.GET_PERMANENT_CODE, reqMap);
        String errcode = CommonUtil.getString(retMap.get("errcode"));
        if ("".equals(errcode) || "0".equals(errcode)) {
            return retMap;
        }
        return null;
    }

    /**
     * 获取授权方的获取企业授权信息
     * @param authCorpID
     * @return
     */
    public Map<String, Object> getCorpAuthInfo(String authCorpID, String permanentCode) {
        String reqUrl = WxUrlConstant.GET_AUTH_INFO;
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("suite_id", Constants.SUITE_ID);
        reqMap.put("auth_corpid", authCorpID);
        reqMap.put("permanent_code", permanentCode);
        Map<String, Object> retMap = requestWechatThirdAPI(reqUrl, reqMap);
        String errcode = CommonUtil.getString(retMap.get("errcode"));
        if ("".equals(errcode) || "0".equals(errcode)) {
            return retMap;
        }
        return null;
    }

    /**
     * 获取企业access_token
     * @param authCorpID
     * @return permanentCode
     */
    public String getCorpTokenApi(String authCorpID, String permanentCode) {
        try {
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("suite_id", Constants.SUITE_ID);
            reqMap.put("auth_corpid", authCorpID);
            reqMap.put("permanent_code", permanentCode);
            String reqUrl = WxUrlConstant.GET_CORP_TOKEN;
            Map<String, Object> retMap = requestWechatThirdAPI(reqUrl, reqMap);
            if (retMap != null) {
                String errcode = CommonUtil.getString(retMap.get("errcode"));
                if ("".equals(errcode) || "0".equals(errcode)) {
                    log.info("获取CorpAccessToken成功: " + retMap + " >>>url: " + reqUrl + " reqMap: " + reqMap + " authCorpID: " + authCorpID);
                    String corpAccessToken = (String) retMap.get("access_token");
                    weixinQyhService.saveCorpAccessToken(authCorpID, corpAccessToken);
                    return corpAccessToken;
                } else {
                    log.error("获取CorpAccessToken失败: " + retMap + " >>>url: " + reqUrl + " reqMap: " + reqMap + " authAppID: " + authCorpID);
                }
            } else {
                log.error("获取CorpAccessToken失败: 返回为空" + " >>>url: " + reqUrl + " reqMap: " + reqMap + " authAppID: " + authCorpID);
            }
        } catch (Exception e) {
            log.error("获取CorpAccessToken失败: " + e.getMessage() + " authAppID: " + authCorpID, e);
        }
        return null;
    }


    /**
     * 访问微信第三方接口共通方法
     * @param url    接口URL
     * @param reqMap POST参数
     * @return 处理结果
     */
    private Map<String, Object> requestWechatThirdAPI(String url, Map<String, Object> reqMap) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        try {
            String suiteAccessToken = weixinQyhService.getSuiteAccessToken(Constants.SUITE_ID);
            Assert.notNull(suiteAccessToken, "获取suiteAccessToken为空");
            String reqUrl = url + suiteAccessToken;
            String req = CommonUtil.map2Json(reqMap);
            Map<String, Object> wechatRetMap = HttpRequest.sendRequest(reqUrl, "POST", req);
            if (wechatRetMap != null) {
                String errcode = CommonUtil.getString(wechatRetMap.get("errcode"));
                if ("40001".equals(errcode) || "40014".equals(errcode) || "42001".equals(errcode)) {
                    log.error("suiteAccessToken失效，重新获取再试一次" + " >>>url: " + url + " reqMap: " + reqMap + " errmsg: " + wechatRetMap);
                    suiteAccessToken = getSuiteAccessTokenApi();
                    reqUrl = url + suiteAccessToken;
                    wechatRetMap = HttpRequest.sendRequest(reqUrl, "POST", req);
                }
            }
            return WechatQyhApi.handleResultMap(wechatRetMap);
        } catch (Exception e) {
            log.error("调用微信接口失败: " + " >>>url: " + url + " reqMap: " + reqMap + " errmsg: " + e.getMessage(), e);
            retMap.put("errcode", -9);
            retMap.put("errmsg", "系统繁忙");
        }
        return retMap;
    }

}
