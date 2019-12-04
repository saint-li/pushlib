package com.saint.pushlib.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;


public class ApplicationUtil {
    /**
     * 获取Manifest 里面的meta-data
     *
     * @param context
     * @param key
     * @return
     */
    public static String getMetaData(Context context, String key) {
//        ApplicationInfo ai = null;
//        try {
//            ai = context.getPackageManager().getApplicationInfo(context.getPackageName(),
//                    PackageManager.GET_META_DATA);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//            return "";
//        }
//        Bundle bundle = ai.metaData;
//        String value = bundle.getString(key);
//        PushLog.e("极光：" + bundle.getString("JPUSH_APPKEY"));
//        return value;

//        String value;
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            String value = appInfo.metaData.getString(key);
            return value;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

}
