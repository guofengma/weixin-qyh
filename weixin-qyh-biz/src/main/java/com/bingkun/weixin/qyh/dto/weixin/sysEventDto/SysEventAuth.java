package com.bingkun.weixin.qyh.dto.weixin.sysEventDto;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * 授权成功通知
 */
@Data
@XStreamAlias("xml")
public class SysEventAuth extends SysEvent{
    @XStreamAlias("AuthCode")
    private String AuthCode; //授权的auth_code,最长为512字节。用于获取企业的永久授权码
}
