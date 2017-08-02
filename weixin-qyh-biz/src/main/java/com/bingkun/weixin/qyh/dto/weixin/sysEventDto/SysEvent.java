package com.bingkun.weixin.qyh.dto.weixin.sysEventDto;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * 系统事件
 */
@Data
public class SysEvent {
    @XStreamAlias("SuiteId")
    private String SuiteId; //应用套件的SuiteId

    @XStreamAlias("InfoType")
    private String InfoType;//事件类型

    @XStreamAlias("TimeStamp")
    private String TimeStamp;//时间戳

    @XStreamAlias("ChangeType")
    private String ChangeType;//修改类型

    @XStreamAlias("AuthCorpId")
    private String AuthCorpId;//授权方的corpid

    public final static String SUITE_TICKET = "suite_ticket";//推送suite_ticket
    public final static String CREATE_AUTH = "create_auth";//授权成功通知
    public final static String CHANGE_AUTH = "change_auth";//变更授权通知
    public final static String CANCEL_AUTH = "cancel_auth";//取消授权通知
    public final static String CHANGE_CONTACT = "change_contact";//通讯录变更通知
    public final static String CREATE_USER = "create_user";//新增成员事件
    public final static String UPDATE_USER = "update_user";//更新成员事件
    public final static String DELETE_USER = "delete_user";//删除成员事件
    public final static String CREATE_PARTY = "create_party";//新增部门事件
    public final static String UPDATE_PARTY = "update_party";//更新部门事件
    public final static String DELETE_PARTY = "delete_party";//删除部门事件
    public final static String UPDATE_TAG = "update_tag";//标签变更事件

    public final static String _AUTH = "_auth";//授权相关通知
    public final static String _USER = "_user";//成员相关事件
    public final static String _PARTY = "_party";//部门相关事件

    /**
     * 是否推送suite_ticket
     * @return
     */
    public boolean isSuiteTicket(){
        return SUITE_TICKET.equals(InfoType);
    }

    /**
     * 是否授权成功通知
     * @return
     */
    public boolean isCreateAuth(){
        return CREATE_AUTH.equals(InfoType);
    }

    /**
     * 是否变更授权通知
     * @return
     */
    public boolean isChangeAuth(){
        return CHANGE_AUTH.equals(InfoType);
    }

    /**
     * 是否取消授权通知
     * @return
     */
    public boolean isCancelAuth(){
        return CANCEL_AUTH.equals(InfoType);
    }

    /**
     * 是否新增成员事件
     * @return
     */
    public boolean isCreateUser(){
        return CREATE_USER.equals(ChangeType);
    }

    /**
     * 是否更新成员事件
     * @return
     */
    public boolean isUpdateUser(){
        return UPDATE_USER.equals(ChangeType);
    }

    /**
     * 是否删除成员事件
     * @return
     */
    public boolean isDeleteUser(){
        return DELETE_USER.equals(ChangeType);
    }

    /**
     * 是否新增部门事件
     * @return
     */
    public boolean isCreateParty(){
        return CREATE_PARTY.equals(ChangeType);
    }

    /**
     * 是否更新部门事件
     * @return
     */
    public boolean isUpdateParty(){
        return UPDATE_PARTY.equals(ChangeType);
    }

    /**
     * 是否删除部门事件
     * @return
     */
    public boolean isDeleteParty(){
        return DELETE_PARTY.equals(ChangeType);
    }

    /**
     * 是否标签变更事件
     * @return
     */
    public boolean isUpdateTag(){
        return UPDATE_TAG.equals(ChangeType);
    }
}
