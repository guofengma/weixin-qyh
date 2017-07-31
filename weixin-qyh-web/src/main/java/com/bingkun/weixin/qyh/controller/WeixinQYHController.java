package com.bingkun.weixin.qyh.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by pengjikun on 2017/7/31.
 */
@RequestMapping("/qyh")
@Controller
@Log4j
public class WeixinQYHController {

    @RequestMapping("/event")
    @ResponseBody
    public String event() {
        return "Hello world!";
    }

}
