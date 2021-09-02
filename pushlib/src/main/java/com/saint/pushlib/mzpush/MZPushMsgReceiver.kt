package com.saint.pushlib.mzpush

import android.content.Context
import com.meizu.cloud.pushsdk.MzPushMessageReceiver
import com.meizu.cloud.pushsdk.handler.MzPushMessage
import com.meizu.cloud.pushsdk.platform.message.*
import com.saint.pushlib.util.PushLog

/**
 * @author Saint  2021/9/2 16:51
 * @DESC:
 */
class MZPushMsgReceiver:MzPushMessageReceiver() {
    override fun onRegisterStatus(p0: Context?, p1: RegisterStatus?) {
        PushLog.e("onRegisterStatus"+ p1.toString())
    }

    override fun onUnRegisterStatus(p0: Context?, p1: UnRegisterStatus?) {

    }

    override fun onPushStatus(p0: Context?, p1: PushSwitchStatus?) {

    }

    override fun onSubTagsStatus(p0: Context?, p1: SubTagsStatus?) {

    }

    override fun onSubAliasStatus(p0: Context?, p1: SubAliasStatus?) {

    }

    override fun onNotificationArrived(p0: Context?, p1: MzPushMessage?) {
        super.onNotificationArrived(p0, p1)
        PushLog.e("onNotificationArrived"+ p1.toString())
    }

    override fun onNotificationClicked(p0: Context?, p1: MzPushMessage?) {
        super.onNotificationClicked(p0, p1)
        PushLog.e("onNotificationClicked" + p1.toString())
    }
}