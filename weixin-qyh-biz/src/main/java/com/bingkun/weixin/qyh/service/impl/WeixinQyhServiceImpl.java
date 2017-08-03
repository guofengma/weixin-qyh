package com.bingkun.weixin.qyh.service.impl;

import com.bingkun.weixin.qyh.constants.Constants;
import com.bingkun.weixin.qyh.dto.weixin.sysEventDto.*;
import com.bingkun.weixin.qyh.qq.weixin.mp.aes.AesException;
import com.bingkun.weixin.qyh.service.RedisService;
import com.bingkun.weixin.qyh.service.WeixinQyhService;
import com.bingkun.weixin.qyh.util.CryptUtil;
import com.bingkun.weixin.qyh.util.XmlUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Objects;
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
            log.error("处理系统事件失败", e);
            throw new RuntimeException("处理系统事件失败", e);
        }
    }

    /**
     * 处理事件
     * @param xml
     * @return
     */
    private String handleEvent(String xml){
        SysEvent sysEvent = getBeanFromXml(xml);
        Objects.requireNonNull(sysEvent, "xml转化为bean返回空");
        if(sysEvent.isSuiteTicket()){
            SysEventTicket sysEventTicket = (SysEventTicket)sysEvent;
            // TODO: 2017/8/2 保存SuiteTicket
            redisService.set("SuiteTicket:"+sysEventTicket.getSuiteId(),
                    sysEventTicket.getSuiteTicket(), 7200);
        }else if(sysEvent.isCreateAuth()){
            // TODO: 2017/8/2 授权成功处理
        }else if(sysEvent.isChangeAuth()){
            // TODO: 2017/8/2 变更授权处理
        }else if(sysEvent.isCancelAuth()){
            // TODO: 2017/8/2 取消授权处理
        }else if(sysEvent.isCreateUser()){
            // TODO: 2017/8/2 新增成员处理
        }else if(sysEvent.isUpdateUser()){
            // TODO: 2017/8/2 更新成员处理
        }else if(sysEvent.isDeleteUser()){
            // TODO: 2017/8/2 删除成员处理
        }else if(sysEvent.isCreateParty()){
            // TODO: 2017/8/2 新增部门处理
        }else if(sysEvent.isUpdateParty()){
            // TODO: 2017/8/2 更新部门处理
        }else if(sysEvent.isDeleteParty()){
            // TODO: 2017/8/2 删除部门处理
        }else if(sysEvent.isUpdateTag()){
            // TODO: 2017/8/2 标签变更事件
        }
        return Constants.SUCCESS;
    }

    /**
     * xml转化为对应的bean
     * @param xml
     * @return
     */
    private SysEvent getBeanFromXml(String xml){
        String infoType = xml.substring(xml.indexOf("<InfoType>")+19, xml.indexOf("]]></InfoType>"));
        Objects.requireNonNull(infoType, "接收系统事件InfoType为空");
        if(SysEvent.SUITE_TICKET.equals(infoType)){
            SysEventTicket sysEventTicket = XmlUtil.xml2Bean(xml);
            return sysEventTicket;
        }else if(infoType.contains(SysEvent._AUTH)){
            return XmlUtil.xml2Bean(xml, SysEventAuth.class);
        }else if(SysEvent.CHANGE_CONTACT.equals(infoType)){
            String changeType = xml.substring(xml.indexOf("<ChangeType>")+21, xml.indexOf("]]></ChangeType>"));
            Objects.requireNonNull(changeType, "接收系统事件ChangeType为空");
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
