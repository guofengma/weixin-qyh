package com.bingkun.weixin.mulberry.wechat.message;


/**
 * 微信公众账号推送过来的消息</br> 语音消息
 * 
 * @author dingyongchang
 * 
 */
public class MessageReceiveVoice extends MessageReceive{

	
	private String mediaId;
	private String format;
	//语音识别结果
	private String recognition;
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getRecognition() {
		return recognition;
	}
	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}
	
	
}
