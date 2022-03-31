# 通用钉钉模块

## 说明
仅针对钉钉文档中的部分常用api进行封装

## 使用
### 下载安装
```shell
git clone #{url}
# make sure that you're on the right branch
git checkout prod
# install it in your local maven repository
mvn install
```
### maven项目引入
```xml
<dependency>
    <groupId>com.luban</groupId>
    <artifactId>luban-dingding</artifactId>
    <version>1.0</version>
</dependency>
```

### 项目内配置

依赖于springboot,redis,请确保项目中包含相关配置信息

基本常用消息等模版已经实现,无需再次实现;

应用信息,需自己实现IDingApp接口配置相关appid,key等信息, 推荐使用enum实现该接口;

对于互动卡片消息, 需要实现IDingCallBack接口,配置相关信息, 项目在启动时会向自动注册回调接口等信息;

对于为实现的接口可以自行实现IDingApi接口后自主集成,或提PR

## 集成
### AccessToken
- 使用静态工厂获取,使用redis缓存

### 用户相关
- 根据扫码/免登/钉钉账号密码登录后返回code获取用户userid, unionid, userinfo等信息

### 消息相关
`主要集成于单聊,支持机器人,企业内应用等`
- 单/群聊(未集成获取场景群id等)
    - 普通消息: 文本,markdown等
    - 互动卡片消息: interactiveMsg, 注册互动回调地址等

## 扩展
定义多种接口,方便后期扩展功能;

顶层api接口: IDingApi;  被用户,发送消息,发送互动卡片消息,token接口等实现

顶层消息模版接口: IDingMsg; 被各类消息模版实现

顶层回调url信息接口: IDingCallBack; 被具体应用使用的回调地址实现(互动卡片的回调地址)

## 期望
减小包体积,集成其他常用功能点,优化代码接口,增加更多可配置项等
