package com.linfeng.statisticsdemo.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.linfeng.saatisticsdemo.R;
import com.linfeng.statisticsdemo.client.MainActivity;
import com.linfeng.statisticsdemo.isystem.AppRunStateEvent;
import com.linfeng.statisticsdemo.isystem.OnSysEventReceive;
import com.linfeng.statisticsdemo.isystem.SystemEnvironment;
import com.linfeng.statisticsdemo.isystem.SystemProxy;
import com.linfeng.statisticsdemo.service.analyzer.AnalyzerResult;
import com.linfeng.statisticsdemo.service.analyzer.IEventAnalyzer;
import com.linfeng.statisticsdemo.service.analyzer.IEventAnalyzerImpl;

import static com.linfeng.statisticsdemo.service.analyzer.AnalyzerResult.ACTION.KILL;
import static com.linfeng.statisticsdemo.service.analyzer.AnalyzerResult.ACTION.NO_START_TODAY;

/**
 * Created by linfeng on 2019/4/21  18:16
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public class AppStatisticsService extends Service implements OnSysEventReceive, IEventAnalyzer.AnalyzerCallback {

    private IEventAnalyzer eventAnalyzer;
    private SystemProxy iSystem;

    @Override
    public void onReceive(AppRunStateEvent event) {
        Log.d("Linfeng", "OnReceiveEvent:" + event);
        if (eventAnalyzer != null) {
            eventAnalyzer.analyzer(event);
        }
    }

    //通知系统执行
    @Override
    public void onAnanyzerResult(AnalyzerResult result) {
        switch (result.action) {
            case KILL:
                iSystem.killPro((int) result.data.pId);
                break;
            case NO_START_TODAY:
                iSystem.banStarted(result.data.appName, (long) (1000 * 60 * 60 * 24 * 0.1));
                return;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new Server();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        eventAnalyzer = new IEventAnalyzerImpl(this);
        monitorSystem();
    }

    private void monitorSystem() {
        iSystem = SystemEnvironment.register(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void startForeground() {
        String CHANNEL_ONE_ID = "com.linfeng.test";
        String CHANNEL_ONE_NAME = "Channel One";
        NotificationChannel notificationChannel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ONE_ID, CHANNEL_ONE_NAME, 1);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);
        }

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Notification notification = null;
        Notification.Builder builder = new Notification.Builder(getApplicationContext())
                .setContentTitle("StatistiscsService")
                .setContentText("Running")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ONE_ID);
        }

        notification = builder.build();

        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        notification.contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);

        startForeground(1234, notification);
    }


}
