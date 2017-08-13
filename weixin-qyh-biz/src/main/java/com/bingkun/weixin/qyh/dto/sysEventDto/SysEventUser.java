package com.bingkun.weixin.qyh.dto.sysEventDto;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.util.List;

/**
 * 成员相关事件（新增成员，更新成员，删除成员）
 */
@Data
@XStreamAlias("xml")
public class SysEventUser extends SysEvent {
    @XStreamAlias("UserID")
    private String UserID; //成员UserID

    @XStreamAlias("Name")
    private String Name;//成员名称，变更时推送

    @XStreamAlias("Department")
    private String Department;//成员部门列表

    @XStreamAlias("Mobile")
    private String Mobile;//手机号码，仅通讯录套件可获取

    @XStreamAlias("Position")
    private String Position;//职位信息。长度为0~64个字节

    @XStreamAlias("Gender")
    private String Gender; //性别。1表示男性，2表示女性

    @XStreamAlias("Email")
    private String Email;//邮箱，仅通讯录套件可获取

    @XStreamAlias("Avatar")
    private String Avatar;//头像

    @XStreamAlias("EnglishName")
    private String EnglishName;//英文名

    @XStreamAlias("IsLeader")
    private String IsLeader;//上级字段，标识是否为上级。0表示普通成员，1表示上级

    @XStreamAlias("Telephone")
    private String Telephone;//座机，仅通讯录套件可获取

    @XStreamAlias("Status")
    private String Status;//激活状态：1=已激活 2=已禁用

    @XStreamAlias("ExtAttr")
    private List<UserExtAttrItem> ExtAttr;//扩展属性，仅通讯录套件可获取
}
