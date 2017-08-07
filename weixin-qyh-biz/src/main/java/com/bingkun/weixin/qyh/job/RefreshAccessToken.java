package com.bingkun.weixin.qyh.job;

import com.bingkun.weixin.qyh.network.WechatQyhThirdApi;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by pengjikun on 2017/8/7.
 */
@Component
@Log4j
public class RefreshAccessToken {
    @Autowired
    private WechatQyhThirdApi wechatQyhThirdApi;

    /**
     * 刷新
     */
    @Scheduled(initialDelay=1000, fixedRate=3600000)
    public void refreshSuiteAccessToken(){
        log.info("正在刷新SuiteAccessToken......");
        String suiteAccessToken = wechatQyhThirdApi.getSuiteAccessTokenApi();
        log.info(String.format("已完成刷新，SuiteAccessToken为:%s", suiteAccessToken));
    }
}
