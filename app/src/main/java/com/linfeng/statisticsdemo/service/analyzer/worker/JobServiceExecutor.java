package com.linfeng.statisticsdemo.service.analyzer.worker;

import android.app.job.JobParameters;
import android.app.job.JobService;

public class JobServiceExecutor extends JobService implements IExecutor {
    @Override
    public boolean onStartJob(JobParameters params) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }


    @Override
    public void execute(Block runnable) {

    }
}