package com.bingkun.weixin.qyh.service;

import com.bingkun.weixin.qyh.network.qq.weinxin.mp.aes.AesException;

import java.util.List;
import java.util.Map;

/**
 * Created by pengjikun on 2017/7/31.
 */
public interface WeixinQyhService {

    /**
     * 保存套件ticket
     * @param suiteId
     * @param suiteTicket
     */
    void saveSuiteTicket(String suiteId, String suiteTicket);

    /**
     * 取套件ticket
     * @param suiteId
     * @return
     */
    String getSuiteTicket(String suiteId);

    /**
     * 取套件令牌
     * @param suiteId
     * @return
     */
    String getSuiteAccessToken(String suiteId);

    /**
     * 更新套件令牌
     * @param suiteId
     * @param suiteAccessToken
     */
    void updateSuiteAccessToken(String suiteId, String suiteAccessToken);

    /**
     * 保存授权公众号信息
     * @param authCode
     */
    void saveCorpAuthInfo(String authCode);

    /**
     * 保存企业access_token
     * @param authCorpID
     * @param corpAccessToken
     */
    void saveCorpAccessToken(String authCorpID, String corpAccessToken);

    /**
     * 取企业access_token
     * @param authCorpID
     * @return
     */
    String getCorpAccessToken(String authCorpID);

    /**
     * 获取永久授权码
     * @param authCorpID
     * @return
     */
    String getPermanentCode(String authCorpID);

    List<Map<String, Object>> getAuthCorpList();

}
