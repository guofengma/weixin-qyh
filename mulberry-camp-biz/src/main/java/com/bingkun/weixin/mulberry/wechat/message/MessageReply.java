package com.bingkun.weixin.mulberry.wechat.message;


/**
 * 微信回复消息体基类
 * 
 * @author dingyongchang
 * 
 */
public abstract class MessageReply {
	
	//文本消息
	public final static String TYPE_TEXT = "text";
	//图文消息
	public final static String TYPE_NEWS = "news";
	//语音消息
	public final static String TYPE_VOICE = "voice";
	//图片消息
	public final static String TYPE_IMAGE = "image";

	//可以使用注释来建立和xml字段的映射
	//@XStreamAlias(value = "toUserName") 
	private String toUserName;
	private String fromUserName;
	private String msgType;	
	
	public MessageReply() {

	}

	public MessageReply(String messageType) {
		this.msgType = messageType;
	}

	// getter and setter
	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getMsgType() {
		return msgType;
	}
	
	/**
	 * 发送被动响应的消息时，格式要求是xml的
	 * @return
	 * @throws Exception
	 */
	public abstract String getReplyXml()throws Exception;
	/**
	 * 使用客服接口，主动回复消息时，提交的消息体是json格式的，所以要实现一个转换为json格式的方法
	 * @return
	 * @throws Exception
	 */
	public abstract String getReplyJson()throws Exception;
}
