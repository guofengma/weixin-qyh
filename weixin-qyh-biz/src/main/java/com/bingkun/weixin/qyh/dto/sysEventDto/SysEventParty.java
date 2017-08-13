package com.bingkun.weixin.qyh.dto.sysEventDto;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * 部门相关事件（新增部门，更新部门，删除部门）
 */
@Data
@XStreamAlias("xml")
public class SysEventParty extends SysEvent {
    @XStreamAlias("Id")
    private String Id;//部门Id

    @XStreamAlias("Name")
    private String Name;//部门名称

    @XStreamAlias("ParentId")
    private String ParentId;//父部门id

    @XStreamAlias("Order")
    private String Order; //部门排序
}
