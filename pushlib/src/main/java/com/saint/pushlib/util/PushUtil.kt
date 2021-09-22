package com.saint.pushlib.util

import android.content.Context
import android.content.pm.PackageManager

object PushUtil {
    /**
     * 获取Manifest 里面的meta-data
     *
     * @param context
     * @param key
     * @return
     */
    @JvmStatic
    fun getMetaData(context: Context, key: String): String? {
        return try {
            val appInfo = context.packageManager
                .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
            appInfo.metaData.getString(key)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            ""
        }
    }
}