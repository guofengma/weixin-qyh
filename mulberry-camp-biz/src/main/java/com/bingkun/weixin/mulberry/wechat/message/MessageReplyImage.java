package com.bingkun.weixin.mulberry.wechat.message;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.bingkun.weixin.mulberry.util.CommonUtil;

public class MessageReplyImage extends MessageReply {
	
	/**
	 * 通过上传多媒体文件，得到的id 
	 */
	private String mediaId;
	
	public MessageReplyImage(){
		super("image");
	}
	
	public MessageReplyImage(String fromUser,String toUser,String mediaId){
		super("image");
		super.setFromUserName(fromUser);
		super.setToUserName(toUser);
		this.mediaId = mediaId;
	}
	@Override
	public String getReplyXml() throws Exception {
		/* 回复的消息体格式
		<xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[fromUser]]></FromUserName>
		<CreateTime>12345678</CreateTime>
		<MsgType><![CDATA[image]]></MsgType>
		<Image>
		<MediaId><![CDATA[media_id]]></MediaId>
		</Image>
		</xml>
	*/
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
		
		buff.append("<MsgType><![CDATA[image]]></MsgType>");
		
		buff.append("<Image><MediaId><![CDATA[");
		buff.append(mediaId);
		buff.append("]]></MediaId></Image>");
		
		buff.append("</xml>");
		return buff.toString();
	}

	@Override
	public String getReplyJson() throws Exception {
		/*
		{
		    "touser":"OPENID",
		    "msgtype":"image",
		    "image":
		    {
		      "media_id":"MEDIA_ID"
		    }
		}
		*/
		Map<String, Object> content = new HashMap<String, Object>();
		content.put("media_id", this.getMediaId());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("touser", this.getToUserName());
		map.put("msgtype", "image");
		map.put("image",content);
		return CommonUtil.map2Json(map);
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

}
