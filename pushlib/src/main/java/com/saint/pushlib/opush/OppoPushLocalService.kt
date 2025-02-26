//package com.saint.pushlib.opush
//
//import android.content.Context
//import com.heytap.msp.push.mode.DataMessage
//import com.heytap.msp.push.service.DataMessageCallbackService
//import com.saint.pushlib.PushConstant
//import com.saint.pushlib.bean.ReceiverInfo
//import com.saint.pushlib.receiver.PushReceiverManager
//import com.saint.pushlib.util.PushLog
//
///**
// * @author Saint  2022/5/12 17:36
// * @DESC:
// */
//class OppoPushLocalService : DataMessageCallbackService() {
//    override fun processMessage(context: Context, message: DataMessage) {
//        super.processMessage(context, message)
//        PushLog.e("spt processMessage: handle:$message")
//        val info = ReceiverInfo()
//        info.title = message.title
//        info.content = message.content
//        info.extra = message.dataExtra
//        info.pushType = PushConstant.OPPO
//        PushReceiverManager.onNotificationReceived(context, info)
//    }
//}