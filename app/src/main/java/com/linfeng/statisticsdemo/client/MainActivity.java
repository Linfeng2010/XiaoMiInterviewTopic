package com.linfeng.statisticsdemo.client;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import com.linfeng.saatisticsdemo.R;
import com.linfeng.statisticsdemo.Utils;
import com.linfeng.statisticsdemo.service.AppStatisticsService;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final boolean serviceRunning = Utils.isServiceRunning(this, AppStatisticsService.class.getName());
        if (!serviceRunning) {
            final Intent intent = new Intent(this, AppStatisticsService.class);
            final ComponentName componentName = startService(intent);
        }
    }
}
