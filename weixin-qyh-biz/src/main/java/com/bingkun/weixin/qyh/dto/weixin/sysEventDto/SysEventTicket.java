package com.bingkun.weixin.qyh.dto.weixin.sysEventDto;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * 推送suite_ticket
 */
@Data
@XStreamAlias("xml")
public class SysEventTicket extends SysEvent{
    @XStreamAlias("SuiteTicket")
    private String SuiteTicket;
}
