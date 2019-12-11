package com.saint.pushlib.opush;

import android.app.Activity;
import android.app.Application;
import android.text.TextUtils;

import com.heytap.mcssdk.PushManager;
import com.heytap.mcssdk.callback.PushAdapter;
import com.saint.pushlib.BasePushInit;
import com.saint.pushlib.PushConstant;
import com.saint.pushlib.PushControl;
import com.saint.pushlib.R;
import com.saint.pushlib.bean.ReceiverInfo;
import com.saint.pushlib.receiver.PushReceiverManager;
import com.saint.pushlib.util.ApplicationUtil;
import com.saint.pushlib.util.PushLog;

public class OPushInit extends BasePushInit {
    /**
     * 推送初始化
     *
     * @param isDebug     设置debug模式
     * @param application --
     */
    public OPushInit(boolean isDebug, Application application) {
        super(isDebug, application);
        String appSecret = ApplicationUtil.getMetaData(application, "OPPO_SECRET");
        String appKey = ApplicationUtil.getMetaData(application, "OPPO_APPKEY");
        PushLog.e("appSecret:" + appSecret + " appKey:" + appKey);
        if (!TextUtils.isEmpty(appKey) && !TextUtils.isEmpty(appSecret)) {
            PushManager.getInstance().register(application, appKey, appSecret, new PushAdapter());
            ReceiverInfo info = new ReceiverInfo();
            info.setPushType(PushConstant.OPPO);
            info.setTitle("OPPO推送");
            info.setContent(mContext.getString(R.string.init_succeed));
            PushReceiverManager.getInstance().onRegistration(application, info);
        } else {
            PushManager.getInstance().register(application, appKey, appSecret, new PushAdapter());
            ReceiverInfo info = new ReceiverInfo();
            info.setPushType(PushConstant.OPPO);
            info.setTitle("OPPO推送");
            info.setContent(mContext.getString(R.string.init_failed));
            PushControl.getInstance().setEnableOPPOPush(false);
            PushControl.getInstance().init(isDebug, application);
        }
    }

    @Override
    public void loginIn(Activity activity) {
        String registerID = PushManager.getInstance().getRegisterID();
        if (TextUtils.isEmpty(registerID)) {
            PushManager.getInstance().getRegister();
            registerID = PushManager.getInstance().getRegisterID();
        }
        ReceiverInfo info = new ReceiverInfo();
        info.setTitle(mContext.getString(R.string.get_token));
        info.setContent(registerID);
        info.setPushType(PushConstant.OPPO);
        PushReceiverManager.getInstance().setToken(activity, info);
    }
}
