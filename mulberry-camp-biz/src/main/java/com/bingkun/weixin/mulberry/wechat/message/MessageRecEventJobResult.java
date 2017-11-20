package com.bingkun.weixin.mulberry.wechat.message;

/**
 * 微信服务器推送过来的异步任务执行结果
 * 目前就企业号有这个功能
 * @author dingyongchang
 *
 */
public class MessageRecEventJobResult extends MessageReceive {

	private String jobId;
	private String jobType;
	private String errCode;
	private String errMsg;
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
}
