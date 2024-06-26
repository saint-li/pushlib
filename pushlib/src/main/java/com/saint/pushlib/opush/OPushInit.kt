package com.saint.pushlib.opush

import android.app.Application
import android.text.TextUtils
import com.heytap.msp.push.HeytapPushManager
import com.heytap.msp.push.callback.ICallBackResultService
import com.saint.pushlib.BasePushInit
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
            HeytapPushManager.register(application, appKey, appSecret, oPushCallBack)
        } else {
            initFailed(getString(R.string.OPPO), OPPO, "OPPO_APPKEY=$appKey,appSecret=$appSecret")
        }
    }

    private val oPushCallBack = object : ICallBackResultService {
        override fun onRegister(responseCode: Int, registerID: String?, packageName: String?, miniPackageName: String?) {
            if (responseCode == 0 && !TextUtils.isEmpty(registerID)) {
                showResult("获取RegisterId成功", "registerId:$registerID")
                onToken(registerID!!, OPPO)
            } else {
                showResult("注册失败", "code=$responseCode,msg=$registerID")
                initFailed(getString(R.string.OPPO), OPPO, "code=$responseCode,msg=$registerID")
            }
        }

        override fun onUnRegister(responseCode: Int, packageName: String?, miniProgramPkg: String?) {
            if (responseCode == 0) {
                showResult("注销成功", "code=$responseCode")
            } else {
                showResult("注销失败", "code=$responseCode")
            }
        }

        override fun onSetPushTime(code: Int, s: String?) {
            showResult("SetPushTime", "code=$code,result:$s")
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

        override fun onError(errorCode: Int, message: String?, packageName: String?, miniProgramPkg: String?) {
            showResult("onError", "onError errorCode = $errorCode   msg = $message")
        }
    }

    /**
     * 此方法会将结果进行回显
     */
    private fun showResult(tag: String, msg: String) {
        i("$tag:$msg")
    }

}