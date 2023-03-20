package com.hp.dingding.constant;

import lombok.AllArgsConstructor;

/**
 * 钉钉消息类型
 * <p>
 * 大小写与官方文档中对应
 */
@AllArgsConstructor
public enum DingMsgType {
    text,
    sampleText,
    officialTextMsg,
    sampleMarkdown,
    officialImageMsg,
    sampleActionCard,
    sampleActionCard1,
    sampleActionCard2,
    sampleActionCard3,
    officialActionCardMsg,
    officialActionCardMsg1,
    officialActionCardMsg2,
    sampleLink,
    image,
    file,
    link,
    voice,
    markdown,
    oa,
    action_card;
}
