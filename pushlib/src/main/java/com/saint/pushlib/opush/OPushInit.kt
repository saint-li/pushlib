package com.saint.pushlib.opush

import android.app.Application
import android.text.TextUtils
import com.heytap.mcssdk.PushManager
import com.heytap.mcssdk.callback.PushAdapter
import com.saint.pushlib.BasePushInit
import com.saint.pushlib.PushConstant
import com.saint.pushlib.PushControl.init
import com.saint.pushlib.PushControl.setEnableOPPOPush
import com.saint.pushlib.R
import com.saint.pushlib.bean.ReceiverInfo
import com.saint.pushlib.receiver.PushReceiverManager
import com.saint.pushlib.util.PushLog.Companion.i
import com.saint.pushlib.util.PushUtil.getMetaData

class OPushInit(isDebug: Boolean, application: Application) : BasePushInit(isDebug, application) {
    override fun loginIn() {
        var registerID = PushManager.getInstance().registerID
        if (TextUtils.isEmpty(registerID)) {
            PushManager.getInstance().getRegister()
            registerID = PushManager.getInstance().registerID
        }
        val info = ReceiverInfo()
        info.title = mContext.getString(R.string.get_token)
         info.content = registerID
        info.pushType = PushConstant.OPPO
        PushReceiverManager.setToken(mContext, info)
    }

    /**
     * 推送初始化
     *
     * @param isDebug     设置debug模式
     * @param application --
     */
    init {
        val appSecret = getMetaData(application!!, "OPPO_SECRET")
        val appKey = getMetaData(application, "OPPO_APPKEY")
        i("appSecret:$appSecret appKey:$appKey")
        if (!TextUtils.isEmpty(appKey) && !TextUtils.isEmpty(appSecret)) {
            PushManager.getInstance().register(application, appKey, appSecret, PushAdapter())
            val info = ReceiverInfo()
            info.pushType = PushConstant.OPPO
            info.title = "OPPO推送"
            info.content = mContext.getString(R.string.init_succeed)
            PushReceiverManager.onRegistration(application, info)
        } else {
            PushManager.getInstance().register(application, appKey, appSecret, PushAdapter())
            val info = ReceiverInfo()
            info.pushType = PushConstant.OPPO
            info.title = "OPPO推送"
            info.content = mContext.getString(R.string.init_failed)
            setEnableOPPOPush(false)
            init(isDebug, application)
        }
    }
}