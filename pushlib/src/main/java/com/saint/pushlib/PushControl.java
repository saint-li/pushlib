package com.saint.pushlib;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.heytap.mcssdk.PushManager;
import com.saint.pushlib.hms.HmsPush;
import com.saint.pushlib.jpush.JPushInit;
import com.saint.pushlib.mipush.MiPushInit;
import com.saint.pushlib.opush.OPushInit;
import com.saint.pushlib.util.PushLog;
import com.saint.pushlib.util.RomUtil;

import static com.saint.pushlib.PushConstant.*;

public class PushControl {
    private static PushControl instance;

//    private static final int JPUSH = 0, HUAWEI = 1, OPPO = 2, XIAOMI = 3, VIVO = 4;

    /**
     * 当前的推送平台，默认为极光 JPUSH
     */
    private BasePushInit mPushTarget;

    private boolean enableHWPush = true;
    private boolean enableMiPush = true;
    private boolean enableOPPOPush = true;

    private Application application;

    private PushControl() {
    }

    public static PushControl getInstance() {
        if (instance == null) {
            synchronized (PushControl.class) {
                if (instance == null) {
                    instance = new PushControl();
                }
            }
        }
        return instance;
    }

    public Application getApplication() {
        return application;
    }

    public void init(boolean isDebug, Application context) {
        this.application = context;
        //日志打印
        PushLog.setDebug(isDebug);
/**
 * 根据设备厂商和使用者设置来选择推送平台,小米的使用小米推送，华为使用华为推送...其他的使用极光推送
 */
        switch (getPhoneType(context)) {
            case XIAOMI:
                mPushTarget = new MiPushInit(isDebug, context);
                break;
            case HUAWEI:
                mPushTarget = new HmsPush(isDebug, context);
                break;
            case OPPO:
                mPushTarget = new OPushInit(isDebug, context);
                break;
//            case VIVO:
//                mPushTarget = new VivoInit(context);
//                break;
            case JPUSH:
                mPushTarget = new JPushInit(isDebug, context);
                break;
            default:
                mPushTarget = new JPushInit(isDebug, context);
                break;
        }
    }

    public void init(boolean isDebug, Application application, boolean enableHWPush, boolean enableMiPush, boolean enableOPPOPush) {
        this.enableHWPush = enableHWPush;
        this.enableMiPush = enableMiPush;
        this.enableOPPOPush = enableOPPOPush;
        init(isDebug, application);
    }

    public int getPhoneType(Context context) {
        int phoneType;
        if (RomUtil.isHuaweiRom() && enableHWPush) {
            phoneType = HUAWEI;
        } else if (RomUtil.isMiuiRom() && enableMiPush) {
            phoneType = XIAOMI;
        } else if (RomUtil.isOPPORom() && PushManager.isSupportPush(context) && enableOPPOPush) {
            phoneType = OPPO;
        } else {
            phoneType = JPUSH;
        }

//         else if (RomUtil.isVivoRom() && PushClient.getInstance(context).isSupport() && enableVivoPush) {
//            phoneType = VIVO;
//        }
//
        PushLog.e("当前手机类型： " + phoneType);
        return phoneType;
    }

    /**
     * 登录，华为登录需要在activity中
     *
     * @param activity
     */
    public void loginIn(Activity activity) {
        mPushTarget.loginIn(activity);
    }

    /**
     * 登出，登出后，设置alias为空，或者传递token给服务器为空
     */
    public void loginOut() {
        mPushTarget.loginOut();
    }

    public void setEnableHWPush(boolean enableHWPush) {
        this.enableHWPush = enableHWPush;
    }

    public void setEnableMiPush(boolean enableMiPush) {
        this.enableMiPush = enableMiPush;
    }

    public void setEnableOPPOPush(boolean enableOPPOPush) {
        this.enableOPPOPush = enableOPPOPush;
    }

    //    public void turnOnHmsPush(Context context) {
//        if (mPushTarget instanceof HmsPush) {
//            ((HmsPush) mPushTarget).turnOnPush(context);
//        }
//    }
//
//    public void turnOffHmsPush(Context context) {
//        if (mPushTarget instanceof HmsPush) {
//            ((HmsPush) mPushTarget).turnOffPush(context);
//        }
//    }
}
