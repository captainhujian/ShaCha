package com.example.luowenliang.idouban;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }
    /**
     * 获取ApplicationContext
     *
     * @return
     */
    public static Context getContext() {
        return mContext;
    }
}
