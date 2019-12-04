package com.saint.pushlib.mipush;

import android.app.Activity;
import android.app.Application;
import android.text.TextUtils;

import com.saint.pushlib.BasePushInit;
import com.saint.pushlib.R;
import com.saint.pushlib.PushConstant;
import com.saint.pushlib.bean.ReceiverInfo;
import com.saint.pushlib.receiver.PushReceiverManager;
import com.saint.pushlib.util.ApplicationUtil;
import com.saint.pushlib.util.PushLog;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

public class MiPushInit extends BasePushInit {
    /**
     * 推送初始化
     *
     * @param isDebug     设置debug模式
     * @param application --
     */
    public MiPushInit(boolean isDebug, Application application) {
        super(isDebug, application);
        //注册SDK
        String appId = ApplicationUtil.getMetaData(application, "MIPUSH_APPID");
        String appKey = ApplicationUtil.getMetaData(application, "MIPUSH_APPKEY");
        if (TextUtils.isEmpty(appId) || TextUtils.isEmpty(appKey)) return;
        MiPushClient.registerPush(application
                , appId.replaceAll(" ", "")
                , appKey.replaceAll(" ", ""));
        if (isDebug) {
            LoggerInterface newLogger = new LoggerInterface() {
                @Override
                public void setTag(String tag) {
                    // ignore
                }

                @Override
                public void log(String content, Throwable t) {
                    PushLog.e(content, t);
                }

                @Override
                public void log(String content) {
                    PushLog.d(content);
                }
            };
            Logger.setLogger(application, newLogger);
        }
    }

    @Override
    public void loginIn(Activity activity) {
        ReceiverInfo info = new ReceiverInfo();
        info.setTitle(mContext.getString(R.string.get_token));
        info.setContent(MiPushClient.getRegId(activity));
        info.setPushType(PushConstant.XIAOMI);
        PushReceiverManager.getInstance().setToken(activity, info);
    }

    @Override
    public void setAlias(String alias) {
        MiPushClient.setAlias(mContext, alias, null);
    }

    @Override
    public void loginOut() {
        MiPushClient.unsetAlias(mContext, mAlias, null);
        ReceiverInfo aliasInfo = new ReceiverInfo();
        aliasInfo.setTitle(mContext.getString(R.string.tip_off_push_succeed));
        aliasInfo.setContent(mAlias);
        aliasInfo.setPushType(PushConstant.XIAOMI);
        PushReceiverManager.getInstance().onLoginOut(mContext, aliasInfo);
    }
}
