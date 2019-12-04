package com.saint.pushlib.jpush;

import android.content.Context;

import com.saint.pushlib.PushControl;
import com.saint.pushlib.PushConstant;
import com.saint.pushlib.bean.ReceiverInfo;
import com.saint.pushlib.receiver.PushReceiverManager;
import com.saint.pushlib.util.PushLog;

import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

public class JPushReceiver extends JPushMessageReceiver {


    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onTagOperatorResult(context, jPushMessage);
    }

    @Override
    public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onCheckTagOperatorResult(context, jPushMessage);
    }

    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onAliasOperatorResult(context, jPushMessage);
    }

    @Override
    public void onMobileNumberOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onMobileNumberOperatorResult(context, jPushMessage);
    }

    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageArrived(context, notificationMessage);
        PushLog.i("极光通知Arrived：" + notificationMessage.toString());
        PushReceiverManager.getInstance().onMessageReceived(context, convertToReceiverInfo(notificationMessage));
    }

    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageOpened(context, notificationMessage);
        PushLog.i("极光通知Opened：" + notificationMessage.toString());
        PushReceiverManager.getInstance().onNotificationOpened(context, convertToReceiverInfo(notificationMessage));
    }

    @Override
    public void onRegister(Context context, String token) {
        super.onRegister(context, token);
        PushLog.i("极光ID注册： " + token);
        //获取token成功，token用于标识设备的唯一性
        ReceiverInfo info = new ReceiverInfo();
        info.setContent(token);
        info.setPushType(PushConstant.JPUSH);
        PushReceiverManager.getInstance().setToken(PushControl.getInstance().getApplication(), info);
    }

    @Override
    public void onConnected(Context context, boolean b) {
        super.onConnected(context, b);
        PushLog.i("极光链接是否成功：" + b);
    }

    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        super.onMessage(context, customMessage);
        PushLog.i("OnCstMsg: " + customMessage.toString());
    }


    /**
     * 将NotificationMessage的数据转化为ReceiverInfo用于处理
     *
     * @param notificationMessage
     * @return
     */
    private ReceiverInfo convertToReceiverInfo(NotificationMessage notificationMessage) {
        ReceiverInfo info = new ReceiverInfo();

        info.setTitle(notificationMessage.notificationTitle);
        info.setContent(notificationMessage.notificationContent);
        info.setExtra(notificationMessage.notificationExtras);
        info.setPushType(PushConstant.JPUSH);
        return info;
    }
}
