package com.hp.dingding.constant;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * 钉钉统一消息响应体
 */
@Data
public class DingResponse {
    @SerializedName("errcode")
    private Long errCode;

    @SerializedName("errmsg")
    private String errMsg;

    @SerializedName("request_id")
    private String requestId;
}
