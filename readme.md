# 通用钉钉模块

## 说明

仅针对钉钉文档中的部分常用api进行封装
钉钉新版SDK常用驼峰命名，旧版SDK常用下划线方式，在看文档和调用其SDK时需特别注意！！！

## 使用

### 下载安装

```shell
# make sure that you're on the right branch
git clone -b prod https://github.com/hp-coder/dingtalk-module.git
# latest version is most likely still on the test branch, I apologize for my laziness :( 
git clone -b test https://github.com/hp-coder/dingtalk-module.git
# install it in your local maven repository
mvn install
# deploy if you have a private repo
mvn deploy
```

### maven项目引入

```xml

<dependency>
    <groupId>com.hp</groupId>
    <artifactId>dingtalk-module</artifactId>
    <version>1.0.9-SNAPSHOT</version>
</dependency>
```

### 依赖

请确保项目中包含相关配置信息

- SpringBoot 2.7.2
- [com.hp/common-base (branch:hup_dev)](https://github.com/hp-coder/common-starters/)

### 接口/扩展

application:

- [IDingApp.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fcomponent%2Fapplication%2FIDingApp.java)
  钉钉应用对象接口,接口配置相关appid,key等信息, 推荐使用enum实现该接口
    - [IDingMiniH5.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fcomponent%2Fapplication%2FIDingMiniH5.java) 企业内微应用
        - [IDingCoolApp.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fcomponent%2Fapplication%2FIDingCoolApp.java)
          酷应用，暂时为一个抽象接口，没有实际应用
    - [IDingBot.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fcomponent%2Fapplication%2FIDingBot.java) 企业内机器人

configuration:

- [IDingMiniH5EventCallbackConfig.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fcomponent%2Fconfiguration%2FIDingMiniH5EventCallbackConfig.java)
  钉钉微应用事件订阅回调接口, 为其提供钉钉应用实例的支持
    - `dingtalk.miniH5.event.enabled=true`时, 必须配置提供一个默认可用的微应用

token:

- [IDingToken.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fcomponent%2Ffactory%2Ftoken%2FIDingToken.java)
  定义Token的能力，目前实现为工厂，仅用于获取企业内应用的accessToken
    - [DingAccessTokenFactory.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fcomponent%2Ffactory%2Ftoken%2FDingAccessTokenFactory.java)
      Token工厂, 对于非频繁重启的服务, 这里用基于服务内存的缓存

statemachine:

- 状态机模式的实现, 用于在机器人消息交互时完成更复杂业务
- [IDingState.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fcomponent%2Fstatemachine%2FIDingState.java) 状态
- [IDingStateContext.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fcomponent%2Fstatemachine%2FIDingStateContext.java)
  状态机上下文
- [IDingStateEvents.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fcomponent%2Fstatemachine%2FIDingStateEvents.java)
  状态机事件
- [IDingStateMachine.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fcomponent%2Fstatemachine%2FIDingStateMachine.java)
  状态机

API:

- [DingBotMsgCallbackController.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fcontroller%2FDingBotMsgCallbackController.java)
  机器人单聊消息回调接口
    - 统一机器人消息回调
    - 如果有拦截器，请释放`/ding/bot/msg/callback`拦截
    - 根据新sdk增加了其他支持的消息类型（file,voice,video,etc.)
    - 增加请求校验
- [DingMiniH5EventController.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fcontroller%2FDingMiniH5EventController.java)
  微应用事件订阅回调接口
    - 微应用事件订阅的回调
    - 如果有拦截器，请释放`/ding/miniH5/event/callback`拦截
    - `dingtalk.miniH5.event.enabled=true`时可用
    - 增加请求校验
    - 通过站内广播事件的方式将收到的回调通知到客户端
    -
  客户端可以考虑实现插件中的 [AbstractDingMiniH5EventCallbackHandler.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fservice%2Fcallback%2Fminih5%2FAbstractDingMiniH5EventCallbackHandler.java)
  处理器来完成对事件的处理

listener:

- [DefaultDingMiniH5EventListener.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Flistener%2FDefaultDingMiniH5EventListener.java)
  插件内提供一个默认的微应用事件回调的事件监听器
    - `dingtalk.miniH5.event.listener.enabled=true`时可用

service:

- [IDingApi.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fcomponent%2FIDingApi.java)
  实现封装后钉钉API的抽象顶层接口,对于未实现的接口可以自行实现IDingApi接口后自主集成
    - [IDingBotFileDownloadHandler.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fservice%2FIDingBotFileDownloadHandler.java)
      下载附件相关
    - [IDingBotMessageHandler.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fservice%2FIDingBotMessageHandler.java)
      机器人消息(主要是单聊)
    - [IDingBotMsgCallBackHandler.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fservice%2FIDingBotMsgCallBackHandler.java)
      机器人消息互动回调,通过正则匹配消息处理器，定义统一机器人消息回调处理器处理逻辑
        - 统一回调消息处理器定义
            - 使用正则表达式匹配消息内容
            - 实现该接口完成对消息的自定义处理
        - 默认测试处理器 DefaultBotMsgCallbackHandler
        - 默认兜底处理器 DefaultFallbackMsgCallbackHandler
    - [IDingChatHandler.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fservice%2FIDingChatHandler.java) 群组等(
      未实际应用)
    - [IDingContactHandler.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fservice%2FIDingContactHandler.java) 联系人
    - [IDingDeptHandler.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fservice%2FIDingDeptHandler.java) 部门
    - [IDingExtContactHandler.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fservice%2FIDingExtContactHandler.java)
      外部联系人
    - [IDingInteractiveMessageHandler.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fservice%2FIDingInteractiveMessageHandler.java)
      机器人互动卡片(高级版)
    - [IDingLoginHandler.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fservice%2FIDingLoginHandler.java) 钉钉登录相关(
      扫码,应用内免登等)
    - [IDingMediaHandler.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fservice%2FIDingMediaHandler.java) 媒体文件
    - [IDingMiniH5EventCallbackHandler.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fservice%2FIDingMiniH5EventCallbackHandler.java)
      微应用事件订阅回调处理
    - [IDingOAHandler.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fservice%2FIDingOAHandler.java) OA审批
    - [IDingRoleHandler.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fservice%2FIDingRoleHandler.java) 角色
    - [IDingSSOHandler.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fservice%2FIDingSSOHandler.java) 系统管理后台
    - [IDingUserHandler.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fservice%2FIDingUserHandler.java) 用户
    - [IDingWorkNotifyMessageHandler.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fservice%2FIDingWorkNotifyMessageHandler.java)
      工作通知

model:

回调:

- [DingMiniH5EventCallbackRequest.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fpojo%2Fcallback%2FDingMiniH5EventCallbackRequest.java)
  微应用事件订阅回调的query params请求体
- [DingInteractiveCardCallBackRequest.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fpojo%2Fcallback%2FDingInteractiveCardCallBackRequest.java)
  机器人高级互动卡片回调请求的请求体
- [DingBotMsgCallbackRequest.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fpojo%2Fcallback%2FDingBotMsgCallbackRequest.java)
  机器人互动消息的回调请求体

消息:

- [IDingMsg.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fpojo%2Fmessage%2FIDingMsg.java) 钉钉消息
    - [IDingBotMsg.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fpojo%2Fmessage%2FIDingBotMsg.java) 机器人通过接口发送的消息
    - [IDingBotWebhookMsg.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fpojo%2Fmessage%2FIDingBotWebhookMsg.java)
      机器人通过webhook发送的消息
        - [DingTextMsg.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fpojo%2Fmessage%2Fcommon%2FDingTextMsg.java) 文本消息
        - [DingMediaMsg.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fpojo%2Fmessage%2Fcommon%2FDingMediaMsg.java)
          媒体消息
        - [DingMarkdownMsg.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fpojo%2Fmessage%2Fcommon%2FDingMarkdownMsg.java)
          markdown消息
        - [DingLinkMsg.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fpojo%2Fmessage%2Fcommon%2FDingLinkMsg.java) 链接消息
        - [DingImageMsg.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fpojo%2Fmessage%2Fcommon%2FDingImageMsg.java)
          图片消息
        - [DingFeedCardMsg.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fpojo%2Fmessage%2Fcommon%2FDingFeedCardMsg.java)
          卡片消息
        - [DingActionCardMsg.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fpojo%2Fmessage%2Fcommon%2FDingActionCardMsg.java)
          简单互动卡片消息
        - [DingEmptyMsg.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fpojo%2Fmessage%2Fcommon%2FDingEmptyMsg.java) 空消息

- [IDingInteractiveMsg.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fpojo%2Fmessage%2Finteractive%2FIDingInteractiveMsg.java)
  互动卡片
- [IDingInteractiveCardCallBack.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fpojo%2Fmessage%2Finteractive%2Fcallback%2FIDingInteractiveCardCallBack.java)
  注册互动卡片按钮回调

- [IDingWorkNotifyMsg.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fpojo%2Fmessage%2Fworknotify%2FIDingWorkNotifyMsg.java)
  工作通知
    - 对于H5mini应用，发送消息为工作通知，SDK中已经对工作通知封装，但是其参数的msgType和数据体存在不对应的风险，使用上述对象能杜绝这种场景

other:

- [MediaRequest.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fpojo%2Ffile%2FMediaRequest.java) 媒体文件请求体
- [CreateProcessInstanceRequest.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fpojo%2Foa%2FCreateProcessInstanceRequest.java)
  创建OA审批实例请求体

support:

- [GsonBuilderVisitor.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Fpojo%2FGsonBuilderVisitor.java)
  系统基于Gson完成json的序列化, 这里使用visitor模式增强
- [DingCallbackCrypto.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Futils%2FDingCallbackCrypto.java) 钉钉官方开源的加解密工具
- [DingMarkdown.java](src%2Fmain%2Fjava%2Fcom%2Fhp%2Fdingtalk%2Futils%2FDingMarkdown.java) 符合钉钉格式的markdown构建工具
