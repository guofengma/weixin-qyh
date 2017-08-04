package com.bingkun.weixin.qyh.logic;

import com.bingkun.weixin.qyh.constants.Constants;
import com.bingkun.weixin.qyh.dto.weixin.sysEventDto.*;
import com.bingkun.weixin.qyh.network.WechatQyhThirdApi;
import com.bingkun.weixin.qyh.network.qq.weinxin.mp.aes.AesException;
import com.bingkun.weixin.qyh.service.WeixinQyhService;
import com.bingkun.weixin.qyh.util.CryptUtil;
import com.bingkun.weixin.qyh.util.XmlUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * Created by pengjikun on 2017/8/4.
 */
@Component
@Log4j
public class WeixinQyhAuthLogic {
    @Autowired
    private CryptUtil cryptUtil;
    @Autowired
    private WeixinQyhService weixinQyhService;
    @Autowired
    private WechatQyhThirdApi wechatQyhThirdApi;

    /**
     * 接入时验证URL
     * @param msgSignature
     * @param timeStamp
     * @param nonce
     * @param echoStr
     * @return
     */
    public String verifyUrl(String msgSignature, String timeStamp, String nonce, String echoStr) throws AesException {
        return cryptUtil.verifyUrl(msgSignature, timeStamp, nonce, echoStr);
    }

    /**
     * 接收系统事件
     * @param timeStamp
     * @param msgSignature
     * @param nonce
     * @param postData
     */
    public String receiveSysEvent(String timeStamp, String msgSignature, String nonce, String postData) {
        String result = "\n timestamp: " + timeStamp + "\n msgSignature: " + msgSignature + "\n nonce: " + nonce + "\n postData: " + postData;
        log.info("正在解密...." + result);
        try {
            String xml = cryptUtil.decryptMsg(msgSignature, timeStamp, nonce, postData);
            return this.handleSysEvent(xml);
        } catch (Exception e) {
            log.error("处理系统事件失败", e);
            throw new RuntimeException("处理系统事件失败", e);
        }
    }

    /**
     * 处理系统事件
     * @param xml
     * @return
     */
    private String handleSysEvent(String xml){
        SysEvent sysEvent = getBeanFromXml(xml);
        Objects.requireNonNull(sysEvent, "xml转化为bean返回空");
        if(sysEvent.isSuiteTicket()){
            SysEventTicket sysEventTicket = (SysEventTicket)sysEvent;
            weixinQyhService.saveSuiteTicket(sysEventTicket.getSuiteId(), sysEventTicket.getSuiteTicket());
        }else if(sysEvent.isCreateAuth()){
            // TODO: 2017/8/2 授权成功处理
            SysEventAuth sysEventAuth = (SysEventAuth)sysEvent;
            wechatQyhThirdApi.getPermanentCode(sysEventAuth.getAuthCode());
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
        Assert.notNull(infoType, "接收系统事件InfoType为空");
        if(SysEvent.SUITE_TICKET.equals(infoType)){
            return XmlUtil.xml2Bean(xml, SysEventTicket.class);
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
