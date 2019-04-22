package com.linfeng.statisticsdemo.service.analyzer.worker;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by linfeng on 2019/4/22  02:55
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public class ThreadPoolExecutor implements IExecutor {

    private final java.util.concurrent.ThreadPoolExecutor threadPoolExecutor;

    public ThreadPoolExecutor() {
        threadPoolExecutor = new java.util.concurrent.
                ThreadPoolExecutor(5, 10, 10, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
    }

    @Override
    public void execute(Block runnable) {
        final Future<?> submit = threadPoolExecutor.submit(runnable);
    }


}
