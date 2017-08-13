package com.bingkun.weixin.qyh.controller;

import com.bingkun.weixin.qyh.constants.Constants;
import com.bingkun.weixin.qyh.network.qq.weinxin.mp.aes.WXBizMsgCrypt;
import com.bingkun.weixin.qyh.service.WeixinQyhService;
import com.bingkun.weixin.qyh.service.logic.WeixinQyhAuthLogic;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by pengjikun on 2017/7/31.
 */
@RequestMapping("/qyh")
@Controller
@Log4j
public class WeixinQyhController {
    @Autowired
    private WeixinQyhAuthLogic weixinQyhAuthLogic;
    @Autowired
    private WeixinQyhService weixinQyhService;

    /**
     * 接收套件系统事件，包括：
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

        log.info("正在接收套件系统事件请求......");
        response.setContentType(Constants.CONTENT_TYPE);
        response.getWriter().println(weixinQyhAuthLogic.receiveSysEvent(timeStamp, msgSignature, nonce, postData));
    }

    /**
     * 套件接入时验证URL
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

        log.info("正在验证套件接入请求......");
        response.setContentType(Constants.CONTENT_TYPE);
        response.getWriter().println(weixinQyhAuthLogic.verifyUrl(msgSignature, timeStamp, nonce, echoStr));
    }

    /**
     * 引导用户进入授权页
     */
    @RequestMapping(value = "/gotoAuthPage", method = RequestMethod.GET)
    public RedirectView gotoAuthPage(HttpServletRequest request)  {
        log.info("引导用户进入授权页");
        String url = weixinQyhAuthLogic.getAuthPageUrl(request);
        return new RedirectView(url);
    }

    /**
     * 授权成功后的回调链接
     * @param authCode
     * @return
     */
    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    public RedirectView callback(@RequestParam("auth_code") String authCode)  {
        log.info("授权成功，正在回调");
        weixinQyhService.saveCorpAuthInfo(authCode);
        return new RedirectView("/");
    }

    /**
     * 接入企业微信应用的用户消息验证URL
     * @param corpId  注意：验证url时corpId是第三方服务商的，真正的授权方企业发送用户消息事件时用的各自企业方的corpId
     * @param timeStamp
     * @param msgSignature
     * @param nonce
     * @param echoStr
     * @param response
     */
    @RequestMapping(value = "/corpEvent/{corpId}", method = RequestMethod.GET)
    public void checkCropEvent(@PathVariable("corpId")String corpId,
                          @RequestParam("timestamp") String timeStamp,
                          @RequestParam("msg_signature") String msgSignature,
                          @RequestParam("nonce") String nonce,
                          @RequestParam("echostr") String echoStr, HttpServletResponse response) throws Exception {
        log.info("正在验证授权企业微信应用接入请求corpId：" + corpId);
        response.setContentType(Constants.CONTENT_TYPE);
        response.getWriter().println(weixinQyhAuthLogic.verifyUrl(msgSignature, timeStamp, nonce, echoStr));
    }

    /**
     * 接入企业微信应用的用户消息事件
     * @param corpId
     * @param timeStamp
     * @param msgSignature
     * @param nonce
     * @param postData
     * @param response
     */
    @RequestMapping(value = "/corpEvent/{corpId}", method = RequestMethod.POST)
    public void receiveCropEvent(@PathVariable("corpId")String corpId,
                                 @RequestParam("timestamp") String timeStamp,
                                 @RequestParam("msg_signature") String msgSignature,
                                 @RequestParam("nonce") String nonce,
                                 @RequestBody String postData, HttpServletResponse response) throws Exception {
        log.info("正在接收企业微信应用的用户消息请求......");
        response.setContentType(Constants.CONTENT_TYPE);
        response.getWriter().println(weixinQyhAuthLogic.receiveCropEvent(corpId, msgSignature, timeStamp, nonce, postData));
    }


}
