package com.bingkun.weixin.mulberry.wechat.message;

import com.thoughtworks.xstream.XStream;

/**
 * 微信第三方平台推送过来的授权事件相关消息
 * 
 * @author WangCT
 * @version 1.0 20160825
 */
public class MessageReceiveAuth {
	
	private String appId;
	private String createTime;
	private String infoType;
	private String authorizerAppid;
	private String authorizationCode;
	private String authorizationCodeExpiredTime;
	private String componentVerifyTicket;
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getInfoType() {
		return infoType;
	}
	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}
	public String getAuthorizerAppid() {
		return authorizerAppid;
	}
	public void setAuthorizerAppid(String authorizerAppid) {
		this.authorizerAppid = authorizerAppid;
	}
	public String getAuthorizationCode() {
		return authorizationCode;
	}
	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}
	public String getAuthorizationCodeExpiredTime() {
		return authorizationCodeExpiredTime;
	}
	public void setAuthorizationCodeExpiredTime(String authorizationCodeExpiredTime) {
		this.authorizationCodeExpiredTime = authorizationCodeExpiredTime;
	}
	public String getComponentVerifyTicket() {
		return componentVerifyTicket;
	}
	public void setComponentVerifyTicket(String componentVerifyTicket) {
		this.componentVerifyTicket = componentVerifyTicket;
	}
	
	public static MessageReceiveAuth getMessageReceiveAuth(String xml) {
		XStream xstream = new XStream();
		xstream.alias("xml", MessageReceiveAuth.class);
		xstream.aliasField("AppId", MessageReceiveAuth.class, "appId");
		xstream.aliasField("CreateTime", MessageReceiveAuth.class, "createTime");
		xstream.aliasField("InfoType", MessageReceiveAuth.class, "infoType");
		xstream.aliasField("AuthorizerAppid", MessageReceiveAuth.class, "authorizerAppid");
		xstream.aliasField("AuthorizationCode", MessageReceiveAuth.class, "authorizationCode");
		xstream.aliasField("AuthorizationCodeExpiredTime", MessageReceiveAuth.class, "authorizationCodeExpiredTime");
		xstream.aliasField("ComponentVerifyTicket", MessageReceiveAuth.class, "componentVerifyTicket");
		return (MessageReceiveAuth)xstream.fromXML(xml);
	}
	
	public static void main(String[] args) {
		try {
			String xml = "<xml><AppId>第三方平台appid</AppId><CreateTime>1413192760</CreateTime><InfoType>unauthorized</InfoType><AuthorizerAppid>公众号appid</AuthorizerAppid></xml>";
			MessageReceiveAuth messageReceiveAuth = getMessageReceiveAuth(xml);
			System.out.print(messageReceiveAuth.appId);
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
}
