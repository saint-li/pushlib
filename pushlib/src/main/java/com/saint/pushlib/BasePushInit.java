package com.saint.pushlib;

import android.app.Activity;
import android.app.Application;
import android.content.Context;


/**
 * 初始化推送平台的基类
 */

public abstract class BasePushInit {
    protected static int MAX_RETRY_COUNT = 5;
    protected Application mApplication;
    protected Context mContext;
    protected String mAlias;
    protected Activity mActivity;

    /**
     * 推送初始化
     *
     * @param isDebug     设置debug模式
     * @param application --
     */
    public BasePushInit(boolean isDebug, Application application) {
        this.mApplication = application;
        this.mContext = mApplication.getApplicationContext();
    }

    /**
     * 设置别名
     *
     * @param alias
     */
    public void setAlias(String alias) {
        this.mAlias = alias;
    }

    public void loginOut() {
    }

    public void loginIn(Activity activity) {
        this.mActivity = activity;
    }
}
