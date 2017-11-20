package com.bingkun.weixin.mulberry.wechat.message;

import java.util.Date;

public class MessageReplyCustomer extends MessageReply {
	
	
	public MessageReplyCustomer(String fromUser,String toUser){
		super.setFromUserName(fromUser);
		super.setToUserName(toUser);
	}

	@Override
	public String getReplyXml() throws Exception {
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
		
		buff.append("<MsgType><![CDATA[transfer_customer_service]]></MsgType>");
		
		buff.append("</xml>");
		return buff.toString();
	}

	@Override
	public String getReplyJson() throws Exception {
		return null;
	}

}
