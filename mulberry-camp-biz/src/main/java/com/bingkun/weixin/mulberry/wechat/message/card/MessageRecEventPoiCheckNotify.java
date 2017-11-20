package com.bingkun.weixin.mulberry.wechat.message.card;


import com.bingkun.weixin.mulberry.wechat.message.MessageReceive;

/**
 * 门店审核事件推送
 * Created by pekon on 2017/2/23.
 */
public class MessageRecEventPoiCheckNotify extends MessageReceive {
    /*商户自己内部ID，即字段中的sid*/
    private String uniqId;
    /*微信的门店ID，微信内门店唯一标示ID*/
    private String poiId;
    /*审核结果，成功succ 或失败fail*/
    private String result;
    /*成功的通知信息，或审核失败的驳回理由*/
    private String msg;

    public String getUniqId() {
        return uniqId;
    }

    public void setUniqId(String uniqId) {
        this.uniqId = uniqId;
    }

    public String getPoiId() {
        return poiId;
    }

    public void setPoiId(String poiId) {
        this.poiId = poiId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
