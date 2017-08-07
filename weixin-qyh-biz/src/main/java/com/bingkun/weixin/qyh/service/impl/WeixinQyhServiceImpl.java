package com.bingkun.weixin.qyh.service.impl;

import com.bingkun.weixin.qyh.constants.Constants;
import com.bingkun.weixin.qyh.network.WechatQyhThirdApi;
import com.bingkun.weixin.qyh.service.RedisService;
import com.bingkun.weixin.qyh.service.WeixinQyhService;
import com.bingkun.weixin.qyh.util.CommonUtil;
import com.google.common.base.Strings;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by pengjikun on 2017/7/31.
 */
@Service
@Log4j
public class WeixinQyhServiceImpl implements WeixinQyhService {
    @Autowired
    private RedisService<String, String> redisService;
    @Autowired
    private WechatQyhThirdApi wechatQyhThirdApi;

    @Override
    public void saveSuiteTicket(String suiteId, String suiteTicket) {
        redisService.set("SuiteTicket:" + suiteId, suiteTicket, 600);
    }

    @Override
    public String getSuiteTicket(String suiteId) {
        // TODO: 2017/8/4 要改为从mysql取
        return redisService.get("SuiteTicket:" + suiteId);
    }

    @Override
    public String getSuiteAccessToken(String suiteId) {
        //先从db中获取suiteAccessToken，如果获取不到再通过接口获取
        // TODO: 2017/8/4 要改为从mysql取，用redis缓存
        String suiteAccessToken = redisService.get("SuiteAccessToken:" + Constants.SUITE_ID);
        if(Strings.isNullOrEmpty(suiteAccessToken)){
            suiteAccessToken = wechatQyhThirdApi.getSuiteAccessTokenApi();
        }
        return suiteAccessToken;
    }

    @Override
    public void updateSuiteAccessToken(String suiteId, String suiteAccessToken) {
        // TODO: 2017/8/4 要改为写入mysql,redis缓存
        redisService.set("SuiteAccessToken:" + Constants.SUITE_ID, suiteAccessToken, 7200);
    }

    @Override
    public void saveCorpAuthInfo(String authCode) {
        Map<String, Object> map = wechatQyhThirdApi.getPermanentCode(authCode);
        String corpAccessToken = CommonUtil.getString(map.get("access_token"));
        String permanentCode = CommonUtil.getString(map.get("permanent_code"));
        Map<String, Object> corpInfo = (Map<String, Object>) map.get("auth_corp_info");
        String authCorpID = CommonUtil.getString(corpInfo.get("corpid"));
        // TODO: 2017/8/4 保存到mysql
        redisService.set("CorpAccessToken:" + authCorpID, corpAccessToken);
        redisService.set("PermanentCode:" + authCorpID, permanentCode);
    }

    @Override
    public void saveCorpAccessToken(String authCorpID, String corpAccessToken) {
        // TODO: 2017/8/4  要改为写入mysql,redis缓存
        redisService.set("CorpAccessToken:" + authCorpID, corpAccessToken);
    }

    @Override
    public String getCorpAccessToken(String authCorpID) {
        // TODO: 2017/8/4 要改为从mysql取
        return redisService.get("CorpAccessToken:" + authCorpID);
    }

    @Override
    public String getPermanentCode(String authCorpID) {
        // TODO: 2017/8/4 要改为从mysql取，redis缓存
        return redisService.get("PermanentCode:" + authCorpID);
    }



}
