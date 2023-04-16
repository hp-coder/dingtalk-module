package com.hp.dingtalk.utils;

import com.hp.dingtalk.constant.DingApiCode;
import com.taobao.api.ApiException;
import com.taobao.api.TaobaoResponse;

import java.util.Objects;

/**
 * 工具类
 *
 * @author hp
 */
public class DingUtils {

    public static void isSuccess(TaobaoResponse response) throws ApiException {
        isTrue(response.isSuccess(), response);
        isTrue(Objects.equals("0", response.getErrorCode()), response);
    }

    private static void isTrue(boolean expression, TaobaoResponse response) throws ApiException {
        if (!expression) {
            throw new ApiException(response.getErrorCode(), response.getMsg(), response.getSubCode(), response.getSubMsg());
        }
    }

    public static class UserResponse {
        public static void isOk(TaobaoResponse response) throws ApiException {
            isSuccess(response);
            isTrue(!Objects.equals(String.valueOf(DingApiCode.UserResponse.NOT_FOUND.getCode()), response.getErrorCode()), response);
        }
    }
}
