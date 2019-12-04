package com.saint.pushlib.hms;


import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.RemoteMessage;
import com.saint.pushlib.PushControl;
import com.saint.pushlib.PushConstant;
import com.saint.pushlib.R;
import com.saint.pushlib.bean.ReceiverInfo;
import com.saint.pushlib.receiver.PushReceiverManager;
import com.saint.pushlib.util.PushLog;

public class HmsMsgService extends HmsMessageService {

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        PushLog.i("华为pushToken: " + token);
        //获取token成功，token用于标识设备的唯一性
        ReceiverInfo info = createReceiverInfo();
        info.setTitle(getApplicationContext().getString(R.string.get_token));
        info.setContent(token);
        info.setPushType(PushConstant.HUAWEI);
        PushReceiverManager.getInstance().setToken(PushControl.getInstance().getApplication(), info);
    }


    @Override
    public void onMessageReceived(RemoteMessage message) {
//        super.onMessageReceived(message);
        PushLog.i("onMessageReceived is called");

        if (message.getData().length() > 0) {
            PushLog.i("Message data payload: " + message.getData());
            ReceiverInfo info = createReceiverInfo();
            info.setContent(message.getData());
            PushReceiverManager.getInstance().onMessageReceived(PushControl.getInstance().getApplication(), info);
        }
        // Check if this message contains a notification payload.
        if (message.getNotification() != null) {
            PushLog.i("Message Notification Body: " + message.getNotification().getBody());
        }
        // TODO: your's other processing logic
    }


    private ReceiverInfo createReceiverInfo() {
        ReceiverInfo info = new ReceiverInfo();
        info.setPushType(PushConstant.HUAWEI);
        return info;
    }
}
