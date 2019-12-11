package com.saint.pushlib.mipush;

import android.content.Context;

import com.saint.pushlib.R;
import com.saint.pushlib.PushConstant;
import com.saint.pushlib.bean.ReceiverInfo;
import com.saint.pushlib.receiver.PushReceiverManager;
import com.saint.pushlib.util.PushLog;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

/**
 * 1、PushMessageReceiver 是个抽象类，该类继承了 BroadcastReceiver。<br/>
 * 2、需要将自定义的 DemoMessageReceiver 注册在 AndroidManifest.xml 文件中：
 * <pre>
 * {@code
 *  <receiver
 *      android:name="com.xiaomi.mipushdemo.DemoMessageReceiver"
 *      android:exported="true">
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.RECEIVE_MESSAGE" />
 *      </intent-filter>
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.MESSAGE_ARRIVED" />
 *      </intent-filter>
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.ERROR" />
 *      </intent-filter>
 *  </receiver>
 *  }</pre>
 * 3、DemoMessageReceiver 的 onReceivePassThroughMessage 方法用来接收服务器向客户端发送的透传消息。<br/>
 * 4、DemoMessageReceiver 的 onNotificationMessageClicked 方法用来接收服务器向客户端发送的通知消息，
 * 这个回调方法会在用户手动点击通知后触发。<br/>
 * 5、DemoMessageReceiver 的 onNotificationMessageArrived 方法用来接收服务器向客户端发送的通知消息，
 * 这个回调方法是在通知消息到达客户端时触发。另外应用在前台时不弹出通知的通知消息到达客户端也会触发这个回调函数。<br/>
 * 6、DemoMessageReceiver 的 onCommandResult 方法用来接收客户端向服务器发送命令后的响应结果。<br/>
 * 7、DemoMessageReceiver 的 onReceiveRegisterResult 方法用来接收客户端向服务器发送注册命令后的响应结果。<br/>
 * 8、以上这些方法运行在非 UI 线程中。
 */

public class MiPushReceiver extends PushMessageReceiver {

    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage miPushMessage) {
        PushReceiverManager.getInstance().onMessageReceived(context, convert2ReceiverInfo(miPushMessage));
    }


    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage miPushMessage) {
        PushReceiverManager.getInstance().onNotificationOpened(context, convert2ReceiverInfo(miPushMessage));
    }


    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage miPushMessage) {
        PushReceiverManager.getInstance().onNotificationReceived(context, convert2ReceiverInfo(miPushMessage));
    }

    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        String command = message.getCommand();
        String log;
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                log = context.getString(R.string.init_succeed);
            } else {
                log = context.getString(R.string.init_failed);
            }
            ReceiverInfo info = convert2ReceiverInfo(message);
            info.setTitle("小米推送");
            info.setContent(log);
            PushReceiverManager.getInstance().onRegistration(context, info);
        }
    }

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
        super.onCommandResult(context, message);
        PushLog.i("XiaomiBroadcastReceiver :onCommandResult = var2=" + message.toString());
        String command = message.getCommand();

        if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                ReceiverInfo info = convert2ReceiverInfo(message);
                info.setTitle(context.getString(R.string.tip_setalias));
                info.setContent(message.getCommandArguments().get(0));
                PushReceiverManager.getInstance().onAliasSet(context, info);
            }
        }

    }

    /**
     * 将intent的数据转化为ReceiverInfo用于处理
     *
     * @param miPushMessage
     * @return
     */
    private ReceiverInfo convert2ReceiverInfo(MiPushMessage miPushMessage) {
        ReceiverInfo info = new ReceiverInfo();
        info.setContent(miPushMessage.getContent());
        info.setPushType(PushConstant.XIAOMI);
        info.setTitle(miPushMessage.getTitle());
        if (miPushMessage.getContent() != null) {
            info.setExtra(miPushMessage.getContent());
        }
        return info;
    }

    /**
     * 将intent的数据转化为ReceiverInfo用于处理
     *
     * @param miPushCommandMessage
     * @return
     */
    private ReceiverInfo convert2ReceiverInfo(MiPushCommandMessage miPushCommandMessage) {
        ReceiverInfo info = new ReceiverInfo();
        info.setContent(miPushCommandMessage.getCommand());
        info.setPushType(PushConstant.XIAOMI);
        return info;
    }

}
