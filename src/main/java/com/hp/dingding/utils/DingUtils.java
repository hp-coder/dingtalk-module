package com.hp.dingding.utils;

import com.taobao.api.TaobaoResponse;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * 工具类
 *
 * @Author: HP
 */
public class DingUtils {

    public static void isSuccess(TaobaoResponse response) {
        Assert.isTrue(response.isSuccess(), "请求失败：" + response.getMsg());
        Assert.isTrue(Objects.equals("0", response.getErrorCode()), "请求失败：" + response.getMsg());
    }
}
