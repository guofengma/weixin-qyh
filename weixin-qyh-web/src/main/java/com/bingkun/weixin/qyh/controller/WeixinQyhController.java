package com.bingkun.weixin.qyh.controller;

import com.bingkun.weixin.qyh.service.WeixinQyhService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by pengjikun on 2017/7/31.
 */
@RequestMapping("/qyh")
@Controller
@Log4j
public class WeixinQyhController {
    @Autowired
    private WeixinQyhService weixinQyhService;

    /**
     * 接收微信的系统事件，包括：
     *      1，接收 suite_ticket, 微信的服务器会定时（10分钟一次）给"系统事件接收URL"发送suite_ticket
     *      2，授权成功
     *      3，更新授权
     *      4，取消授权
     *      5. 通讯录变更事件通知（新增成员事件，更新成员事件，删除成员事件，新增部门事件，更新部门事件，删除部门事件，标签变更事件）
     * @param timeStamp      时间戳
     * @param msgSignature   消息体签名
     * @param nonce          随机数
     * @param postData       postData
     */
    @RequestMapping(value = "/suiteEvent", method = RequestMethod.POST)
    public void receiveSysEvent(@RequestParam("timestamp") String timeStamp,
                                @RequestParam("msg_signature") String msgSignature,
                                @RequestParam("nonce") String nonce,
                                @RequestBody String postData, HttpServletResponse response) throws Exception {

        log.info("正在接受微信服务器推送系统事件......");
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println(weixinQyhService.receiveSysEvent(timeStamp, msgSignature, nonce, postData));
    }

    /**
     * 接入时验证URL
     * @param timeStamp
     * @param msgSignature
     * @param nonce
     * @param echoStr
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/suiteEvent", method = RequestMethod.GET)
    public void checkSysEvent(@RequestParam("timestamp") String timeStamp,
                              @RequestParam("msg_signature") String msgSignature,
                              @RequestParam("nonce") String nonce,
                              @RequestParam("echostr") String echoStr, HttpServletResponse response) throws Exception {

        log.info("正在接收验证请求......");
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println(weixinQyhService.verifyUrl(msgSignature, timeStamp, nonce, echoStr));
    }

}
