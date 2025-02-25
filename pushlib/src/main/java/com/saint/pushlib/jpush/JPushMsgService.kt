package com.saint.pushlib.jpush

import android.content.Context
import cn.jpush.android.api.CustomMessage
import cn.jpush.android.api.NotificationMessage
import cn.jpush.android.service.JPushMessageReceiver
import com.saint.pushlib.PushConstant
import com.saint.pushlib.bean.ReceiverInfo
import com.saint.pushlib.receiver.PushReceiverManager
import com.saint.pushlib.util.PushLog.Companion.i

class JPushMsgService: JPushMessageReceiver() {
    override fun onRegister(context: Context, token: String) {
        super.onRegister(context, token)
        i("极光ID注册： $token")
        //获取token成功，token用于标识设备的唯一性
        val info = ReceiverInfo()
        info.content = token
        info.pushType = PushConstant.JPUSH
        PushReceiverManager.setToken(context, info)
    }

    override fun onConnected(p0: Context?, p1: Boolean) {
        super.onConnected(p0, p1)
        i("极光链接是否成功：$p1")
    }

    override fun onMessage(p0: Context?, customMessage: CustomMessage?) {
        super.onMessage(p0, customMessage)
        i("OnCstMsg: $customMessage")
    }
    override fun onNotifyMessageArrived(context: Context, message: NotificationMessage) {
        super.onNotifyMessageArrived(context, message)
        i("极光通知Arrived：$message")
        PushReceiverManager.onMessageReceived(context, convertToReceiverInfo(message))
    }

    override fun onNotifyMessageOpened(context: Context, message: NotificationMessage) {
        super.onNotifyMessageOpened(context, message)
        i("极光通知Opened：$message")
        PushReceiverManager.onNotificationOpened(context, convertToReceiverInfo(message))
    }
    /**
     * 将NotificationMessage的数据转化为ReceiverInfo用于处理
     *
     * @param notificationMessage
     * @return
     */
    private fun convertToReceiverInfo(notificationMessage: NotificationMessage): ReceiverInfo {
        val info = ReceiverInfo()
        info.title = notificationMessage.notificationTitle
        info.content = notificationMessage.notificationContent
        info.extra =  notificationMessage.notificationExtras
        info.pushType = PushConstant.JPUSH
        return info
    }
}