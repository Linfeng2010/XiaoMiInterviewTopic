package com.linfeng.statisticsdemo;

import android.app.Application;
import android.os.Build;
import android.support.annotation.RequiresApi;

import io.realm.Realm;

/**
 * Created by linfeng on 2019/4/22  02:36
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public class App extends Application {
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onCreate() {
        super.onCreate();
        if (getProcessName().equalsIgnoreCase("com.linfeng.statistics_service"))
            Realm.init(this);
    }
}
