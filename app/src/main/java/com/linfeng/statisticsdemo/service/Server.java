package com.linfeng.statisticsdemo.service;

import android.os.RemoteException;

import com.linfeng.statisticsdemo.IStatisticsAidlInterface;

/**
 * Created by linfeng on 2019/4/21  19:54
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public class Server extends IStatisticsAidlInterface.Stub {
    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }

    @Override
    public void getAppInfo() throws RemoteException {

    }

    @Override
    public void getAppInfoList() throws RemoteException {

    }
}
