package com.bingkun.weixin.mulberry.wechat.message;


/**
 * 微信公众账号推送过来的消息</br> 文本消息
 * 
 * @author dingyongchang
 * 
 */
public class MessageReceiveText extends MessageReceive{

	/**
	 * 消息正文
	 */
	private String content;
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
