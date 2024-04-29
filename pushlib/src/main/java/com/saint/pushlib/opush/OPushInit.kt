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

        /**
         * 注册的结果,如果注册成功,registerID就是客户端的唯一身份标识
         *
         * @param responseCode 接口执行结果码，0表示接口执行成功
         * @param registerID   注册id/token
         * @param packageName 如果当前执行注册的应用是常规应用，则通过packageName返回當前應用对应的包名
         * @param miniPackageName  如果當前是快應用進行push registerID的注冊，則通過miniPackageName進行標識快應用包名
         */
        override fun onRegister(responseCode: Int, registerID: String, packageName: String, miniPackageName: String) {
            if (responseCode == 0) {
                showResult("获取RegisterId成功", "registerId:$registerID")
                onToken(registerID, OPPO)
            } else {
                showResult("注册失败", "code=$responseCode,msg=$registerID")
                initFailed(getString(R.string.OPPO), OPPO, "code=$responseCode,msg=$registerID")
            }
        }

        /**
         * 应用注销结果回调接口，将应用请求服务端的注销接口进行结果传达
         * @param responseCode 接口执行结果码，0标识接口执行成功
         * @param packageName  当前注销的应用的包名
         * @param miniProgramPkg  如果是快应用注销，则会将快应用的包名一起返回给业务方(一般是快应用中心，由快应用中心进行分发)
         */
        override fun onUnRegister(responseCode: Int, packageName: String, miniProgramPkg: String) {
            if (responseCode == 0) {
                showResult("注销成功", "code=$responseCode")
            } else {
                showResult("注销失败", "code=$responseCode")
            }
        }
        //获取设置推送时间的执行结果
        override fun onSetPushTime(code: Int, s: String) {
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

        /**
         * 异常处理的回调
         * @param errorCode   错误码
         * @param message     错误信息
         * @param packageName 当前注册失败的应用包名，如果是应用注册，则返回应用注册包名，如果是為快應用做接口請求，则这里返回的是快應用中心的包名
         * @param miniProgramPkg 当前注册失败的快應用包名
         */
        override fun onError(errorCode: Int, message: String?, packageName: String, miniProgramPkg: String) {
            showResult("onError", "onError errorCode = $errorCode   msg = $message")
        }


    }

    /**
     * 此方法会将结果进行回显
     */
    private fun showResult(@Nullable tag: String, @NonNull msg: String) {
        i("$tag:$msg")
    }

}