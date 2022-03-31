package com.hp.dingding.service.api;

import com.hp.dingding.component.application.IDingApp;
import com.hp.dingding.pojo.dto.DingUserInfoDTO;
import com.hp.dingding.component.IDingApi;

/**
 * 钉钉用户信息处理器
 *
 * @Author: HP
 */
public interface IDingUserHandler extends IDingApi {

    /**
     * 根据手机获取企业内用户userId
     * <p>
     * 员工离职后，无法再通过手机号获取userId
     *
     * @param app    钉钉应用，新应用请实现IDingApp接口
     * @param mobile 企业内钉钉用户电话
     * @return 钉钉userId
     */
    String findUserIdByMobile(IDingApp app, String mobile);

    /**
     * 根据回调code获取钉钉用户unionId
     *
     * @param app  钉钉应用，新应用请实现IDingApp接口
     * @param code 前端回调后请求的code
     * @return 钉钉用户unionId
     */
    String unionIdByCode(IDingApp app, String code);

    /**
     * 根据钉钉用户unionId获取钉钉用户userId
     *
     * @param app     钉钉应用，新应用请实现IDingApp接口
     * @param unionId 钉钉用户unionId
     * @return 钉钉用户userId
     */
    String userIdByUnionId(IDingApp app, String unionId);

    /**
     * 根据钉钉用户userId获取钉钉用户信息
     *
     * @param app    钉钉应用，新应用请实现IDingApp接口
     * @param userId 钉钉用户userId
     * @return 钉钉用户信息json，两种情况，企业内和非企业内
     */
    String userByUserId(IDingApp app, String userId);

    /**
     * 根据钉钉前端回调后提交的code获取钉钉用户信息json字符串
     *
     * @param app  钉钉应用，新应用请实现IDingApp接口
     * @param code 前端回调后请求的code
     * @return 钉钉用户信息json，两种情况，企业内和非企业内
     */
    String userJsonByCode(IDingApp app, String code);

    DingUserInfoDTO userByCode(IDingApp app, String code);
}
