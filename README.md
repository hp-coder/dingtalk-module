# 通用钉钉模块

## 说明
仅针对钉钉文档中的部分常用api进行封装

## 更新
- 0000-00-00：增加消息，用户，角色，联系人等
- 2022-06-27：增加OA审批接口
- 2022-07-01：bug fixed, 钉钉应用的accesstoken过期时间为7200s，原提前60s过期，实际发现在调用时，钉钉服务还是容易发生超时问题，增加提前过期时间至600s
- 2022-07-27：钉钉机器人单聊文字消息回调及自定义处理器, 分支处理 

## 使用
### 下载安装
```shell
# make sure that you're on the right branch
git clone -b prod https://github.com/hp-coder/dingtalk-module.git
# install it in your local maven repository
mvn install
```
### maven项目引入
```xml
<dependency>
    <groupId>com.hp</groupId>
    <artifactId>dingtalk-module</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 依赖
请确保项目中包含相关配置信息
- springboot
- 
### 接口/扩展
- IDingMsg 基本常用消息等模版已经实现,无需再次实现;
- IDingApp 钉钉应用对象接口,接口配置相关appid,key等信息, 推荐使用enum实现该接口;
    - IDingMiniH5 企业内微应用
    - IDingBot 企业内机器人
- IDingCallBack 对于互动卡片的回调地址封装的接口,实现该接口,项目在启动时会自动注册回调地址;
- IDingApi 实现封装后钉钉API的抽象顶层接口,对于未实现的接口可以自行实现IDingApi接口后自主集成,或提PR
- IDingBotMsgCallBackHandler 通过正则匹配消息处理器，定义统一机器人消息回调处理器处理逻辑
## 集成
### AccessToken
DingAccessTokenFactory
- 使用静态工厂获取,使用redis缓存

### 用户
DingUserHandler
- 根据扫码/免登/钉钉账号密码登录后返回code获取用户userid, unionid, userinfo等信息

### 消息
DingBotMessageHandler
DingAppMessageHandler

`主要集成于单聊,支持机器人,企业内应用等`
- 单/群聊(未集成获取场景群id等)
    - 普通消息: 文本,markdown等
    - 互动卡片消息: interactiveMsg, 注册互动回调地址等

### 外部联系人
DingExtContactHandler

`截至2022-06-01钉钉文档对于添加联系人的参数是否必须还是存在问题，缺少几个非必要参数会导致提交失败`

可以配置企业座机号，当用户呼叫该号码时根据外部联系人中保存的客户号码转接到对应到客户经理来接通

经测试其添加接口同时可以做到修改联系人，故可以不用存储外部联系人用户的钉钉userid就可以做到修改
- 钉钉外部联系人的CRUD接口

### 钉钉机器人消息配置
BotInteractiveMsgPayload
- 封装一个用户回复钉钉机器人消息时通过配置的url post获得的请求数据对象

### 角色
DingRoleHandler
- 角色列表
- 角色下用户列表

### 部门
DingDeptHandler
- 部门列表

### 单群聊会话
DingChatHandler
- 通过chatId获取openConversationId
- 获取会话信息

### OA审批
DingOAHandler
- 根据模版名称获取模版
- 获取审批模版列表
- 获取模版下实例id列表
- 获取实例信息

### 机器人消息回调接口（目前只针对文字内容单聊）
DingBotMsgCallbackController
- 统一机器人消息回调
    - 启动类增加componentScan注解，扫描"com.hp"即可
    - 如果有拦截器，请释放/ding/bot/msg/callback拦截

### 机器人回调消息处理器
IDingBotMsgCallBackHandler
- 统一回调消息处理器定义
    - 使用正则表达式匹配消息内容
    - 实现该接口完成对消息的自定义处理
- 默认集成测试模版 DefaultBotMsgCallbackHandler

#### 使用方式
- 利用class实现IDingBot接口配置机器人信息
- 实现IDingBotMsgCallBackHandler并实现其方法配置正则规则
- 启动增加包扫描即可



## 期望
减小包体积,集成其他常用功能点,优化代码接口,增加更多可配置项等
