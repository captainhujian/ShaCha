package com.example.luowenliang.idouban.moviedetail.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.util.Log;


import java.util.List;

public class GetResourcePackageName {

    private String resourceAppName;
    private Context context;


    public GetResourcePackageName(String resourceAppName, Context context) {
        this.resourceAppName = resourceAppName;
        this.context = context;
        getAppPackageName();
    }

    public String getAppPackageName() {
        //当前应用pid
        final PackageManager packageManager = context.getPackageManager();
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        // get all apps
        final List<ResolveInfo> apps = packageManager.queryIntentActivities(mainIntent, 0);
        for (int i = 0; i <apps.size() ; i++) {
            String name = apps.get(i).activityInfo.packageName;
            String appLabel = getApplicationLabel(context,name);
            if(!name.contains("Samsung")&&!name.contains("android")&&resourceAppName.contains(appLabel)){
                Log.d("包名", "getAppProcessName: " + apps.get(i).activityInfo.packageName);
                return name;
            }
        }
        return null;
    }
    /**
     * 根据包名获得应用名
     */
    public String getApplicationLabel(Context context, String pkgName){
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo appInfo = pm.getApplicationInfo(pkgName, PackageManager.GET_META_DATA);
            return (String) pm.getApplicationLabel(appInfo);
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
