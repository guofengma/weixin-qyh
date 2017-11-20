package com.bingkun.weixin.mulberry.wechat.message;

/**
 * 微信公众账号推送过来的消息</br> 事件 
 * 未关注用户的二维码扫描事件
 * @author dingyongchang
 * 
 */
public class MessageRecEventScanSubcribe extends MessageReceive {
	private String ticket;

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
}
