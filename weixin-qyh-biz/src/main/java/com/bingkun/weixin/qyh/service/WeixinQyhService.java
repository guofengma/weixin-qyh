package com.bingkun.weixin.qyh.service;

import com.bingkun.weixin.qyh.qq.weixin.mp.aes.AesException;

/**
 * Created by pengjikun on 2017/7/31.
 */
public interface WeixinQyhService {

    /**
     * 接入时验证URL
     * @param msgSignature
     * @param timeStamp
     * @param nonce
     * @param echoStr
     * @return
     */
    String verifyUrl(String msgSignature, String timeStamp, String nonce, String echoStr) throws AesException;

    /**
     * 处理系统事件
     * @param timeStamp
     * @param msgSignature
     * @param nonce
     * @param postData
     */
    String receiveSysEvent(String timeStamp, String msgSignature, String nonce, String postData);
}
