package com.saint.pushlib.mzpush

import android.app.Application
import android.text.TextUtils
import com.meizu.cloud.pushsdk.PushManager
import com.saint.pushlib.BasePushInit
import com.saint.pushlib.PushConstant
import com.saint.pushlib.R
import com.saint.pushlib.util.PushLog
import com.saint.pushlib.util.PushUtil

/**
 * @author Saint  2021/9/2 16:40
 * @DESC: 魅族推送初始化
 */
class MZPushInit(isDebug: Boolean, application: Application) : BasePushInit(isDebug, application) {
    /**
     * 推送初始化
     *
     * @param isDebug     设置debug模式
     * @param application --
     */
    init {
        val appid = PushUtil.getMetaData(application, "MEIZU_APPID")
        val appKey = PushUtil.getMetaData(application, "MEIZU_APPKEY")
        PushLog.i("appid:$appid appKey:$appKey")
        if (!TextUtils.isEmpty(appKey) && !TextUtils.isEmpty(appid)) {
            PushManager.register(application, appid, appKey)
        } else {
            initFailed(
                getString(R.string.MEIZU),
                PushConstant.MEIZU,
                "MEIZU_APPKEY=$appKey,appid=$appid"
            )
        }
    }
}