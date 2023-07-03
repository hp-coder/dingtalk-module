package com.hp.dingtalk.pojo.callback;

import lombok.Data;

/**
 * @author hp
 */
@Data
public class DingMiniH5EventCallbackRequest {
    private String signature;
    private String timestamp;
    private String nonce;
}
