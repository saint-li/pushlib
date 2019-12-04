package com.saint.pushlib.opush;

import android.content.Context;

import com.heytap.mcssdk.PushService;
import com.heytap.mcssdk.mode.AppMessage;
import com.heytap.mcssdk.mode.CommandMessage;
import com.heytap.mcssdk.mode.SptDataMessage;
import com.saint.pushlib.PushConstant;
import com.saint.pushlib.bean.ReceiverInfo;
import com.saint.pushlib.receiver.PushReceiverManager;
import com.saint.pushlib.util.PushLog;

public class OppoPushService extends PushService {

    /**
     * 普通应用消息
     *
     * @param context
     * @param appMessage
     */
    @Override
    public void processMessage(Context context, AppMessage appMessage) {
        PushLog.e("app processMessage: handle:" + appMessage);
        ReceiverInfo info = new ReceiverInfo();
        info.setTitle(appMessage.getTitle());
        info.setContent(appMessage.getContent());
        info.setPushType(PushConstant.OPPO);
        PushReceiverManager.getInstance().onNotificationReceived(context, info);
    }

    /**
     * 透传消息处理，应用可以打开页面或者执行命令,如果应用不需要处理透传消息，则不需要重写此方法
     *
     * @param context
     * @param sptDataMessage
     */
    @Override
    public void processMessage(Context context, SptDataMessage sptDataMessage) {
        PushLog.e("spt processMessage: handle:" + sptDataMessage);
        ReceiverInfo info = new ReceiverInfo();
        info.setContent(sptDataMessage.getContent());
        info.setExtra(sptDataMessage.getDescription());
        info.setPushType(PushConstant.OPPO);
        PushReceiverManager.getInstance().onMessageReceived(context, info);
    }

    /**
     * 命令消息，主要是服务端对客户端调用的反馈，一般应用不需要重写此方法
     *
     * @param context
     * @param commandMessage
     */
    @Override
    public void processMessage(Context context, CommandMessage commandMessage) {
        super.processMessage(context, commandMessage);
        PushLog.e("command processMessage: " + commandMessage);
    }
}
