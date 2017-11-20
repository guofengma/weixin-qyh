package com.bingkun.weixin.mulberry.wechat.message;


/**
 * 微信公众账号推送过来的消息</br> 图片消息
 * 
 * @author dingyongchang
 * 
 */
public class MessageReceiveImage extends MessageReceive{

	private String picUrl;
	private String mediaId;
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
}
