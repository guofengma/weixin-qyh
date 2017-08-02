package com.bingkun.weixin.qyh.dto.weixin.sysEventDto;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * 标签变更事件
 */
@Data
@XStreamAlias("xml")
public class SysEventTag extends SysEvent {
    @XStreamAlias("TagId")
    private String TagId;//标签Id

    @XStreamAlias("AddUserItems")
    private String AddUserItems;//标签中新增的成员userid列表，用逗号分隔

    @XStreamAlias("DelUserItems")
    private String DelUserItems;//标签中删除的成员userid列表，用逗号分隔

    @XStreamAlias("AddPartyItems")
    private String AddPartyItems;//标签中新增的部门id列表，用逗号分隔

    @XStreamAlias("DelPartyItems")
    private String DelPartyItems;//标签中删除的部门id列表，用逗号分隔
}
