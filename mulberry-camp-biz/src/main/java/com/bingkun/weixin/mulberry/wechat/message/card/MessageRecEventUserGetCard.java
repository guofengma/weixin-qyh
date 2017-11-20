package com.bingkun.weixin.mulberry.wechat.message.card;


import com.bingkun.weixin.mulberry.wechat.message.MessageReceive;

/**
 * 卡券领取事件推送
 * 
 * @author pengJK 2017.3.2
 * 
 */
public class MessageRecEventUserGetCard extends MessageReceive {
    /**
     * 是否为转赠，1代表是，0代表否。
     */
    private String isGiveByFriend;
    /**
     * 赠送方账号（一个OpenID），"IsGiveByFriend”为1时填写该参数。
     */
    private String friendUserName;

    /**
     * 转赠前的code序列号。
     */
    private String oldUserCardCode;
    /**
     * 领取场景值，用于领取渠道数据统计。可在生成二维码接口及添加JS API接口中自定义该字段的整型值。
     */
    private String outerId;

    /**
     * 领取场景值，用于领取渠道数据统计。可在生成二维码接口及添加JS API接口中自定义该字段的字符串值。
     */
    private String outerStr;

    public String getIsGiveByFriend() {
        return isGiveByFriend;
    }

    public void setIsGiveByFriend(String isGiveByFriend) {
        this.isGiveByFriend = isGiveByFriend;
    }

    public String getFriendUserName() {
        return friendUserName;
    }

    public void setFriendUserName(String friendUserName) {
        this.friendUserName = friendUserName;
    }

    public String getOldUserCardCode() {
        return oldUserCardCode;
    }

    public void setOldUserCardCode(String oldUserCardCode) {
        this.oldUserCardCode = oldUserCardCode;
    }

    public String getOuterId() {
        return outerId;
    }

    public void setOuterId(String outerId) {
        this.outerId = outerId;
    }

    public String getOuterStr() {
        return outerStr;
    }

    public void setOuterStr(String outerStr) {
        this.outerStr = outerStr;
    }
}
