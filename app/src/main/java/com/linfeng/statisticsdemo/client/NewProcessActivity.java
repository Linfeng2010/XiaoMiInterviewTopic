package com.linfeng.statisticsdemo.client;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import com.linfeng.saatisticsdemo.R;
import com.linfeng.statisticsdemo.service.AppStatisticsService;

import java.lang.reflect.InvocationTargetException;

public class NewProcessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_process);

//        try {
////            registerService();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
    }


    private void registerService() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
//        Class<?> serviceManager = Class.forName("android.os.ServiceManager");
//        Method getService = serviceManager.getDeclaredMethod("getService", String.class);
//        IBinder rawBinder = (IBinder) getService.invoke(null, CLIPBOARD_SERVICE);

//        final Class<?> aClass = Class.forName("android.os.ServiceManager");
//        final Method getIServiceManager = aClass.getDeclaredMethod("getIServiceManager");
//        getIServiceManager.setAccessible(true);
//        final Object iServiceManager = getIServiceManager.invoke(null);
//        final Method addService = iServiceManager.getClass().getMethod("addService", String.class, IBinder.class, boolean.class);
//        final Object linfeng = addService.invoke(iServiceManager, "Linfeng", new AppStatisticsService(),false);
//        Log.d("Linfeng", "registerService() called=" + linfeng);

//        final Intent service = new Intent(this, AppStatisticsService.class);
//        startForegroundService(service);
//        bindService(inte)

    }
}
