package com.bingkun.weixin.mulberry.wechat.message.card;


import com.bingkun.weixin.mulberry.wechat.message.MessageReceive;

/**
 * 进入会员卡事件推送
 * 
 * @author pengJK 2017.3.2
 * 
 */
public class MessageRecEventCardSkuRemind extends MessageReceive {
    /**
     * 报警详细信息
     */
    private String detail;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
