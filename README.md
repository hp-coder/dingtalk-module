# 通用钉钉模块

## 说明
仅针对钉钉文档中的部分常用api进行封装
钉钉新版SDK常用驼峰命名，旧版SDK常用下划线方式，在看文档和调用其SDK时需特别注意！！！

## 更新
- 0000-00-00：增加消息，用户，角色，联系人等
- 2022-06-27：增加OA审批接口
- 2022-07-01：bug fixed, 钉钉应用的accesstoken过期时间为7200s，原提前60s过期，实际发现在调用时，钉钉服务还是容易发生超时问题，增加提前过期时间至600s
- 2022-07-27：钉钉机器人单聊文字消息回调及自定义处理器, 分支处理 
- 2023-03-22：升级最新版本的新版钉钉SDK，优化对普通消息对象的定义，以优化消息接口使用消息类型的此前未限制的问题

## 使用
### 下载安装
```shell
# make sure that you're on the right branch
git clone -b prod https://github.com/hp-coder/dingtalk-module.git
# latest version is most likely still on the test branch, I apologize for my laziness :( 
git clone -b test https://github.com/hp-coder/dingtalk-module.git
# install it in your local maven repository
mvn install
```
### maven项目引入
```xml
<dependency>
  <groupId>com.hp</groupId>
  <artifactId>dingtalk-module</artifactId>
  <version>1.0.5-SNAPSHOT</version>
</dependency>
```

### 依赖
请确保项目中包含相关配置信息
- springboot

### 接口/扩展
- IDingApp 钉钉应用对象接口,接口配置相关appid,key等信息, 推荐使用enum实现该接口
    - IDingMiniH5 企业内微应用
      - IDingCoolApp 酷应用，暂时为一个抽象接口，没有实际应用
    - IDingBot 企业内机器人
- IDingApi 实现封装后钉钉API的抽象顶层接口,对于未实现的接口可以自行实现IDingApi接口后自主集成
- IDingToken 定义系统内获取token的能力，目前实现为工厂，仅用于获取企业内应用的accessToken
- IDingInteractiveCardCallBack 对于互动卡片的回调地址封装的接口,实现该接口,项目在启动时会自动注册回调地址
- IDingInteractiveMsg 互动卡片公共能力的抽象
- IDingMsg 基本常用消息等模版已经实现,无需再次实现
  - IDingBotMsg 机器人通过接口发送的消息
  - IDingBotWebhookMsg 机器人通过webhook发送的消息
  - AbstractDingMsg 上述消息的公共能力
  - IDingWorkNotifyMsg h5mini应用发送的工作通知
    - DingWorkNotifyMsg 一个相对安全的封装
    - 对于H5mini应用，发送消息为工作通知，SDK中已经对工作通知封装，但是其参数的msgType和数据体存在不对应的风险，使用上述对象能杜绝这种场景
- IDingBotMsgCallBackHandler 通过正则匹配消息处理器，定义统一机器人消息回调处理器处理逻辑
  - 忽略：消息中有sessionWebhook字段可以直接实例化一个调用SDK接口的Client，通过这个client可以直接对该会话发送消息
  - 场景太多，暂时就业务中使用最多的单聊做了迭代


## 集成
### AccessToken
DingAccessTokenFactory
- 使用静态工厂获取,本地cache减少依赖性
- 引入了对应SDK版本的概念，虽然此前旧版SDK的API使用新版的AccessToken暂未发现问题，但是在最近即成新版钉钉登录的时候为了保险起见还是引入了旧版的token接口

### 用户
DingUserHandler
- 根据扫码/免登/钉钉账号密码登录后返回code获取用户userid, unionid, userinfo等信息
- 增加一个钉钉应用内免登->获取临时授权码->配合accessToken获取用户userId->获取用户信息的登录接口
  - 具体登录接入的实现会在demo仓库实现

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
DingBotMsgCallbackPayload
- 封装一个用户回复钉钉机器人消息时通过配置的url post获得的请求数据对象
- 该数据对象现在支持多种类型，暂时没有好的限制方法将数据的获取的类型固定，使用时必须判断msgType类型,用对应的类型反序列化数据

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

### 机器人消息回调接口
DingBotMsgCallbackController
- 统一机器人消息回调
    - 启动类增加componentScan注解，扫描"com.hp"即可
    - 如果有拦截器，请释放`/ding/bot/msg/callback`拦截
    - 根据新sdk增加了其他支持的消息类型（file,voice,video,etc.)
    - 增加请求校验

### 机器人回调消息处理器
IDingBotMsgCallBackHandler
- 统一回调消息处理器定义
    - 使用正则表达式匹配消息内容
    - 实现该接口完成对消息的自定义处理
- 默认测试处理器 DefaultBotMsgCallbackHandler
- 默认兜底处理器 DefaultFallbackMsgCallbackHandler

#### 说明
- 机器人由于新增了可获得用户发送的多种消息类型，以前是只有文字类型，在predicate时只取跟当前处理业务对应类型的数据即可，在这里影响不大。

#### 使用
- 实现IDingBotMsgCallBackHandler并实现其方法配置正则规则
- 启动增加包扫描即可
- 钉钉机器人后台配置消息回调接口并向机器人发送消息即可

## 期望
- *机器人消息互动类shell交互式的抽象或实现,这个应该更实用些
- 减小包体积,集成其他常用功能点,优化代码接口,增加更多可配置项等
