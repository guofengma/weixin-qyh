package com.bingkun.weixin.mulberry.wechat.message;



/**
 * 微信公众账号推送过来的消息，抽出一个基类。</br> 文本消息、 图片消息 、地理位置消息、 链接消息、 事件推送
 * 
 * @author dingyongchang
 * 
 */
public class MessageReceive {

	//文本消息
	public final static String TYPE_TEXT = "text";
	//图片消息
	public final static String TYPE_IMAGE = "image";
	//地理位置消息
	public final static String TYPE_LOCATION = "location";
	//链接消息
	public final static String TYPE_LINK = "link";
	//视频消息
	public final static String TYPE_VIDEO = "video";
	//语音消息
	public final static String TYPE_VOICE = "voice";
	//关注事件
	public final static String TYPE_EVENT_SUBSCRIBE = "eventsubscribe";
	//取消关注事件
	public final static String TYPE_EVENT_UNSUBSCRIBE = "eventunsubscribe";
	//未关注用户扫描带参二维码事件
	public final static String TYPE_EVENT_SCANSUBSCRIBE = "eventscansubscribe";
	//已关注用户扫描带参二维码事件
	public final static String TYPE_EVENT_SCAN = "eventscan";
	//上报地理位置事件
	public final static String TYPE_EVENT_LOCATION = "eventlocation";
	//自定义菜单事件
	public final static String TYPE_EVENT_CLICK = "eventclick";
	//自定义菜单事件 链接
	public final static String TYPE_EVENT_VIEW = "eventview";
	//job运行结果通知事件
	public final static String TYPE_EVENT_JOB_RESULT = "eventjobresult";
	//门店审核结果通知事件
	public final static String TYPE_EVENT_POI_CHECK_NOTIFY = "eventpoichecknotify";
	//卡券审核通过事件
	public final static String TYPE_EVENT_CARD_PASS_CHECK = "card_pass_check";
	//卡券审核未通过事件
	public final static String TYPE_EVENT_CARD_NOT_PASS_CHECK = "card_not_pass_check";
	//卡券领取事件
	public final static String TYPE_EVENT_USER_GET_CARD = "user_get_card";
	//卡券删除事件
	public final static String TYPE_EVENT_USER_DEL_CARD = "user_del_card";
	//卡券核销事件
	public final static String TYPE_EVENT_USER_CONSUME_CARD = "user_consume_card";
	//进入会员卡事件
	public final static String TYPE_EVENT_USER_VIEW_CARD = "user_view_card";
	//从卡券进入公众号会话事件
	public final static String TYPE_EVENT_USER_ENTER_SESSION_FROM_CARD = "user_enter_session_from_card";
	//卡券库存报警事件
	public final static String TYPE_EVENT_CARD_SKU_REMIND = "card_sku_remind";


	// 可以使用注释来建立和xml字段的映射
	// @XStreamAlias(value = "toUserName")
	private String toUserName;
	private String fromUserName;
	private String msgType;
	private String createTime;
	private String event;
	private String eventKey;
	private String msgId;
	private String openId;
	private String wcaID;
	private String corpID;
	private String agentID;
	/**
	 * 卡券ID
	 */
	private String cardId;
	/**
	 * code序列号
	 */
	private String userCardCode;
	/**
	 * FWH:服务号
	 * QYH:企业号
	 */
	private String sourceType;
	//附加属性
	private String memberInfoID;
	private String brandID;
	private String cherryBrandCode;
	private String language;
	//当前所处于的功能代号（即上一次请求的功能代号，已经执行）
	private String curFunctionCode;
	//当前请求的功能代号（还未执行）
	private String reqFunctionCode;
	
	private String remark;
	private String originalXml;

	public MessageReceive() {

	}	
	/**
	 * 是否普通的文本消息
	 * @return
	 */
	public boolean isText(){
		return TYPE_TEXT.equals(msgType);
	}
	/**
	 * 是否普通的图片消息
	 * @return
	 */
	public boolean isImage(){
		return TYPE_IMAGE.equals(msgType);
	}
	/**
	 * 是否语音消息
	 * @return
	 */
	public boolean isVoice(){
		return TYPE_VOICE.equals(msgType);
	}
	/**
	 * 是否视频消息
	 * @return
	 */
	public boolean isVideo(){
		return TYPE_VIDEO.equals(msgType);
	}
	/**
	 * 是否普通的地理位置消息
	 * @return
	 */
	public boolean isLocation(){
		return TYPE_LOCATION.equals(msgType);
	}
	/**
	 * 是否普通的链接消息
	 * @return
	 */
	public boolean isLink(){
		return TYPE_LINK.equals(msgType);
	}
	
	/**
	 * 是否菜单点击事件
	 * @return
	 */
	public boolean isEventClick(){
		return TYPE_EVENT_CLICK.equals(msgType);
	}
	
	/**
	 * 是否菜单点击事件 链接
	 * @return
	 */
	public boolean isEventView(){
		return TYPE_EVENT_VIEW.equals(msgType);
	}
	
	/**
	 * 是否推送地理位置事件
	 * @return
	 */
	public boolean isEventLocation(){
		return TYPE_EVENT_LOCATION.equals(msgType);
	}
	/**
	 * 是否普通的关注事件
	 * @return
	 */
	public boolean isEventSubscribe(){
		return TYPE_EVENT_SUBSCRIBE.equals(msgType);
	}
	
	/**
	 * 是否取消关注事件
	 * @return
	 */
	public boolean isEventUnsubscribe(){
		return TYPE_EVENT_UNSUBSCRIBE.equalsIgnoreCase(msgType);
	}
	
	/**
	 * 是否未关注用户的扫描二维码事件
	 * @return
	 */
	public boolean isEventScanSubscribe(){
		return TYPE_EVENT_SCANSUBSCRIBE.equalsIgnoreCase(msgType);
	}
	/**
	 * 是否已关注用户的扫描二维码事件
	 * @return
	 */
	public boolean isEventScan(){
		return TYPE_EVENT_SCAN.equalsIgnoreCase(msgType);
	}
	
	/**
	 * 是否job结果推送事件
	 * @return
	 */
	public boolean isEventJobResult(){
		return TYPE_EVENT_JOB_RESULT.equalsIgnoreCase(msgType);
	}

	/**
	 * 是否门店审核结果推送事件
	 * @return
	 */
	public boolean isEventPoiCheckNotify(){
		return TYPE_EVENT_POI_CHECK_NOTIFY.equalsIgnoreCase(msgType);
	}

	/**
	 * 是否卡券审核通过事件
	 * @return
	 */
	public boolean isCardPassCheck(){
		return TYPE_EVENT_CARD_PASS_CHECK.equalsIgnoreCase(msgType);
	}

	/**
	 * 是否卡券审核未通过事件
	 * @return
	 */
	public boolean isCardNotPassCheck(){
		return TYPE_EVENT_CARD_NOT_PASS_CHECK.equalsIgnoreCase(msgType);
	}

	/**
	 * 是否卡券领取事件
	 * @return
	 */
	public boolean isUserGetCard(){
		return TYPE_EVENT_USER_GET_CARD.equalsIgnoreCase(msgType);
	}

	/**
	 * 是否卡券删除事件
	 * @return
	 */
	public boolean isUserDelCard(){
		return TYPE_EVENT_USER_DEL_CARD.equalsIgnoreCase(msgType);
	}

	/**
	 * 是否卡券核销事件
	 * @return
	 */
	public boolean isUserConsumeCard(){
		return TYPE_EVENT_USER_CONSUME_CARD.equalsIgnoreCase(msgType);
	}

	/**
	 * 是否进入会员卡事件
	 * @return
	 */
	public boolean isUserViewCard(){
		return TYPE_EVENT_USER_VIEW_CARD.equalsIgnoreCase(msgType);
	}

	/**
	 * 是否从卡券进入公众号会话事件
	 * @return
	 */
	public boolean isUserEnterSessionFromCard(){
		return TYPE_EVENT_USER_ENTER_SESSION_FROM_CARD.equalsIgnoreCase(msgType);
	}

	/**
	 * 是否卡券库存报警事件
	 * @return
	 */
	public boolean isCardSkuRemind(){
		return TYPE_EVENT_CARD_SKU_REMIND.equalsIgnoreCase(msgType);
	}


	
	public boolean isQYH(){
		return "QYH".equals(sourceType);
	}
	
	public boolean isFWH(){
		return "FWH".equals(sourceType);
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	public String getMemberInfoID() {
		return memberInfoID;
	}
	public void setMemberInfoID(String memberInfoID) {
		this.memberInfoID = memberInfoID;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getEventKey() {
		return eventKey;
	}
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	//TODO:逐步废除getBrandID，使用getWcaID
	@Deprecated
	public String getBrandID() {
		return brandID;
	}
	public void setBrandID(String brandID) {
		this.brandID = brandID;
	}
	public String getCherryBrandCode() {
		return cherryBrandCode;
	}
	public void setCherryBrandCode(String cherryBrandCode) {
		this.cherryBrandCode = cherryBrandCode;
	}
	public String getOpenId() {
		return fromUserName;
	}

	public String getLanguage() {
		if(null==language || "".equals(language)){
			language = "zh_CN";
		}
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getCurFunctionCode() {
		return curFunctionCode;
	}
	public void setCurFunctionCode(String curFunctionCode) {
		this.curFunctionCode = curFunctionCode;
	}
	public String getReqFunctionCode() {
		return reqFunctionCode;
	}
	public void setReqFunctionCode(String reqFunctionCode) {
		this.reqFunctionCode = reqFunctionCode;
	}

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOriginalXml() {
		return originalXml;
	}
	public void setOriginalXml(String originalXml) {
		this.originalXml = originalXml;
	}
	public String getWcaID() {
		return wcaID;
	}
	public void setWcaID(String wcaID) {
		this.wcaID = wcaID;
	}
	public String getAgentID() {
		return agentID;
	}
	public void setAgentID(String agentID) {
		this.agentID = agentID;
	}
	public String getCorpID() {
		return corpID;
	}
	public void setCorpID(String corpID) {
		this.corpID = corpID;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getUserCardCode() {
		return userCardCode;
	}

	public void setUserCardCode(String userCardCode) {
		this.userCardCode = userCardCode;
	}
}
