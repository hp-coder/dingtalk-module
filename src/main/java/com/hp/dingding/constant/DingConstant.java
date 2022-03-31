package com.hp.dingding.constant;


import lombok.Data;

/**
 * 钉钉系统常量
 *
 * @Author: HP
 */
@Data
public class DingConstant {

    /**
     * 根据用户手机号获取企业内钉钉userId
     */
    public static final String GET_USERID_BY_MOBILE = "https://oapi.dingtalk.com/topapi/v2/user/getbymobile";

    /**
     * 根据钉钉userId获取钉钉用户信息
     */
    public static final String SEND_WORK_MESSAGE = "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2";

    /**
     * 钉钉互动卡片注册回调地址和路由键
     */
    public static final String REGISTER_CALLBACK = "https://oapi.dingtalk.com/topapi/im/chat/scencegroup/interactivecard/callback/register";

    /**
     * 根据扫码后回调code获取用户信息
     */
    public static final String GET_USER_INFO_BY_CODE = "https://oapi.dingtalk.com/sns/getuserinfo_bycode";

    /**
     * 根据钉钉unionId获取userId
     */
    public static final String GET_USER_ID_BY_UNION_ID = "https://oapi.dingtalk.com/topapi/user/getbyunionid";

    /**
     * 根据钉钉userId获取user
     */
    public static final String GET_USER_BY_USER_ID = "https://oapi.dingtalk.com/topapi/v2/user/get";

    public static final String LOGIN_WITH_ACCOUNT = "https://oapi.dingtalk.com/connect/oauth2/sns_authorize?appid=APPID&response_type=code&scope=snsapi_login&state=STATE&redirect_uri=REDIRECT_URI";

    public static final String LOGIN_WITH_SCAN = "https://oapi.dingtalk.com/connect/qrconnect?appid=APPID&response_type=code&scope=snsapi_login&state=STATE&redirect_uri=REDIRECT_URI";

    public static final String LOGIN_WITHOUT_OP = "https://oapi.dingtalk.com/connect/oauth2/sns_authorize?appid=APPID&response_type=code&scope=snsapi_auth&state=STATE&redirect_uri=REDIRECT_URI";

}
