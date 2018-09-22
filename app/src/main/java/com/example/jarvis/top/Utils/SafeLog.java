package com.example.jarvis.top.Utils;

import android.util.Log;

import com.example.jarvis.top.BuildConfig;

public class SafeLog {
    private static String TAG = "TagDefault";

    public static void Logd(String msg){
        if(!isRelease()) {
            Log.d(TAG, msg);
        }
    }

    public static void Logd(String tag, String msg){
        if(!isRelease()) {
            Log.d(tag, msg);
        }
    }

    public static void Logw(String msg){
        if(!isRelease()) {
            Log.w(TAG, msg);
        }
    }

    public static void Logw(String tag, String msg){
        if(!isRelease()) {
            Log.w(tag, msg);
        }
    }

    public static void Loge(String msg){
        if(!isRelease()) {
            Log.e(TAG, msg);
        }
    }

    public static void Loge(String tag, String msg){
        if(!isRelease()) {
            Log.e(tag, msg);
        }
    }

    public static void Loge(String tag, String msg, Throwable throwable){
        if(!isRelease()) {
            Log.e(tag, msg, throwable);
        }
    }

    public static void Loge(String msg, Throwable throwable){
        if(!isRelease()) {
            Log.e(TAG, msg, throwable);
        }
    }

    private static boolean isRelease(){
        return !BuildConfig.DEBUG;
    }

}
