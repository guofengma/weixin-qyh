package com.bingkun.weixin.qyh.service;

import com.bingkun.weixin.qyh.constants.Constants;
import com.bingkun.weixin.qyh.service.impl.WeixinQyhServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by pengjikun on 2017/8/13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class WeixinQyhServiceTest {
    @Autowired
    private WeixinQyhServiceImpl weixinQyhService;

    @Test
    public void saveSuiteTicket(){
        weixinQyhService.saveSuiteTicket("testKey", "testValue22");
    }

    @Test
    public void getSuiteTicket(){
        String suiteId123 = null;
        try{
            suiteId123 = weixinQyhService.getSuiteTicket("testKey");
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(suiteId123);
    }

    @Test
    public void saveCorpAuthInfo(){
        weixinQyhService.saveCorpAuthInfo("");
    }

    @Test
    public void updateCorpAccessToken(){
        weixinQyhService.updateCorpAccessToken("authCorpID", "I am CorpAccessToken");
    }

    @Test
    public void getCorpAccessToken(){
        String accessToken = weixinQyhService.getCorpAccessToken("authCorpID");
        System.out.println(accessToken);
    }

    @Test
    public void getPermanentCode(){
        String wx97abc13e7c480fa5 = weixinQyhService.getPermanentCode("wx97abc13e7c480fa5");
        System.out.println(wx97abc13e7c480fa5);
    }

    @Test
    public void clearPermanentCode(){
        weixinQyhService.clearPermanentCode("wx97abc13e7c480fa5");
    }
}
