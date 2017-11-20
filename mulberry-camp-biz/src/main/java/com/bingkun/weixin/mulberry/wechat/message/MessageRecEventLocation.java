package com.bingkun.weixin.mulberry.wechat.message;

/**
 * 微信公众账号推送过来的消息</br> 事件 上报地理位置
 * 
 * @author dingyongchang
 * 
 */
public class MessageRecEventLocation extends MessageReceive {
	// 地理位置经度
	private String longitude;
	// 地理位置纬度
	private String latitude;
	// 地理位置精度
	private String precision;
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getPrecision() {
		return precision;
	}
	public void setPrecision(String precision) {
		this.precision = precision;
	}



}
