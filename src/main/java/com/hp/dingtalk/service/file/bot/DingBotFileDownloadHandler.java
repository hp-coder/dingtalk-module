package com.hp.dingtalk.service.file.bot;

import com.aliyun.dingtalkrobot_1_0.models.RobotMessageFileDownloadResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teautil.models.RuntimeOptions;
import com.hp.dingtalk.component.application.IDingBot;
import com.hp.dingtalk.component.exception.DingApiException;
import com.hp.dingtalk.component.factory.token.DingAccessTokenFactory;
import com.hp.dingtalk.service.IDingBotFileDownloadHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * @author hp 2023/3/22
 */
@Slf4j
public class DingBotFileDownloadHandler implements IDingBotFileDownloadHandler {
    @Override
    public String downloadUrl(IDingBot bot, String downloadCode) throws Exception {
        com.aliyun.dingtalkrobot_1_0.Client client = new com.aliyun.dingtalkrobot_1_0.Client(this.config());
        com.aliyun.dingtalkrobot_1_0.models.RobotMessageFileDownloadHeaders robotMessageFileDownloadHeaders = new com.aliyun.dingtalkrobot_1_0.models.RobotMessageFileDownloadHeaders();
        robotMessageFileDownloadHeaders.xAcsDingtalkAccessToken = DingAccessTokenFactory.accessToken(bot);
        com.aliyun.dingtalkrobot_1_0.models.RobotMessageFileDownloadRequest robotMessageFileDownloadRequest = new com.aliyun.dingtalkrobot_1_0.models.RobotMessageFileDownloadRequest()
                .setDownloadCode(downloadCode)
                .setRobotCode(bot.getAppKey());
        try {
            final RobotMessageFileDownloadResponse response = client.robotMessageFileDownloadWithOptions(robotMessageFileDownloadRequest, robotMessageFileDownloadHeaders, new RuntimeOptions());
            return response.getBody().getDownloadUrl();
        } catch (TeaException err) {
            if (StringUtils.hasText(err.code) && StringUtils.hasText(err.message)) {
                log.error("换取机器人文件异常,code:{},msg:{}", err.code, err.message);
                throw new DingApiException("换取机器人文件异常" + err.message);
            }
        } catch (Exception e) {
            TeaException err = new TeaException(e.getMessage(), e);
            if (StringUtils.hasText(err.code) && StringUtils.hasText(err.message)) {
                log.error("换取机器人文件异常,code:{},msg:{}", err.code, err.message);
                throw new DingApiException("换取机器人文件异常" + err.message);
            }
            throw new DingApiException("换取机器人文件异常", e);
        }
        return null;
    }
}
