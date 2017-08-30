package com.bingkun.weixin.qyh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

import java.io.Serializable;

/**
 * Created by pengjikun on 2017/8/16.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthCorpAccessTokenDto implements Serializable{
    /**
     * 授权企业ID
     */
    private String authCorpID;
    /**
     * 授权企业凭证
     */
    private String corpAccessToken;
    /**
     * 授权企业永久授权码
     */
    private String permanentCode;
}
