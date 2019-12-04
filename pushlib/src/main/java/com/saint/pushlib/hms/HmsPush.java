package com.saint.pushlib.hms;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.push.HmsMessaging;
import com.saint.pushlib.BasePushInit;
import com.saint.pushlib.R;
import com.saint.pushlib.PushConstant;
import com.saint.pushlib.bean.ReceiverInfo;
import com.saint.pushlib.receiver.PushReceiverManager;
import com.saint.pushlib.util.PushLog;

public class HmsPush extends BasePushInit {

    private String appId;
    private HmsInstanceId instanceId;

    /**
     * 推送初始化
     *
     * @param isDebug     设置debug模式
     * @param application --
     */
    public HmsPush(boolean isDebug, Application application) {
        super(isDebug, application);
        instanceId = HmsInstanceId.getInstance(application);
        appId = AGConnectServicesConfig.fromContext(application).getString("client/app_id");
        ReceiverInfo info = new ReceiverInfo();
        info.setPushType(PushConstant.HUAWEI);
        info.setContent("华为初始化ID：" + appId);
        info.setTitle(mContext.getString(R.string.init_succeed));
        PushReceiverManager.getInstance().onRegistration(application, info);
    }


    @Override
    public void loginIn(Activity activity) {
        super.loginIn(activity);
        getToken(activity);
    }

    @Override
    public void loginOut() {
        new Thread() {
            @Override
            public void run() {
                try {
                    HmsInstanceId.getInstance(mActivity).deleteToken(appId, "HCM");
                } catch (Exception e) {
                    PushLog.e("deleteToken failed.", e);
                }
            }
        }.start();
    }

    /**
     * get token
     */
    private void getToken(final Context context) {

        // get token
        new Thread() {
            @Override
            public void run() {
                try {
                    // read from agconnect-services.json
//                    String appId = AGConnectServicesConfig.fromContext(context).getString("client/app_id");
                    PushLog.e("华为ID：" + appId);
                    String pushtoken = instanceId.getToken(appId, "HCM");
                    if (!TextUtils.isEmpty(pushtoken)) {
                        PushLog.i("get token:" + pushtoken);
                    }
                } catch (Exception e) {
                    PushLog.e("getToken failed, " + e);

                }
            }
        }.start();
    }

    public void turnOnPush(Context context) {
        HmsMessaging.getInstance(context).turnOnPush().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()) {
                    PushLog.i("turnOnPush Complete");
                } else {
                    PushLog.i("turnOnPush failed: ret=" + task.getException().getMessage());
                }
            }
        });
    }

    public void turnOffPush(Context context) {
        HmsMessaging.getInstance(context).turnOffPush().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()) {
                    PushLog.i("turnOffPush Complete");
                } else {
                    PushLog.i("turnOffPush failed: ret=" + task.getException().getMessage());
                }
            }
        });
    }


}
