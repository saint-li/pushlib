package com.saint.pushlib.jpush

import android.app.Application
import cn.jpush.android.api.JPushInterface
import com.saint.pushlib.BasePushInit
import com.saint.pushlib.PushConstant
import com.saint.pushlib.PushConstant.JPUSH
import com.saint.pushlib.PushControl.app
import com.saint.pushlib.R
import com.saint.pushlib.bean.ReceiverInfo
import com.saint.pushlib.receiver.PushReceiverManager

class JPushInit(isDebug: Boolean, application: Application) : BasePushInit(isDebug, application) {

    /**
     * 推送初始化
     *
     * @param isDebug     设置debug模式
     * @param application --
     */
    init {
        JPushInterface.setDebugMode(isDebug)
        JPushInterface.init(application)
        initSucceed(getString(R.string.JPUSH), JPUSH)
    }

    override fun loginIn() {
        onToken(JPushInterface.getRegistrationID(mContext), JPUSH)
    }

    override fun setAlias(alias: String?) {
        super.setAlias(alias)
        JPushInterface.setAlias(mContext, 0, alias)
    }

    override fun loginOut() {
        JPushInterface.deleteAlias(mContext, 0)
        val aliasInfo = ReceiverInfo()
        aliasInfo.title = mContext.getString(R.string.tip_off_push_succeed)
        if (mAlias != null) aliasInfo.content = mAlias!!
        aliasInfo.pushType = PushConstant.JPUSH
        PushReceiverManager.onLoginOut(mContext, aliasInfo)
    }

}