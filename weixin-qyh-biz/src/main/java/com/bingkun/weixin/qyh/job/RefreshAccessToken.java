package com.bingkun.weixin.qyh.job;

import com.bingkun.weixin.qyh.network.WechatQyhThirdApi;
import com.bingkun.weixin.qyh.service.WeixinQyhService;
import com.bingkun.weixin.qyh.util.CommonUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by pengjikun on 2017/8/7.
 */
@Component
@Log4j
public class RefreshAccessToken {
    @Autowired
    private WechatQyhThirdApi wechatQyhThirdApi;
    @Autowired
    private WeixinQyhService weixinQyhService;

    /**
     * 刷新套件令牌
     * 启动后延迟1秒执行，之后每隔1小时执行一次
     */
    @Scheduled(initialDelay=1000, fixedRate=3600000)
    public void refreshSuiteAccessToken(){
        log.info("正在刷新SuiteAccessToken......");
        String suiteAccessToken = wechatQyhThirdApi.getSuiteAccessTokenApi();
        log.info(String.format("刷新结束，SuiteAccessToken为:%s", suiteAccessToken));
    }

    /**
     * 刷新授权企业令牌
     * 启动后延迟1秒执行，之后每隔1小时执行一次
     */
    @Scheduled(initialDelay=1000, fixedRate=3600000)
    public void refreshCorpAccessToken(){
        List<Map<String, Object>> authList = weixinQyhService.getAuthCorpList();
        if(!CollectionUtils.isEmpty(authList)){
            authList.forEach(map -> {
                String authCorpID = (String) map.get("AuthCorpID");
                String permanentCode = (String) map.get("PermanentCode");
                log.info(String.format("正在刷新AuthCorpID为：%s的CorpAccessToken......", authCorpID));
                String corpAccessToken = wechatQyhThirdApi.getCorpAccessTokenApi(authCorpID, permanentCode);
                log.info(String.format("刷新结束，CorpAccessToken为:%s", corpAccessToken));
            });
        }
    }
}
