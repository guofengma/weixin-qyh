package com.bingkun.weixin.mulberry.wechat.message;

import com.bingkun.weixin.mulberry.wechat.message.card.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;
import com.thoughtworks.xstream.mapper.MapperWrapper;

public class MessageFactory {
	private static Logger logger = LoggerFactory.getLogger(MessageFactory.class);
	public static MessageReceive getMessageReceive(String xml) {
		XStream xstream = new XStream(new DomDriver()) {
			protected MapperWrapper wrapMapper(MapperWrapper next) {
				return new MapperWrapper(next) {
					public boolean shouldSerializeMember(Class definedIn, String fieldName) {
						try {
							return definedIn != Object.class || realClass(fieldName) != null;
						} catch (CannotResolveClassException cnrce) {
							return false;
						}
					}
				};
			}
		};
		
		//String tmp = xml.replaceAll("<!\\[CDATA\\[.*?]]>", "");
		String msgType = xml.substring(xml.indexOf("<MsgType>")+18,xml.indexOf("]]></MsgType>"));
		
		MessageReceive messageReceive = null;
		if("text".equalsIgnoreCase(msgType)){
			//文本消息
			xstream.alias("xml", MessageReceiveText.class);
			xstream.aliasField("ToUserName", MessageReceiveText.class, "toUserName");
			xstream.aliasField("FromUserName", MessageReceiveText.class, "fromUserName");
			xstream.aliasField("CreateTime", MessageReceiveText.class, "createTime");
			xstream.aliasField("MsgId", MessageReceiveText.class, "msgId");
			xstream.aliasField("Content", MessageReceiveText.class, "content");
			messageReceive = (MessageReceiveText) xstream.fromXML(xml);
			messageReceive.setMsgType(MessageReceive.TYPE_TEXT);
		}else if("image".equalsIgnoreCase(msgType)){
			//图片消息
			xstream.alias("xml", MessageReceiveImage.class);
			xstream.aliasField("ToUserName", MessageReceiveImage.class, "toUserName");
			xstream.aliasField("FromUserName", MessageReceiveImage.class, "fromUserName");
			xstream.aliasField("CreateTime", MessageReceiveImage.class, "createTime");
			xstream.aliasField("MsgId", MessageReceiveImage.class, "msgId");
			xstream.aliasField("PicUrl", MessageReceiveImage.class, "picUrl");
			xstream.aliasField("MediaId", MessageReceiveImage.class, "mediaId");
			messageReceive = (MessageReceiveImage) xstream.fromXML(xml);
			messageReceive.setMsgType(MessageReceive.TYPE_IMAGE);
		}else if("link".equalsIgnoreCase(msgType)){
			//链接消息
			xstream.alias("xml", MessageReceiveLink.class);
			xstream.aliasField("ToUserName", MessageReceiveLink.class, "toUserName");
			xstream.aliasField("FromUserName", MessageReceiveLink.class, "fromUserName");
			xstream.aliasField("CreateTime", MessageReceiveLink.class, "createTime");
			xstream.aliasField("MsgId", MessageReceiveLink.class, "msgId");
			xstream.aliasField("Title", MessageReceiveLink.class, "title");
			xstream.aliasField("Description", MessageReceiveLink.class, "description");
			xstream.aliasField("Url", MessageReceiveLink.class, "url");
			messageReceive = (MessageReceiveLink) xstream.fromXML(xml);
			messageReceive.setMsgType(MessageReceive.TYPE_LINK);
		}else if("voice".equalsIgnoreCase(msgType)){
			//语音消息
			xstream.alias("xml", MessageReceiveVoice.class);
			xstream.aliasField("ToUserName", MessageReceiveVoice.class, "toUserName");
			xstream.aliasField("FromUserName", MessageReceiveVoice.class, "fromUserName");
			xstream.aliasField("CreateTime", MessageReceiveVoice.class, "createTime");
			xstream.aliasField("MsgId", MessageReceiveVoice.class, "msgId");
			xstream.aliasField("MediaId", MessageReceiveVoice.class, "mediaId");
			xstream.aliasField("Format", MessageReceiveVoice.class, "format");
			xstream.aliasField("Recognition", MessageReceiveVoice.class, "recognition");
			messageReceive = (MessageReceiveVoice) xstream.fromXML(xml);
			messageReceive.setMsgType(MessageReceive.TYPE_VOICE);
		}else if("video".equalsIgnoreCase(msgType)){
			//视频消息
			xstream.alias("xml", MessageReceiveVideo.class);
			xstream.aliasField("ToUserName", MessageReceiveVideo.class, "toUserName");
			xstream.aliasField("FromUserName", MessageReceiveVideo.class, "fromUserName");
			xstream.aliasField("CreateTime", MessageReceiveVideo.class, "createTime");
			xstream.aliasField("MsgId", MessageReceiveVideo.class, "msgId");
			xstream.aliasField("MediaId", MessageReceiveVideo.class, "mediaId");
			xstream.aliasField("ThumbMediaId", MessageReceiveVideo.class, "thumbMediaId");
			messageReceive = (MessageReceiveVideo) xstream.fromXML(xml);
			messageReceive.setMsgType(MessageReceive.TYPE_VOICE);
		}else if("location".equalsIgnoreCase(msgType)){
			//地理位置消息
			xstream.alias("xml", MessageReceiveLocation.class);
			xstream.aliasField("ToUserName", MessageReceiveLocation.class, "toUserName");
			xstream.aliasField("FromUserName", MessageReceiveLocation.class, "fromUserName");
			xstream.aliasField("CreateTime", MessageReceiveLocation.class, "createTime");
			xstream.aliasField("MsgId", MessageReceiveLocation.class, "msgId");
			xstream.aliasField("Location_X", MessageReceiveLocation.class, "location_X");
			xstream.aliasField("Location_Y", MessageReceiveLocation.class, "location_Y");
			xstream.aliasField("Scale", MessageReceiveLocation.class, "scale");
			xstream.aliasField("Label", MessageReceiveLocation.class, "label");
			messageReceive = (MessageReceiveLocation) xstream.fromXML(xml);
			messageReceive.setMsgType(MessageReceive.TYPE_LOCATION);
			
		}else if("event".equals(msgType)){
			//事件
			String eventType = xml.substring(xml.indexOf("<Event>")+16,xml.indexOf("]]></Event>"));
			if(eventType.equalsIgnoreCase("CLICK")){
				//自定义菜单点击事件
				xstream.alias("xml", MessageRecEventClick.class);
				xstream.aliasField("ToUserName", MessageRecEventClick.class, "toUserName");
				xstream.aliasField("FromUserName", MessageRecEventClick.class, "fromUserName");
				xstream.aliasField("CreateTime", MessageRecEventClick.class, "createTime");
				xstream.aliasField("Event", MessageRecEventClick.class, "event");
				xstream.aliasField("EventKey", MessageRecEventClick.class, "eventKey");
				messageReceive = (MessageRecEventClick) xstream.fromXML(xml);
				messageReceive.setMsgType(MessageReceive.TYPE_EVENT_CLICK);
			}else if(eventType.equalsIgnoreCase("VIEW")){
				//自定义菜单点击事件 链接跳转
				xstream.alias("xml", MessageRecEventClick.class);
				xstream.aliasField("ToUserName", MessageRecEventClick.class, "toUserName");
				xstream.aliasField("FromUserName", MessageRecEventClick.class, "fromUserName");
				xstream.aliasField("CreateTime", MessageRecEventClick.class, "createTime");
				xstream.aliasField("Event", MessageRecEventClick.class, "event");
				xstream.aliasField("EventKey", MessageRecEventClick.class, "eventKey");
				messageReceive = (MessageRecEventClick) xstream.fromXML(xml);
				messageReceive.setMsgType(MessageReceive.TYPE_EVENT_VIEW);
			}else if(eventType.equalsIgnoreCase("LOCATION")){
				//上报地理位置
				xstream.alias("xml", MessageRecEventLocation.class);
				xstream.aliasField("ToUserName", MessageRecEventLocation.class, "toUserName");
				xstream.aliasField("FromUserName", MessageRecEventLocation.class, "fromUserName");
				xstream.aliasField("CreateTime", MessageRecEventLocation.class, "createTime");
				xstream.aliasField("Event", MessageRecEventLocation.class, "event");
				xstream.aliasField("Longitude", MessageRecEventLocation.class, "longitude");
				xstream.aliasField("Latitude", MessageRecEventLocation.class, "latitude");
				xstream.aliasField("Precision", MessageRecEventLocation.class, "precision");
				messageReceive = (MessageRecEventLocation) xstream.fromXML(xml);
				messageReceive.setMsgType(MessageReceive.TYPE_EVENT_LOCATION);
			}else if(eventType.equalsIgnoreCase("scan")){
				//已关注用户的二维码扫描事件
				xstream.alias("xml", MessageRecEventScan.class);
				xstream.aliasField("ToUserName", MessageRecEventScan.class, "toUserName");
				xstream.aliasField("FromUserName", MessageRecEventScan.class, "fromUserName");
				xstream.aliasField("CreateTime", MessageRecEventScan.class, "createTime");
				xstream.aliasField("Event", MessageRecEventScan.class, "event");
				xstream.aliasField("EventKey", MessageRecEventScan.class, "eventKey");
				xstream.aliasField("Ticket", MessageRecEventScan.class, "ticket");
				messageReceive = (MessageRecEventScan) xstream.fromXML(xml);
				messageReceive.setMsgType(MessageReceive.TYPE_EVENT_SCAN);
			}else if(eventType.equalsIgnoreCase("unsubscribe")){
				//用户取消关注事件
				xstream.alias("xml", MessageRecEventUnsubcribe.class);
				xstream.aliasField("ToUserName", MessageRecEventUnsubcribe.class, "toUserName");
				xstream.aliasField("FromUserName", MessageRecEventUnsubcribe.class, "fromUserName");
				xstream.aliasField("CreateTime", MessageRecEventUnsubcribe.class, "createTime");
				xstream.aliasField("Event", MessageRecEventUnsubcribe.class, "event");
				messageReceive = (MessageRecEventUnsubcribe) xstream.fromXML(xml);
				messageReceive.setMsgType(MessageReceive.TYPE_EVENT_UNSUBSCRIBE);
			}else if(eventType.equalsIgnoreCase("subscribe")){
				if(xml.indexOf("]]></Ticket>")>-1){
					//未关注用户扫描二维码事件
					xstream.alias("xml", MessageRecEventScanSubcribe.class);
					xstream.aliasField("ToUserName", MessageRecEventScanSubcribe.class, "toUserName");
					xstream.aliasField("FromUserName", MessageRecEventScanSubcribe.class, "fromUserName");
					xstream.aliasField("CreateTime", MessageRecEventScanSubcribe.class, "createTime");
					xstream.aliasField("Event", MessageRecEventScanSubcribe.class, "event");
					xstream.aliasField("EventKey", MessageRecEventScanSubcribe.class, "eventKey");
					xstream.aliasField("Ticket", MessageRecEventScanSubcribe.class, "ticket");
					messageReceive = (MessageRecEventScanSubcribe) xstream.fromXML(xml);
					messageReceive.setMsgType(MessageReceive.TYPE_EVENT_SCANSUBSCRIBE);
				}else{
					//普通的用户订阅事件(在微信里搜索公众号进行关注)
					xstream.alias("xml", MessageRecEventSubcribe.class);
					xstream.aliasField("ToUserName", MessageRecEventSubcribe.class, "toUserName");
					xstream.aliasField("FromUserName", MessageRecEventSubcribe.class, "fromUserName");
					xstream.aliasField("CreateTime", MessageRecEventSubcribe.class, "createTime");
					xstream.aliasField("Event", MessageRecEventSubcribe.class, "event");
					messageReceive = (MessageRecEventSubcribe) xstream.fromXML(xml);
					messageReceive.setMsgType(MessageReceive.TYPE_EVENT_SUBSCRIBE);
				}
				
			}else if (eventType.equalsIgnoreCase("poi_check_notify")){
				//门店审核结果通知事件
				xstream.alias("xml", MessageRecEventPoiCheckNotify.class);
				xstream.aliasField("ToUserName", MessageRecEventPoiCheckNotify.class, "toUserName");
				xstream.aliasField("FromUserName", MessageRecEventPoiCheckNotify.class, "fromUserName");
				xstream.aliasField("CreateTime", MessageRecEventPoiCheckNotify.class, "createTime");
				xstream.aliasField("Event", MessageRecEventPoiCheckNotify.class, "event");
				xstream.aliasField("UniqId", MessageRecEventPoiCheckNotify.class, "uniqId");
				xstream.aliasField("PoiId", MessageRecEventPoiCheckNotify.class, "poiId");
				xstream.aliasField("Result", MessageRecEventPoiCheckNotify.class, "result");
				xstream.aliasField("Msg", MessageRecEventPoiCheckNotify.class, "msg");
				messageReceive = (MessageRecEventPoiCheckNotify) xstream.fromXML(xml);
				messageReceive.setMsgType(MessageReceive.TYPE_EVENT_POI_CHECK_NOTIFY);
			}else if(MessageReceive.TYPE_EVENT_CARD_PASS_CHECK.equalsIgnoreCase(eventType)){
				//卡券审核通过事件
				xstream.alias("xml", MessageRecEventCardCheck.class);
				xstream.aliasField("ToUserName", MessageRecEventCardCheck.class, "toUserName");
				xstream.aliasField("FromUserName", MessageRecEventCardCheck.class, "fromUserName");
				xstream.aliasField("CreateTime", MessageRecEventCardCheck.class, "createTime");
				xstream.aliasField("Event", MessageRecEventCardCheck.class, "event");
				xstream.aliasField("CardId", MessageRecEventCardCheck.class, "cardId");
				xstream.aliasField("RefuseReason", MessageRecEventCardCheck.class, "refuseReason");
				messageReceive = (MessageRecEventCardCheck) xstream.fromXML(xml);
				messageReceive.setMsgType(MessageReceive.TYPE_EVENT_CARD_PASS_CHECK);
			}else if(MessageReceive.TYPE_EVENT_CARD_NOT_PASS_CHECK.equalsIgnoreCase(eventType)){
				//卡券审核不通过事件
				xstream.alias("xml", MessageRecEventCardCheck.class);
				xstream.aliasField("ToUserName", MessageRecEventCardCheck.class, "toUserName");
				xstream.aliasField("FromUserName", MessageRecEventCardCheck.class, "fromUserName");
				xstream.aliasField("CreateTime", MessageRecEventCardCheck.class, "createTime");
				xstream.aliasField("Event", MessageRecEventCardCheck.class, "event");
				xstream.aliasField("CardId", MessageRecEventCardCheck.class, "cardId");
				xstream.aliasField("RefuseReason", MessageRecEventCardCheck.class, "refuseReason");
				messageReceive = (MessageRecEventCardCheck) xstream.fromXML(xml);
				messageReceive.setMsgType(MessageReceive.TYPE_EVENT_CARD_NOT_PASS_CHECK);
			}else if(MessageReceive.TYPE_EVENT_USER_GET_CARD.equalsIgnoreCase(eventType)){
				//用户领取卡券事件
				xstream.alias("xml", MessageRecEventUserGetCard.class);
				xstream.aliasField("ToUserName", MessageRecEventUserGetCard.class, "toUserName");
				xstream.aliasField("FromUserName", MessageRecEventUserGetCard.class, "fromUserName");
				xstream.aliasField("CreateTime", MessageRecEventUserGetCard.class, "createTime");
				xstream.aliasField("Event", MessageRecEventUserGetCard.class, "event");
				xstream.aliasField("CardId", MessageRecEventUserGetCard.class, "cardId");
				xstream.aliasField("UserCardCode", MessageRecEventUserGetCard.class, "userCardCode");
				xstream.aliasField("FriendUserName", MessageRecEventUserGetCard.class, "friendUserName");
				xstream.aliasField("IsGiveByFriend", MessageRecEventUserGetCard.class, "isGiveByFriend");
				xstream.aliasField("OldUserCardCode", MessageRecEventUserGetCard.class, "oldUserCardCode");
				xstream.aliasField("OuterId", MessageRecEventUserGetCard.class, "outerId");
				xstream.aliasField("OuterStr", MessageRecEventUserGetCard.class, "outerStr");
				messageReceive = (MessageRecEventUserGetCard) xstream.fromXML(xml);
				messageReceive.setMsgType(MessageReceive.TYPE_EVENT_USER_GET_CARD);
			}else if(MessageReceive.TYPE_EVENT_USER_DEL_CARD.equalsIgnoreCase(eventType)){
				//用户删除卡券事件
				xstream.alias("xml", MessageRecEventUserDelCard.class);
				xstream.aliasField("ToUserName", MessageRecEventUserDelCard.class, "toUserName");
				xstream.aliasField("FromUserName", MessageRecEventUserDelCard.class, "fromUserName");
				xstream.aliasField("CreateTime", MessageRecEventUserDelCard.class, "createTime");
				xstream.aliasField("Event", MessageRecEventUserDelCard.class, "event");
				xstream.aliasField("CardId", MessageRecEventUserDelCard.class, "cardId");
				xstream.aliasField("UserCardCode", MessageRecEventUserDelCard.class, "userCardCode");
				messageReceive = (MessageRecEventUserDelCard) xstream.fromXML(xml);
				messageReceive.setMsgType(MessageReceive.TYPE_EVENT_USER_DEL_CARD);
			}else if(MessageReceive.TYPE_EVENT_USER_CONSUME_CARD.equalsIgnoreCase(eventType)){
				//用户核销卡券事件
				xstream.alias("xml", MessageRecEventUserConsumeCard.class);
				xstream.aliasField("ToUserName", MessageRecEventUserConsumeCard.class, "toUserName");
				xstream.aliasField("FromUserName", MessageRecEventUserConsumeCard.class, "fromUserName");
				xstream.aliasField("CreateTime", MessageRecEventUserConsumeCard.class, "createTime");
				xstream.aliasField("Event", MessageRecEventUserConsumeCard.class, "event");
				xstream.aliasField("CardId", MessageRecEventUserConsumeCard.class, "cardId");
				xstream.aliasField("UserCardCode", MessageRecEventUserConsumeCard.class, "userCardCode");
				xstream.aliasField("ConsumeSource", MessageRecEventUserConsumeCard.class, "consumeSource");
				xstream.aliasField("OutTradeNo", MessageRecEventUserConsumeCard.class, "outTradeNo");
				xstream.aliasField("TransId", MessageRecEventUserConsumeCard.class, "transId");
				xstream.aliasField("LocationName", MessageRecEventUserConsumeCard.class, "locationName");
				xstream.aliasField("StaffOpenId", MessageRecEventUserConsumeCard.class, "staffOpenId");
				messageReceive = (MessageRecEventUserConsumeCard) xstream.fromXML(xml);
				messageReceive.setMsgType(MessageReceive.TYPE_EVENT_USER_CONSUME_CARD);
			}else if(MessageReceive.TYPE_EVENT_USER_VIEW_CARD.equalsIgnoreCase(eventType)){
				//用户进入会员卡事件
				xstream.alias("xml", MessageRecEventUserViewCard.class);
				xstream.aliasField("ToUserName", MessageRecEventUserViewCard.class, "toUserName");
				xstream.aliasField("FromUserName", MessageRecEventUserViewCard.class, "fromUserName");
				xstream.aliasField("CreateTime", MessageRecEventUserViewCard.class, "createTime");
				xstream.aliasField("Event", MessageRecEventUserViewCard.class, "event");
				xstream.aliasField("CardId", MessageRecEventUserViewCard.class, "cardId");
				xstream.aliasField("UserCardCode", MessageRecEventUserViewCard.class, "userCardCode");
				messageReceive = (MessageRecEventUserViewCard) xstream.fromXML(xml);
				messageReceive.setMsgType(MessageReceive.TYPE_EVENT_USER_VIEW_CARD);
			}else if(MessageReceive.TYPE_EVENT_USER_ENTER_SESSION_FROM_CARD.equalsIgnoreCase(eventType)){
				//用户从卡券进入公众号会话事件
				xstream.alias("xml", MessageRecEventUserEnterFromCard.class);
				xstream.aliasField("ToUserName", MessageRecEventUserEnterFromCard.class, "toUserName");
				xstream.aliasField("FromUserName", MessageRecEventUserEnterFromCard.class, "fromUserName");
				xstream.aliasField("CreateTime", MessageRecEventUserEnterFromCard.class, "createTime");
				xstream.aliasField("Event", MessageRecEventUserEnterFromCard.class, "event");
				xstream.aliasField("CardId", MessageRecEventUserEnterFromCard.class, "cardId");
				xstream.aliasField("UserCardCode", MessageRecEventUserEnterFromCard.class, "userCardCode");
				messageReceive = (MessageRecEventUserEnterFromCard) xstream.fromXML(xml);
				messageReceive.setMsgType(MessageReceive.TYPE_EVENT_USER_ENTER_SESSION_FROM_CARD);
			}else if(MessageReceive.TYPE_EVENT_CARD_SKU_REMIND.equalsIgnoreCase(eventType)){
				//卡券库存报警事件
				xstream.alias("xml", MessageRecEventCardSkuRemind.class);
				xstream.aliasField("ToUserName", MessageRecEventCardSkuRemind.class, "toUserName");
				xstream.aliasField("FromUserName", MessageRecEventCardSkuRemind.class, "fromUserName");
				xstream.aliasField("CreateTime", MessageRecEventCardSkuRemind.class, "createTime");
				xstream.aliasField("Event", MessageRecEventCardSkuRemind.class, "event");
				xstream.aliasField("CardId", MessageRecEventCardSkuRemind.class, "cardId");
				xstream.aliasField("Detail", MessageRecEventCardSkuRemind.class, "detail");
				messageReceive = (MessageRecEventCardSkuRemind) xstream.fromXML(xml);
				messageReceive.setMsgType(MessageReceive.TYPE_EVENT_CARD_SKU_REMIND);
			}
			
		}
		messageReceive.setSourceType("FWH");
		messageReceive.setOriginalXml(xml);
		logger.info(messageReceive.getFromUserName()+" "+messageReceive.getMsgType());
		return messageReceive;
	}
}
