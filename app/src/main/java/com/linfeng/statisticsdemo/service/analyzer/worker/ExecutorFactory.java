package com.linfeng.statisticsdemo.service.analyzer.worker;

/**
 * Created by linfeng on 2019/4/22  03:02
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public class ExecutorFactory {
    public static final String WORK_JOB_SCHEDULER = "jobService";
    public static final String WORK_THREADPOOL = "threadPool";

    public static IExecutor getWork(String type) {
        switch (type) {
            case WORK_JOB_SCHEDULER:
                return new JobServiceExecutor();
            case WORK_THREADPOOL:
                return new ThreadPoolExecutor();
        }
        return new EmptyExecutor();
    }


}
