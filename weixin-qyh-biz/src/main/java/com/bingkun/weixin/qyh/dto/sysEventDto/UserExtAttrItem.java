package com.bingkun.weixin.qyh.dto.sysEventDto;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.experimental.Builder;

@Data
@XStreamAlias("Item")
public class UserExtAttrItem {
    @XStreamAlias("Name")
    private String Name;
    @XStreamAlias("Value")
    private String Value;

}
