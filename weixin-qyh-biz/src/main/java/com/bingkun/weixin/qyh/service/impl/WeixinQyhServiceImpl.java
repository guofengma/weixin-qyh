package com.bingkun.weixin.qyh.service.impl;

import com.bingkun.weixin.qyh.constants.Constants;
import com.bingkun.weixin.qyh.dto.AuthCorpAccessTokenDto;
import com.bingkun.weixin.qyh.network.WechatQyhThirdApi;
import com.bingkun.weixin.qyh.service.WeixinQyhService;
import com.bingkun.weixin.qyh.util.CommonUtil;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Created by pengjikun on 2017/7/31.
 */
@Service
@Log4j
public class WeixinQyhServiceImpl implements WeixinQyhService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private WechatQyhThirdApi wechatQyhThirdApi;

    @Override
    @CachePut(value = "SuiteTicketCache", key = "'SuiteTicket-' + #suiteId")
    public void saveSuiteTicket(String suiteId, String suiteTicket) {
        redisTemplate.opsForValue().set("SuiteTicket:" + suiteId, suiteTicket, 600, TimeUnit.SECONDS);
    }

    @Override
    @Cacheable(value = "SuiteTicketCache", key = "'SuiteTicket-' + #suiteId")
    public String getSuiteTicket(String suiteId) {
        // TODO: 2017/8/4 要改为从mysql取
        return  redisTemplate.opsForValue().get("SuiteTicket:" + suiteId);
    }

    @Override
    @Cacheable(value = "SuiteAccessTokenCache", key = "'SuiteAccessToken-' + #suiteId")
    public String getSuiteAccessToken(String suiteId) {
        //先从db中获取suiteAccessToken，如果获取不到再通过接口获取
        // TODO: 2017/8/4 要改为从mysql取，用redis缓存
        String suiteAccessToken =  redisTemplate.opsForValue().get("SuiteAccessToken:" + Constants.SUITE_ID);
        if(Strings.isNullOrEmpty(suiteAccessToken)){
            suiteAccessToken = wechatQyhThirdApi.getSuiteAccessTokenApi();
        }
        return suiteAccessToken;
    }

    @Override
    @CachePut(value = "SuiteAccessTokenCache", key = "'SuiteAccessToken-' + #suiteId")
    public void updateSuiteAccessToken(String suiteId, String suiteAccessToken) {
        // TODO: 2017/8/4 要改为写入mysql,redis缓存
        redisTemplate.opsForValue().set("SuiteAccessToken:" + Constants.SUITE_ID, suiteAccessToken, 7200, TimeUnit.SECONDS);
    }

    @Override
    public void saveCorpAuthInfo(String authCode) {
        Map<String, Object> map = wechatQyhThirdApi.getPermanentCode(authCode);
        String corpAccessToken = CommonUtil.getString(map.get("access_token"));
        String permanentCode = CommonUtil.getString(map.get("permanent_code"));
        Map<String, Object> corpInfo = (Map<String, Object>) map.get("auth_corp_info");
        String authCorpID = CommonUtil.getString(corpInfo.get("corpid"));
        clearPermanentCode(authCorpID);

        // TODO: 2017/8/16 保存授权企业信息和应用信息

        // TODO: 2017/8/4 保存到mysql
        AuthCorpAccessTokenDto accessTokenDto = AuthCorpAccessTokenDto.builder().authCorpID(authCorpID)
                .corpAccessToken(corpAccessToken)
                .permanentCode(permanentCode)
                .build();
        redisTemplate.opsForHash().put("AuthCorpList:qyh", authCorpID, accessTokenDto);
    }

    @Override
    @CachePut(value = "CorpAccessTokenCache", key = "'CorpAccessToken-' + #authCorpID")
    public void updateCorpAccessToken(String authCorpID, String corpAccessToken) {
        // TODO: 2017/8/4  要改为更新mysql
        AuthCorpAccessTokenDto accessTokenDto = (AuthCorpAccessTokenDto) redisTemplate.opsForHash().get("AuthCorpList:qyh", authCorpID);
        accessTokenDto.setCorpAccessToken(corpAccessToken);
        redisTemplate.opsForHash().put("AuthCorpList:qyh", authCorpID, accessTokenDto);
    }

    @Override
    @Cacheable(value = "CorpAccessTokenCache", key = "'CorpAccessToken-' + #authCorpID")
    public String getCorpAccessToken(String authCorpID) {
        // TODO: 2017/8/4 将来要改为从mysql取
        AuthCorpAccessTokenDto accessTokenDto = (AuthCorpAccessTokenDto) redisTemplate.opsForHash().get("AuthCorpList:qyh", authCorpID);

        return Optional.ofNullable(accessTokenDto)
                .map(dto -> dto.getCorpAccessToken())
                .orElse(null);
    }

    @Override
    @Cacheable(value = "PermanentCodeCache", key = "'PermanentCode-' + #authCorpID")
    public String getPermanentCode(String authCorpID) {
        // TODO: 2017/8/4 将来要改为从mysql取，redis缓存
        AuthCorpAccessTokenDto accessTokenDto = (AuthCorpAccessTokenDto) redisTemplate.opsForHash().get("AuthCorpList:qyh", authCorpID);

        return Optional.ofNullable(accessTokenDto)
                .map(dto -> dto.getPermanentCode())
                .orElseThrow(() -> new RuntimeException("获取PermanentCode为空"));
    }

    /**
     * 用于清除PermanentCode缓存
     * @param authCorpID
     */
    @CacheEvict(value = "PermanentCodeCache", key = "'PermanentCode-' + #authCorpID")
    public void clearPermanentCode(String authCorpID){
        return;
    }

    @Override
    public List<Map<String, Object>> getAuthCorpList() {
        // TODO: 2017/8/10 将来要改成从mysql里取
        String hasListStr = redisTemplate.opsForValue().get("AuthCorpList:qyh");
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
