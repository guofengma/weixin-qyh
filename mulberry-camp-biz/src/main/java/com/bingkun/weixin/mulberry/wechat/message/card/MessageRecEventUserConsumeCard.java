package com.bingkun.weixin.mulberry.wechat.message.card;


import com.bingkun.weixin.mulberry.wechat.message.MessageReceive;

/**
 * 卡券核销事件推送
 * 
 * @author pengJK 2017.3.2
 * 
 */
public class MessageRecEventUserConsumeCard extends MessageReceive {
    /**
     * 核销来源。支持开发者统计API核销（FROM_API）、
     * 公众平台核销（FROM_MP）、卡券商户助手核销（FROM_MOBILE_HELPER）（核销员微信号）
     */
    private String consumeSource;
    /**
     * outTradeNo
     */
    private String outTradeNo;
    /**
     * transId
     */
    private String transId;
    /**
     * 	门店名称，当前卡券核销的门店名称（只有通过卡券商户助手和买单核销时才会出现）
     */
    private String locationName;
    /**
     * 	核销该卡券核销员的openid（只有通过卡券商户助手核销时才会出现）
     */
    private String staffOpenId;

    public String getConsumeSource() {
        return consumeSource;
    }

    public void setConsumeSource(String consumeSource) {
        this.consumeSource = consumeSource;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getStaffOpenId() {
        return staffOpenId;
    }

    public void setStaffOpenId(String staffOpenId) {
        this.staffOpenId = staffOpenId;
    }
}
