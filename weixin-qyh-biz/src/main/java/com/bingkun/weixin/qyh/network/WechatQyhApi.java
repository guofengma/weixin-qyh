package com.bingkun.weixin.qyh.network;

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
public class WechatQyhApi {

    @Autowired
    private WeixinQyhService weixinQyhService;
    @Autowired
    WechatQyhThirdApi wechatQyhThirdApi;




    /**
     * 访问微信接口共通方法
     * @param url    接口URL
     * @param reqMap POST参数
     * @return 处理结果
     */
    public Map<String, Object> requestAuthCorpWechatAPI(String authCorpID, String url, Map<String, Object> reqMap, String method) {
        Map<String, Object> retMap = new HashMap<>();
        try {
            String corpAccessToken = weixinQyhService.getCorpAccessToken(authCorpID);
            Assert.notNull(corpAccessToken, "获取corpAccessToken为空");
            String reqUrl = url + corpAccessToken;
            String req = null;
            if (reqMap != null) {
                if ("GET".equals(method)) {
                    for (String key : reqMap.keySet()) {
                        reqUrl += "&" + key + "=" + reqMap.get(key);
                    }
                } else {
                    req = CommonUtil.map2Json(reqMap);
                }
            }
            Map<String, Object> wechatRetMap = HttpRequest.sendRequest(reqUrl, method, req);
            if (wechatRetMap != null) {
                String errcode = CommonUtil.getString(wechatRetMap.get("errcode"));
                if ("40001".equals(errcode) || "40014".equals(errcode) || "42001".equals(errcode)) {
                    log.error("corpAccessToken失效，重新获取再试一次" + " >>>url: " + url + " reqMap: " + reqMap + " errmsg: " + wechatRetMap);
                    String permanentCode = weixinQyhService.getPermanentCode(authCorpID);
                    corpAccessToken = wechatQyhThirdApi.getCorpTokenApi(authCorpID, permanentCode);
                    reqUrl = replaceUrlParam(reqUrl, "access_token", corpAccessToken);
                    wechatRetMap = HttpRequest.sendRequest(reqUrl, method, req);
                }
            }
            // 返回结果处理
            return handleResultMap(wechatRetMap);
        } catch (Exception e) {
            log.error("调用微信接口失败 >>>url: " + url + " reqMap: " + reqMap + "\n" + e.getMessage(), e);
            retMap.put("errcode", -9);
            retMap.put("errmsg", "系统繁忙");
        }
        return retMap;
    }

    /**
     * 替换URL中的参数值
     *
     * @param url   URL
     * @param name  参数名称
     * @param value 替换的参数值
     * @return 处理结果
     */
    private static String replaceUrlParam(String url, String name, String value) {
        return url.replaceAll("(" + name + "=[^&]*)", name + "=" + value);
    }

    /**
     * 共通处理访问微信接口返回结果
     * @param wechatRetMap 微信接口返回结果map
     * @return
     */
    public static Map<String, Object> handleResultMap(Map<String, Object> wechatRetMap) {
        Assert.notNull(wechatRetMap, "调用微信接口返回结果为空");
        String errcode = CommonUtil.getString(wechatRetMap.get("errcode"));
        String errmsg = CommonUtil.getString(wechatRetMap.get("errmsg"));
        Assert.isTrue(!"".equals(errcode) || !"0".equals(errcode), String.format("调用微信接口异常errcode:%s，errmsg:%s", errcode, errmsg));
        return wechatRetMap;
    }

}
