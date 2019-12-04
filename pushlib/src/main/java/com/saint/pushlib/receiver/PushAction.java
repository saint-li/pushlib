package com.saint.pushlib.receiver;

public interface PushAction {
    //通知
    String RECEIVE_NOTIFICATION = "com.saint.pushlib.ACTION_RECEIVE_NOTIFICATION";
    //通知点击
    String RECEIVE_NOTIFICATION_CLICK = "com.saint.pushlib.ACTION_RECEIVE_NOTIFICATION_CLICK";
    //收到消息
    String RECEIVE_MESSAGE = "com.saint.pushlib.ACTION_RECEIVE_MESSAGE";
    //Token
    String RECEIVE_TOKEN_SETED = "com.saint.pushlib.ACTION_RECEIVE_TOKEN_SET";
    //初始化结果
    String RECEIVE_INIT_RESULT = "com.saint.pushlib.ACTION_RECEIVE_INIT_RESULT";
    //设置别名
    String RECEIVE_SET_ALIAS = "com.saint.pushlib.ACTION_RECEIVE_SET_ALIAS";
    //退出登录
    String RECEIVE_LOGIN_OUT = "com.saint.pushlib.ACTION_RECEIVE_LOGIN_OUT";
}
