package com.hp.dingding.pojo.dto;

import com.google.gson.annotations.SerializedName;
import com.hp.dingding.constant.DingResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 根据手机号获取钉钉用户userId响应信息
 *
 * @Author: HP
 */
@Data
public class DingUserIdDTO extends DingResponse {

    @SerializedName("result")
    private Result result;

    @Data
    @AllArgsConstructor
    public static class Result {
        private String userid;
        private List<String> exclusive_account_userid_list;
    }


}
