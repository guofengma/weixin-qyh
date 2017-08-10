package com.bingkun.weixin.qyh.service.impl;

import com.bingkun.weixin.qyh.constants.Constants;
import com.bingkun.weixin.qyh.network.WechatQyhThirdApi;
import com.bingkun.weixin.qyh.service.RedisService;
import com.bingkun.weixin.qyh.service.WeixinQyhService;
import com.bingkun.weixin.qyh.util.CommonUtil;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        List<Map<String, Object>> authList = getAuthCorpList();
        Map<String, Object> authMap = Maps.newHashMap();
        authMap.put("AuthCorpID", authCorpID);
        authMap.put("CorpAccessToken", corpAccessToken);
        authMap.put("PermanentCode", permanentCode);
        authList.add(authMap);
        String hasListStr = CommonUtil.obj2json(authList);
        redisService.set("AuthCorpList:qyh", hasListStr);
    }

    @Override
    public void saveCorpAccessToken(String authCorpID, String corpAccessToken) {
        // TODO: 2017/8/4  要改为写入mysql,redis缓存
        List<Map<String, Object>> authList = getAuthCorpList();
        if(CollectionUtils.isEmpty(authList)){
            authList = Lists.newArrayList();
        }
        //取出已存在的，不会为空
        Map<String, Object> authMap = authList.stream().filter(map -> authCorpID.equals(CommonUtil.getString(map.get("AuthCorpID")))).
                findFirst().get();
        authMap.put("AuthCorpID", authCorpID);
        authMap.put("CorpAccessToken", corpAccessToken);
        String hasListStr = CommonUtil.obj2json(authList);
        redisService.set("AuthCorpList:qyh", hasListStr);
    }

    @Override
    public String getCorpAccessToken(String authCorpID) {
        // TODO: 2017/8/4 奖励要改为从mysql取
        List<Map<String, Object>> authList = getAuthCorpList();
        return Optional.ofNullable(authList).map(maps -> maps.stream().filter(map -> authCorpID.equals(CommonUtil.getString(map.get("AuthCorpID"))))
                .map(map -> map.get("CorpAccessToken")).findFirst().get().toString()).orElse(null);
    }

    @Override
    public String getPermanentCode(String authCorpID) {
        // TODO: 2017/8/4 将来要改为从mysql取，redis缓存
        List<Map<String, Object>> authList = getAuthCorpList();
        return Optional.ofNullable(authList).map(maps -> maps.stream().filter(map -> authCorpID.equals(CommonUtil.getString(map.get("AuthCorpID"))))
                .map(map -> map.get("PermanentCode")).findFirst().get().toString()).orElse(null);
    }

    @Override
    public List<Map<String, Object>> getAuthCorpList() {
        // TODO: 2017/8/10 将来要改成从mysql里取
        String hasListStr = redisService.get("AuthCorpList:qyh");
        List<Map<String, Object>> authList;
        if(Strings.isNullOrEmpty(hasListStr)){
            authList = Lists.newArrayList();
        }else {
            try {
                authList = CommonUtil.json2ArryList(hasListStr);
            } catch (Exception e) {
                log.error("json转化List失败，json：" + hasListStr, e);
                throw new RuntimeException("json转化List失败，json：" + hasListStr);
            }
        }
        return authList;
    }


}
