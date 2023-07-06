package com.hp.dingtalk.constant;


import com.hp.common.base.annotations.FieldDesc;
import lombok.Data;

/**
 * 钉钉系统常量
 *
 * @author hp
 */
@Data
public class DingUrlConstant {

    @FieldDesc("根据用户手机号获取企业内钉钉userId")
    public static final String GET_USERID_BY_MOBILE = "https://oapi.dingtalk.com/topapi/v2/user/getbymobile";

    @FieldDesc("h5微应用发送工作通知")
    public static final String SEND_WORK_MESSAGE = "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2";

    @FieldDesc("钉钉互动卡片注册回调地址和路由键")
    public static final String REGISTER_CALLBACK = "https://oapi.dingtalk.com/topapi/im/chat/scencegroup/interactivecard/callback/register";

    @FieldDesc("根据扫码后回调code获取用户信息")
    public static final String GET_USER_INFO_BY_CODE = "https://oapi.dingtalk.com/sns/getuserinfo_bycode";

    @FieldDesc("根据钉钉unionId获取userId")
    public static final String GET_USER_ID_BY_UNION_ID = "https://oapi.dingtalk.com/topapi/user/getbyunionid";

    @FieldDesc("根据钉钉userId获取user")
    public static final String GET_USER_BY_USER_ID = "https://oapi.dingtalk.com/topapi/v2/user/get";

    public static final String LOGIN_WITH_ACCOUNT = "https://oapi.dingtalk.com/connect/oauth2/sns_authorize?appid=APPID&response_type=code&scope=snsapi_login&state=STATE&redirect_uri=REDIRECT_URI";

    public static final String LOGIN_WITH_SCAN = "https://oapi.dingtalk.com/connect/qrconnect?appid=APPID&response_type=code&scope=snsapi_login&state=STATE&redirect_uri=REDIRECT_URI";

    public static final String LOGIN_WITHOUT_OP = "https://oapi.dingtalk.com/connect/oauth2/sns_authorize?appid=APPID&response_type=code&scope=snsapi_auth&state=STATE&redirect_uri=REDIRECT_URI";

    @FieldDesc("获取部门列表")
    public static final String GET_DEPT_LIST = "https://oapi.dingtalk.com/topapi/v2/department/listsub";

    @FieldDesc("获取群会话")
    public static final String GET_CHAT_INFO = "https://oapi.dingtalk.com/chat/get";

    @FieldDesc("获取外部联系人")
    public static final String GET_EXTCONTACT_INFO = "https://oapi.dingtalk.com/topapi/extcontact/get";

    @FieldDesc("添加外部联系人")
    public static final String ADD_EXTCONTACT = "https://oapi.dingtalk.com/topapi/extcontact/create";

    @FieldDesc("删除外部联系人")
    public static final String DELETE_EXTCONTACT = "https://oapi.dingtalk.com/topapi/extcontact/delete";

    @FieldDesc("更新外部联系人")
    public static final String UPDATE_EXTCONTACT = "https://oapi.dingtalk.com/topapi/extcontact/update";

    @FieldDesc("获取外部联系人列表")
    public static final String GET_EXTCONTACTS = "https://oapi.dingtalk.com/topapi/extcontact/list";

    @FieldDesc("获取外部联系人标签列表")
    public static final String GET_EXTCONTACT_TAGS = "https://oapi.dingtalk.com/topapi/extcontact/listlabelgroups";

    @FieldDesc("角色列表")
    public static final String GET_ROLE_LIST = "https://oapi.dingtalk.com/topapi/role/list";

    @FieldDesc("根据角色id获取用户列表")
    public static final String GET_USERS_BY_ROLE_ID = "https://oapi.dingtalk.com/topapi/role/simplelist";

    @FieldDesc("创建审批实例")
    public static final String CREATE_PROCESS_INSTANCE = "https://oapi.dingtalk.com/topapi/processinstance/create";

    @FieldDesc("获取企业内,用户可管理的所有审批模版")
    public static final String GET_MANAGEABLE_PROCESS_TEMPLATE_IN_CORP_LIST = "https://oapi.dingtalk.com/topapi/process/template/manage/get";

    @FieldDesc("根据审批模版名称获取模版编号")
    public static final String GET_PROCESS_TEMPLATE_CODE_BY_NAME = "https://oapi.dingtalk.com/topapi/process/get_by_name";

    @FieldDesc("根据审批模版编号获取审批实例id列表")
    public static final String GET_PROCESS_INSTANCE_IDS = "https://oapi.dingtalk.com/topapi/processinstance/listids";

    @FieldDesc("获取审批实例信息")
    public static final String GET_PROCESS_INSTANCE = "https://oapi.dingtalk.com/topapi/processinstance/get";

    @FieldDesc("media文件上传")
    public static final String MEDIA_UPLOAD = "https://oapi.dingtalk.com/media/upload";

    @FieldDesc("旧版SDK 获取企业内部应用的access_token")
    public static final String ACCESS_TOKEN_OLD = "https://oapi.dingtalk.com/gettoken";

    @FieldDesc("通过免登码获取用户信息旧版SDK")
    public static final String GET_USER_BY_LOGIN_AUTH_CODE = "https://oapi.dingtalk.com/topapi/v2/user/getuserinfo";

    @FieldDesc("获取部门用户基础信息,只支持获取员工的userId和name两个字段信息")
    public static final String GET_USER_BASE_INFO_IN_DEPT = "https://oapi.dingtalk.com/topapi/user/listsimple";
}
