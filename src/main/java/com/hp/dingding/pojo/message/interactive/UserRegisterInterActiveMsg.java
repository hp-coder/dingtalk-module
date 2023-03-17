package com.hp.dingding.pojo.message.interactive;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 资质平台新用户+开通体验+更新卡片
 * ea49421b-fe80-4026-af08-5c954f0d636c
 * IM 卡片
 * 已发布
 * 2022/03/15 14:46
 * 2022/03/15 14:50
 */
@Getter
@Setter
@Accessors(chain = true)
public class UserRegisterInterActiveMsg extends AbstractDingInteractiveMsg {

    private String title;
    private String registerOrigin;
    private String account;
    private String roleName;
    private String validDate;
    private String provinceName;
    private String cityName;
    private String realName;
    private String invitationCode;
    private String thirdUrl;
    private String clickUrl;
    private String popularizor;
    private Integer freeDays;

    public UserRegisterInterActiveMsg(String callbackUrl, String outTrackId) {
        super(callbackUrl, outTrackId, "6f79c335-272e-4baa-846d-4927983ff816");
    }
}
