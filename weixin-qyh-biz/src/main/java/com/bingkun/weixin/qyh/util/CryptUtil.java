package com.bingkun.weixin.qyh.util;

import com.bingkun.weixin.qyh.constants.Constants;
import com.bingkun.weixin.qyh.qq.weixin.mp.aes.AesException;
import com.bingkun.weixin.qyh.qq.weixin.mp.aes.WXBizMsgCrypt;
import org.springframework.stereotype.Component;

/**
 * Created by pengjikun on 2016/7/31.
 */
@Component
public class CryptUtil {
    private WXBizMsgCrypt wxBizMsgCrypt;
    private WXBizMsgCrypt wxBizMsgCryptCheckUrl;

    /**
     * 初始化CryptUtil,这个类的实例将会放入spring 容器中,以实现单例模式.
     * 使用该类实例时,可使用sping提供的方法注入.
     */
    public CryptUtil(){
        try {
            wxBizMsgCryptCheckUrl = new WXBizMsgCrypt(Constants.TOKEN, Constants.ENCODEING_AESKEY, Constants.CORP_ID);
            wxBizMsgCrypt = new WXBizMsgCrypt(Constants.TOKEN, Constants.ENCODEING_AESKEY, Constants.SUITE_ID);
        } catch (AesException e) {
            throw new RuntimeException("init WXBizMsgCrypt failed", e);
        }
    }

    /**
     * 解密XML
     * @param msgSignature
     * @param timeStamp
     * @param nonce
     * @param postData
     * @return
     * @throws Exception
     */
    public String decryptMsg(String msgSignature, String timeStamp, String nonce, String postData) throws Exception {
        return wxBizMsgCrypt.decryptMsg(msgSignature, timeStamp, nonce, postData);
    }

    /**
     * 加密xml
     * @param replyMsg
     * @param timeStamp
     * @param nonce
     * @return
     * @throws AesException
     */
    public String encryptMsg(String replyMsg, String timeStamp, String nonce) throws AesException {
        return wxBizMsgCrypt.encryptMsg(replyMsg,timeStamp,nonce);
    }

    /**
     * 验证URL，用的是服务商的CorpId进行解密处理
     * @param msgSignature
     * @param timeStamp
     * @param nonce
     * @param echoStr
     * @return
     * @throws AesException
     */
    public String verifyUrl(String msgSignature, String timeStamp, String nonce, String echoStr) throws AesException {
        return wxBizMsgCryptCheckUrl.verifyUrl(msgSignature, timeStamp, nonce, echoStr);
    }




}
