package com.saint.pushlib.opush

import android.app.Application
import android.text.TextUtils
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.heytap.msp.push.HeytapPushManager
import com.heytap.msp.push.callback.ICallBackResultService
import com.saint.pushlib.BasePushInit
import com.saint.pushlib.PushConstant
import com.saint.pushlib.PushConstant.OPPO
import com.saint.pushlib.R
import com.saint.pushlib.util.PushLog.Companion.i
import com.saint.pushlib.util.PushUtil.getMetaData

class OPushInit(isDebug: Boolean, private val application: Application) : BasePushInit(isDebug, application) {
    private val appSecret = getMetaData(application, "OPPO_SECRET")
    private val appKey = getMetaData(application, "OPPO_APPKEY")

    /**
     * 推送初始化
     *
     * @param isDebug     设置debug模式
     * @param application --
     */
    init {
        if (!TextUtils.isEmpty(appKey) && !TextUtils.isEmpty(appSecret)) {
            HeytapPushManager.init(application, isDebug)
        } else {
            initFailed(getString(R.string.OPPO), OPPO, "OPPO_APPKEY=$appKey,appSecret=$appSecret")
        }
    }

    override fun loginIn() {
        if (!TextUtils.isEmpty(appKey) && !TextUtils.isEmpty(appSecret)) {
            HeytapPushManager.register(application, appKey, appSecret, OPushRegisterCallBack())
        } else {
            initFailed(getString(R.string.OPPO), OPPO, "OPPO_APPKEY=$appKey,appSecret=$appSecret")
        }
    }

    inner class OPushRegisterCallBack : ICallBackResultService {

        override fun onRegister(code: Int, s: String) {
            if (code == 0) {
                showResult("获取RegisterId成功", "registerId:$s")
                onToken(s, OPPO)
            } else {
                showResult("注册失败", "code=$code,msg=$s")
                initFailed(getString(R.string.OPPO), OPPO, "code=$code,msg=$s")
            }
        }

        override fun onUnRegister(code: Int) {
            if (code == 0) {
                showResult("注销成功", "code=$code")
            } else {
                showResult("注销失败", "code=$code")
            }
        }

        override fun onGetPushStatus(code: Int, status: Int) {
            if (code == 0 && status == 0) {
                showResult("Push状态正常", "code=$code,status=$status")
            } else {
                showResult("Push状态错误", "code=$code,status=$status")
            }
        }

        override fun onGetNotificationStatus(code: Int, status: Int) {
            if (code == 0 && status == 0) {
                showResult("通知状态正常", "code=$code,status=$status")
            } else {
                showResult("通知状态错误", "code=$code,status=$status")
            }
        }

        override fun onError(code: Int, s: String?) {
            showResult("onError", "onError code = $code   msg = $s")
        }

        override fun onSetPushTime(code: Int, s: String) {
            showResult("SetPushTime", "code=$code,result:$s")
        }

    }

    /**
     * 此方法会将结果进行回显
     */
    private fun showResult(@Nullable tag: String, @NonNull msg: String) {
        i("$tag:$msg")
    }

}