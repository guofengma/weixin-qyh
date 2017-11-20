package com.bingkun.weixin.mulberry.wechat.message.card;


import com.bingkun.weixin.mulberry.wechat.message.MessageReceive;

/**
 * 卡券审核事件推送
 * 
 * @author pengJK 2017.3.2
 * 
 */
public class MessageRecEventCardCheck extends MessageReceive {
    /**
     * card_pass_check 审核通过
     * card_not_pass_check 审核不通过
     */
    /**
     * 卡券审核失败原因
     */
    private String refuseReason;

    public String getRefuseReason() {
        return this.refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }
}
