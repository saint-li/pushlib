package com.saint.push_lib;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.saint.pushlib.bean.ReceiverInfo;
import com.saint.pushlib.receiver.BasePushReceiver;

public class MyPushReceiver extends BasePushReceiver {
    private final String TAG = "PUSH_LOG";

    @Override
    public void onReceiveNotification(Context context, ReceiverInfo info) {
        Log.e(TAG, "onReceiveNotification：" + info.toString());
    }

    @Override
    public void onReceiveNotificationClick(Context context, ReceiverInfo info) {
        if (info == null || TextUtils.isEmpty(info.getExtra())) {
            Log.e(TAG, "NotificationClick为空或者Extra为空");
            return;
        }
        Log.e(TAG, "onReceiveNotificationClick：" + info.toString());

    }

    @Override
    public void onReceiveMessage(Context context, ReceiverInfo info) {
        if (info == null || TextUtils.isEmpty(info.getExtra())) {
            Log.e(TAG, "ReceiveMessage为空或者Extra为空");
            return;
        }
        Log.e(TAG, "onReceiveMessage：" + info.toString());
    }

    @Override
    public void onTokenSet(Context context, ReceiverInfo info) {
        Log.e(TAG, "onTokenSet：" + info.toString());
    }

    @Override
    public void onInitResult(Context context, ReceiverInfo info) {
        Log.e(TAG, "onInitResult：" + info.toString());
    }

    @Override
    public void onSetAlias(Context context, ReceiverInfo info) {
        Log.e(TAG, "onSetAlias：" + info.toString());
    }

    @Override
    public void onLoginOut(Context context, ReceiverInfo info) {
        Log.e(TAG, "onLoginOut：" + info.toString());
    }
}
