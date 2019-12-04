package com.saint.pushlib.jpush;

import android.app.Activity;
import android.app.Application;

import com.saint.pushlib.BasePushInit;
import com.saint.pushlib.PushControl;
import com.saint.pushlib.R;
import com.saint.pushlib.PushConstant;
import com.saint.pushlib.bean.ReceiverInfo;
import com.saint.pushlib.receiver.PushReceiverManager;

import cn.jpush.android.api.JPushInterface;

public class JPushInit extends BasePushInit {

    /**
     * 推送初始化
     *
     * @param isDebug     设置debug模式
     * @param application --
     */
    public JPushInit(boolean isDebug, Application application) {
        super(isDebug, application);
        JPushInterface.setDebugMode(isDebug);
        JPushInterface.init(application);
        ReceiverInfo info = new ReceiverInfo();
        info.setPushType(PushConstant.JPUSH);
        info.setContent("极光初始化");
        info.setTitle(mContext.getString(R.string.init_succeed));
        PushReceiverManager.getInstance().onRegistration(application, info);
    }

    @Override
    public void loginIn(Activity activity) {
        super.loginIn(activity);
        ReceiverInfo info = new ReceiverInfo();
        info.setTitle(mContext.getString(R.string.get_token));
        info.setContent(JPushInterface.getRegistrationID(activity));
        info.setPushType(PushConstant.JPUSH);
        PushReceiverManager.getInstance().setToken(PushControl.getInstance().getApplication(), info);
    }

    @Override
    public void setAlias(String alias) {
        super.setAlias(alias);
        JPushInterface.setAlias(mContext, 0, alias);
    }

    @Override
    public void loginOut() {
        JPushInterface.deleteAlias(mContext, 0);
        ReceiverInfo aliasInfo = new ReceiverInfo();
        aliasInfo.setTitle(mContext.getString(R.string.tip_off_push_succeed));
        aliasInfo.setContent(mAlias);
        aliasInfo.setPushType(PushConstant.JPUSH);
        PushReceiverManager.getInstance().onLoginOut(mContext, aliasInfo);
    }
}
