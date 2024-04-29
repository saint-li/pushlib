package com.saint.pushlib

class PushConfig(
    var enableHWPush: Boolean = true,
    var enableMiPush: Boolean = true,
    var enableOPPOPush: Boolean = true,
    var enableJPushSmart:Boolean = false,//极光用户分群推送，读取软件列表
    var enableJPushLBS:Boolean = false,//极光地理围栏推送，读取位置信息
    var enableJPushAutoWakeup:Boolean = false,//极光推送SDK自启动
)