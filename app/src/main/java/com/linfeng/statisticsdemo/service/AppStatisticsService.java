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

import com.linfeng.saatisticsdemo.R;
import com.linfeng.statisticsdemo.client.MainActivity;

/**
 * Created by linfeng on 2019/4/21  18:16
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public class AppStatisticsService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        return new Server();
    }

    @Override
    public void onCreate() {
        super.onCreate();
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification = new Notification.Builder(getApplicationContext())
                    .setChannelId(CHANNEL_ONE_ID)
                    .setContentTitle("StatistiscsService")
                    .setContentText("Running")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(icon)
                    .build();
        } else {
            notification = new Notification.Builder(getApplicationContext())
                    .setContentTitle("StatistiscsService")
                    .setContentText("Running")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(icon)
                    .build();
        }

        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        notification.contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);


        startForeground(1234, notification);
    }
}
