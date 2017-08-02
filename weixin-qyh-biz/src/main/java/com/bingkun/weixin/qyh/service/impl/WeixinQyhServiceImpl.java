package com.bingkun.weixin.qyh.service.impl;

import com.bingkun.weixin.qyh.constants.Constants;
import com.bingkun.weixin.qyh.dto.weixin.sysEventDto.*;
import com.bingkun.weixin.qyh.qq.weixin.mp.aes.AesException;
import com.bingkun.weixin.qyh.service.RedisService;
import com.bingkun.weixin.qyh.service.WeixinQyhService;
import com.bingkun.weixin.qyh.util.CryptUtil;
import com.bingkun.weixin.qyh.util.XmlUtil;
import com.sun.xml.internal.bind.v2.TODO;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Created by pengjikun on 2017/7/31.
 */
@Service
@Log4j
public class WeixinQyhServiceImpl implements WeixinQyhService {
    @Autowired
    private RedisService<String, String> redisService;
    @Autowired
    private CryptUtil cryptUtil;

    @Override
    public String verifyUrl(String msgSignature, String timeStamp, String nonce, String echoStr) throws AesException {
        return cryptUtil.verifyUrl(msgSignature, timeStamp, nonce, echoStr);
    }

    @Override
    public String receiveSysEvent(String timeStamp, String msgSignature, String nonce, String postData) {
        String result = "\n timestamp: " + timeStamp + "\n msgSignature: " + msgSignature + "\n nonce: " + nonce + "\n postData: " + postData;
        log.info("正在解密...." + result);
        try {
            String xml = cryptUtil.decryptMsg(msgSignature, timeStamp, nonce, postData);
            return this.handleEvent(xml);
        } catch (Exception e) {
            log.error("解密失败", e);
            throw new RuntimeException("解密失败", e);
        }
    }

    /**
     * 处理事件
     * @param xml
     * @return
     */
    private String handleEvent(String xml){
        String infoType = xml.substring(xml.indexOf("<InfoType>")+19, xml.indexOf("]]></InfoType>"));

        if(SysEvent.SUITE_TICKET.equals(infoType)){
            SysEventTicket sysEventTicket = XmlUtil.xml2Bean(xml, SysEventTicket.class);
            // TODO: 2017/8/2 保存SuiteTicket
            redisService.set("SuiteTicket:"+sysEventTicket.getSuiteId(),
                    sysEventTicket.getSuiteTicket(), 7200);
        }else if(SysEvent.CREATE_AUTH.equals(infoType)){
            SysEventAuth sysEventAuth = XmlUtil.xml2Bean(xml, SysEventAuth.class);
            // TODO: 2017/8/2 授权成功处理
            redisService.set("AuthCode:", sysEventAuth.getAuthCode());
        }else if(SysEvent.CHANGE_AUTH.equals(infoType)){
            SysEventAuth sysEventAuth = XmlUtil.xml2Bean(xml, SysEventAuth.class);
            // TODO: 2017/8/2 变更授权处理
        }else if(SysEvent.CANCEL_AUTH.equals(infoType)){
            SysEventAuth sysEventAuth = XmlUtil.xml2Bean(xml, SysEventAuth.class);
            // TODO: 2017/8/2 取消授权处理
        }else if(SysEvent.CHANGE_CONTACT.equals(infoType)){
            String changeType = xml.substring(xml.indexOf("<ChangeType>")+21, xml.indexOf("]]></ChangeType>"));
            if(SysEvent.CREATE_USER.equals(changeType)){
                SysEventUser sysEventUser = XmlUtil.xml2Bean(xml, SysEventUser.class);
                // TODO: 2017/8/2 新增成员处理
            }else if(SysEvent.UPDATE_USER.equals(changeType)){

            }
        }

        SysEvent sysEventDto = XmlUtil.xml2Bean(xml, SysEvent.class);
        System.out.println(sysEventDto);
        return Constants.SUCCESS;
    }

    private SysEvent getBeanFromXml(String xml){
        String infoType = xml.substring(xml.indexOf("<InfoType>")+19, xml.indexOf("]]></InfoType>"));
        if(StringUtils.isEmpty(infoType)){
            throw new RuntimeException("接收系统事件InfoType为空");
        }
        if(SysEvent.SUITE_TICKET.equals(infoType)){
            return XmlUtil.xml2Bean(xml, SysEventTicket.class);
        }else if(infoType.contains(SysEvent._AUTH)){
            return XmlUtil.xml2Bean(xml, SysEventAuth.class);
        }else if(SysEvent.CHANGE_CONTACT.equals(infoType)){
            String changeType = xml.substring(xml.indexOf("<ChangeType>")+21, xml.indexOf("]]></ChangeType>"));
            if(StringUtils.isEmpty(changeType)){
                throw new RuntimeException("接收系统事件ChangeType为空");
            }
            if(changeType.contains(SysEvent._USER)){
                return XmlUtil.xml2Bean(xml, SysEventUser.class);
            }else if(changeType.contains(SysEvent._PARTY)){
                return XmlUtil.xml2Bean(xml, SysEventParty.class);
            }else if(SysEvent.UPDATE_TAG.equals(changeType)){
                return XmlUtil.xml2Bean(xml, SysEventTag.class);
            }
        }
        return null;
    }

}
