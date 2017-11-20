package com.bingkun.weixin.mulberry.wechat.message;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.bingkun.weixin.mulberry.util.CommonUtil;

public class MessageReplyText extends MessageReply{
	//可以使用注释来建立和xml字段的映射
//	@XStreamAlias(value = "toUserName") 
//	private String toUserName;
//	private String fromUserName;
	private String msgType;

	private String content;
	private String funcFlag;
	
	public MessageReplyText(){
		super("text");
	}
	
	public MessageReplyText(String fromUser,String toUser,String msg){
		super("text");
		super.setFromUserName(fromUser);
		super.setToUserName(toUser);
		this.content = msg;
	}
	
	@Override
	public String getReplyXml() {
//		XStream xstream = new XStream(new DomDriver());
//		xstream.alias("xml", ImageMessageReply.class);
//		xstream.aliasField("ToUserName", ImageMessageReply.class, "toUserName");
//		xstream.aliasField("FromUserName", ImageMessageReply.class, "fromUserName");
//		xstream.aliasField("CreateTime", ImageMessageReply.class, "createTime");
//		xstream.aliasField("MsgType", ImageMessageReply.class, "messageType");
//		xstream.aliasField("ArticleCount", ImageMessageReply.class, "articleCount");
//		xstream.aliasField("Articles", ImageMessageReply.class, "articles");
//		xstream.aliasField("Title", ImageMessageReply.class, "title");
//		xstream.aliasField("Description", ImageMessageReply.class, "description");
//		xstream.aliasField("PicUrl", ImageMessageReply.class, "picUrl");
//		xstream.aliasField("Url", ImageMessageReply.class, "url");
//		String xml = xstream.toXML(bean);
		//return xml;
//		 <xml>
//		 <ToUserName><![CDATA[toUser]]></ToUserName>
//		 <FromUserName><![CDATA[fromUser]]></FromUserName>
//		 <CreateTime>12345678</CreateTime>
//		 <MsgType><![CDATA[text]]></MsgType>
//		 <Content><![CDATA[content]]></Content>
//		 <FuncFlag>0</FuncFlag>
//		 </xml>
		StringBuffer buff = new StringBuffer();
		buff.append("<xml>");
		
		buff.append("<ToUserName><![CDATA[");
		buff.append(this.getToUserName());
		buff.append("]]></ToUserName>");
		
		buff.append("<FromUserName><![CDATA[");
		buff.append(this.getFromUserName());
		buff.append("]]></FromUserName>");
		
		buff.append("<CreateTime>");
		buff.append(new Long(new Date().getTime()).toString());
		buff.append("</CreateTime>");
		
		buff.append("<MsgType><![CDATA[text]]></MsgType>");
		
		buff.append("<Content><![CDATA[");
		buff.append(content);
		buff.append("]]></Content>");
		
		buff.append("<FuncFlag>");
		buff.append(funcFlag==null||"".equals(funcFlag)?"0":funcFlag);
		buff.append("</FuncFlag>");
		
		buff.append("</xml>");
		return buff.toString();
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFuncFlag() {
		return funcFlag;
	}
	public void setFuncFlag(String funcFlag) {
		this.funcFlag = funcFlag;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	@Override
	public String getReplyJson() throws Exception {
		Map<String, Object> content = new HashMap<String, Object>();
		content.put("content", this.getContent());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("touser", this.getToUserName());
		map.put("msgtype", "text");
		map.put("text",content);
		return CommonUtil.map2Json(map);
	}

}
