package com.saint.pushlib.util;

import android.util.Log;

public class PushLog {
    private static boolean sDebug = false;

    public static final String TAG = "PushLibLog";

    public static void i(String log) {
        if (sDebug) {
            Log.i(TAG, log);
        }
    }

    public static void d(String log) {
        if (sDebug) {
            Log.d(TAG, log);
        }
    }

    public static void e(String log) {
        if (sDebug) {
            Log.e(TAG, log);
        }
    }

    public static void e(String log, Throwable throwable) {
        if (sDebug) {
            Log.e(TAG, log, throwable);
        }
    }

    public static void setDebug(boolean isDebug) {
        sDebug = isDebug;
    }

    public static boolean isDebug() {
        return sDebug;
    }

}
