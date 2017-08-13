package com.bingkun.weixin.qyh.service;

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
    private WeixinQyhService weixinQyhService;

    @Test
    public void saveSuiteTicket(){
        weixinQyhService.saveSuiteTicket("suiteId123", "test123");
    }

    @Test
    public void getSuiteTicket(){
        String suiteId123 = weixinQyhService.getSuiteTicket("suiteId123");
        System.out.println(suiteId123);
    }
}