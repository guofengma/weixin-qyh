package com.bingkun.weixin.mulberry.wechat.message;


/**
 * 微信公众账号推送过来的消息</br> 视频消息
 * 
 * @author dingyongchang
 * 
 */
public class MessageReceiveVideo extends MessageReceive{

	
	private String mediaId;
	private String thumbMediaId;
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getThumbMediaId() {
		return thumbMediaId;
	}
	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}
	
}
