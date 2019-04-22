package com.linfeng.statisticsdemo.service.analyzer;

import com.linfeng.statisticsdemo.isystem.AppRunStateEvent;
import com.linfeng.statisticsdemo.service.AppRunRecord;
import com.linfeng.statisticsdemo.service.analyzer.worker.ExecutorFactory;
import com.linfeng.statisticsdemo.service.analyzer.worker.IExecutor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.linfeng.statisticsdemo.isystem.AppRunStateEvent.ACTION.START;
import static com.linfeng.statisticsdemo.isystem.AppRunStateEvent.ACTION.STOP;

/**
 * Created by linfeng on 2019/4/22  02:50
 * Email zhanglinfengdev@163.com
 * Function app活动事件分析处理类
 */
public class IEventAnalyzerImpl implements IEventAnalyzer {

    private static final long MAX_CONSUME_TIME = 1000 * 60 * 30;
    private final IExecutor executor;//用来执行数据的更新和读取操作 负责一个读写IO的环境  默认是线程池  也可以换成JobService+Jobscheduler形式 使用工厂来隔离创建过程
    private AnalyzerCallback callback;
    private List<AppRunRecord> mActiveProcess;

    public IEventAnalyzerImpl(AnalyzerCallback callback) {
        this.callback = callback;
        this.mActiveProcess = new ArrayList<>();
        mActiveProcess = Collections.synchronizedList(mActiveProcess);//简单起见 先这么处理一下
        executor = ExecutorFactory.getWork(ExecutorFactory.WORK_THREADPOOL);
        runDemonsLopper();
    }

    private void runDemonsLopper() {
        executor.execute((Realm realm) -> looper());
    }

    private void looper() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            final ArrayList<AppRunRecord> removed = new ArrayList<>();
            for (AppRunRecord activeProcess : mActiveProcess) {
                if (activeProcess.proState == 0) {//Dead
                    removed.add(activeProcess);
                } else {//Alive
                    if (activeProcess.isStrideDay()) {
//                        跨天的进程通知系统kill
                        final AnalyzerResult result = new AnalyzerResult();
                        result.data = activeProcess;
                        result.action = AnalyzerResult.ACTION.KILL;
                        callback.onAnanyzerResult(result);
                        continue;
                    }
                    final long consumeTime = activeProcess.getConsumeTime();
                    if (consumeTime > 1000 * 60 * 30 * 0.1) {//单次启动时长超过3分钟  通知KIll
                        final AnalyzerResult result = new AnalyzerResult();
                        result.data = activeProcess;
                        result.action = AnalyzerResult.ACTION.KILL;
                        callback.onAnanyzerResult(result);
                    }

                    final long todayConsume = getTodayConsume(activeProcess);
                    if (todayConsume > 1000 * 60 * 60 * 2 * 0.1) {//当天启动累计时长超过12分钟 通知当天不允许再次运行
                        final AnalyzerResult result = new AnalyzerResult();
                        result.data = activeProcess;
                        result.action = AnalyzerResult.ACTION.NO_START_TODAY;
                        callback.onAnanyzerResult(result);
                    }
                    activeProcess.proState = 0;//置零下一次轮训会移除内存mActiveProcess中的该条
                }
            }
            mActiveProcess.removeAll(removed);
        }
    }

    /**
     * 当前进程对应的app当天消耗的所有时间
     *
     * @param activeProcess
     */
    private long getTodayConsume(AppRunRecord activeProcess) {
        long consumeTime = activeProcess.getConsumeTime();

        //找出当天启动的且 已经结束的记录
        final RealmResults<AppRunRecord> appName = Realm.getDefaultInstance()
                .where(AppRunRecord.class).equalTo("proState", 0).and()
                .equalTo("year", activeProcess.year)
                .equalTo("month", activeProcess.month)
                .equalTo("dayOfMonth", activeProcess.dayOfMonth)
                .equalTo("strideDay", false)
                .findAll();

        for (AppRunRecord record : appName) {
            consumeTime += record.getConsumeTime();
        }
        return consumeTime;
    }

    @Override
    public void analyzer(AppRunStateEvent event) {
        executor.execute((Realm realm) -> {

            final AnalyzerResult analyzerResult = new AnalyzerResult();

            updateLocal(event, realm, analyzerResult);

            callback.onAnanyzerResult(analyzerResult);
        });
    }

    /**
     * 将新的Event转化为RunRecored同步到库
     * 添加到一个活动了de列表中  方便前面的Looper轮训处理
     * 系统只在创建 和销毁进程的时候才会发出消息 不会重复发送已经创建的消息  也不会重复发送已经销毁的消息  不同的pId代表一次独立的启动
     *
     * @param event
     * @param realm
     * @param analyzerResult
     */
    private void updateLocal(AppRunStateEvent event, Realm realm, AnalyzerResult analyzerResult) {

        AppRunRecord record = realm.where(AppRunRecord.class).equalTo("pId", event.getpId()).findFirst();

        if (event.getAction() == START) {
            if (record != null) {//每个进程id是不同的  每个进程只会在创建的时候发送一次启动消息 所以这部不可能会查到一个刚刚创建的全局唯一进程
//               之前启动过  更新记录
            } else {
//                插入新记录
                final Calendar instance = Calendar.getInstance();
                record = new AppRunRecord();
                record.pId = event.getpId();
                record.appName = event.getProceName();
                record.proState = event.getAction();

                record.startTime = event.getTimestamp();
                record.stopTime = System.currentTimeMillis();

                record.year = instance.get(Calendar.YEAR);
                record.month = instance.get(Calendar.MONTH);
                record.dayOfMonth = instance.get(Calendar.DAY_OF_MONTH);
            }
        } else if (event.getAction() == STOP) {
            if (record != null) {
                record.stopTime = event.getTimestamp();
                record.proState = event.getAction();

                final Calendar instance = Calendar.getInstance();
                record.yearStop = instance.get(Calendar.YEAR);
                record.monthStop = instance.get(Calendar.MONTH);
                record.dayOfMonthStop = instance.get(Calendar.DAY_OF_MONTH);
                record.isStrideDay();//结束的时候可以确定是否跨天了
            } else {
//                这里查不到 说明数据同出现问题了
                throw new RuntimeException("AppRunRecord Update Error!");
            }
        }

        realm.copyToRealmOrUpdate(record);

        mActiveProcess.add(record);


//        record.pId = event.getpId();
//        record.appName = event.getProceName();
//        record.proState = event.getAction();
//
//
////            record.fastLauncherTime = event.getTimestamp();
////            record.lastLauncherTime = event.getTimestamp();
////            record.consumeTime = 0;
//
////            record.todayFastLauncherTime = event.getTimestamp();
////            record.todayLastLauncherTime = event.getTimestamp();
////            record.todayConsumeTime = 0;
//
//
//        mActiveProcess.add(record);
//
//        realm.copyToRealmOrUpdate(record);
//    } else
//
//    {
////            首次启动
//        final AppRunRecord record = new AppRunRecord();
//        record.pId = event.getpId();
//        record.appName = event.getProceName();
//
//        record.fastLauncherTime = event.getTimestamp();
//        record.lastLauncherTime = event.getTimestamp();
//        record.consumeTime = 0;
//
//        record.todayFastLauncherTime = event.getTimestamp();
//        record.todayLastLauncherTime = event.getTimestamp();
//        record.todayConsumeTime = 0;
//
//        record.lastUpdateTime = event.getTimestamp();
//        record.proState = RUNNING;
//        record.launcherCount++;
//
//        mActiveProcess.add(record);
//
//        realm.copyToRealmOrUpdate(record);
//    }

//        if (event.getAction() == RUNNING) {//app启动消息
//
//        } else if (event.getAction() == STOP) {//App退出消息
//            final AppRunRecord record = new AppRunRecord();
//            record.pId = event.getpId();
//            record.appName = event.getProceName();
//
////            record.fastLauncherTime = event.getTimestamp();
//            record.lastLauncherTime = event.getTimestamp();
//            record.consumeTime += Math.abs(event.getTimestamp() - record.todayLastLauncherTime);
//
////            record.todayFastLauncherTime = event.getTimestamp();
//            record.todayLastLauncherTime = event.getTimestamp();
//            record.todayConsumeTime += Math.abs(event.getTimestamp() - record.todayLastLauncherTime);
//
//            record.lastUpdateTime = event.getTimestamp();
//            record.proState = 0;
//
//            mActiveProcess.add(record);
//        }
//}
    }
}
