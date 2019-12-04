package com.saint.pushlib.receiver;

import android.content.Context;
import android.content.Intent;

import com.saint.pushlib.bean.ReceiverInfo;
import com.saint.pushlib.cache.PushTokenCache;
import com.saint.pushlib.util.PushLog;


/**
 * 统一处理收到的推送
 */

public class PushReceiverManager {

    public static final String INTENT_RECEIVER_INFO = "receiverInfo";
    private static PushReceiverManager instance;


    public static PushReceiverManager getInstance() {
        if (instance == null) {
            synchronized (PushReceiverManager.class) {
                if (instance == null) {
                    instance = new PushReceiverManager();
                }
            }
        }
        return instance;
    }

    private PushReceiverManager() {

    }


    /***
     * 用户注册sdk之后的通知
     * 注册成功之后调用
     * @param context
     */
    public void onRegistration(Context context, ReceiverInfo info) {
        PushLog.i("onRegistration=" + info.toString());
        info.setType(PushAction.RECEIVE_INIT_RESULT);
        sendPushData(context, PushAction.RECEIVE_INIT_RESULT, info);
    }

    /**
     * 获取到华为token后
     */
    public void setToken(Context context, ReceiverInfo info) {
        PushLog.i("setToken=" + info.toString());
        PushTokenCache.putToken(context, info.getContent());
        info.setType(PushAction.RECEIVE_TOKEN_SETED);
        sendPushData(context, PushAction.RECEIVE_TOKEN_SETED, info);
    }

    /**
     * 设置了别名之后
     *
     * @param context
     * @param info
     */
    public void onAliasSet(Context context, ReceiverInfo info) {
        PushLog.i("onAliasSet=" + info.toString());
        info.setType(PushAction.RECEIVE_SET_ALIAS);
        sendPushData(context, PushAction.RECEIVE_SET_ALIAS, info);
    }

    /**
     * 接收到消息推送，不会主动显示在通知栏
     *
     * @param context
     * @param info
     */
    public void onMessageReceived(Context context, ReceiverInfo info) {
        PushLog.i("onMessageReceived=" + info.toString());
        info.setType(PushAction.RECEIVE_MESSAGE);
        sendPushData(context, PushAction.RECEIVE_MESSAGE, info);
    }

    /**
     * 接收到通知，会主动显示在通知栏的
     *
     * @param context
     * @param info
     */
    public void onNotificationReceived(Context context, ReceiverInfo info) {
        PushLog.i("onNotificationReceived=" + info.toString());
        info.setType(PushAction.RECEIVE_NOTIFICATION);
        sendPushData(context, PushAction.RECEIVE_NOTIFICATION, info);
    }

    /**
     * 用户点击了通知
     *
     * @param context
     * @param info
     */
    public void onNotificationOpened(Context context, ReceiverInfo info) {
        PushLog.i("onNotificationOpened=" + info.toString());
        info.setType(PushAction.RECEIVE_NOTIFICATION_CLICK);
        sendPushData(context, PushAction.RECEIVE_NOTIFICATION_CLICK, info);
    }

    /**
     * 登出后
     */
    public void onLoginOut(Context context, ReceiverInfo info) {
        PushLog.i("onLoginOut=" + info.toString());
        info.setType(PushAction.RECEIVE_LOGIN_OUT);
        sendPushData(context, PushAction.RECEIVE_LOGIN_OUT, info);
    }


    public static void sendPushData(Context context, String action, ReceiverInfo data) {
        Intent intent = new Intent(action);
        intent.putExtra(INTENT_RECEIVER_INFO, data);
        //ComponentName componentName = new ComponentName(context.getPackageName(),"com.saint.pushlib.handle.PushReceiverManager");
        intent.setPackage(context.getPackageName());
        context.sendBroadcast(intent);
    }

}
