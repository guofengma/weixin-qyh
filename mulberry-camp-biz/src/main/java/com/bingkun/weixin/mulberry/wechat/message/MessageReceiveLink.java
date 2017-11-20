package com.bingkun.weixin.mulberry.wechat.message;


/**
 * 微信公众账号推送过来的消息</br> 链接消息
 * 
 * @author dingyongchang
 * 
 */
public class MessageReceiveLink extends MessageReceive{

	
	private String title;
	private String description;
	private String url;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	

}
