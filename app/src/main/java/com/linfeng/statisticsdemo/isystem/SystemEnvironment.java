package com.linfeng.statisticsdemo.isystem;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

import static com.linfeng.statisticsdemo.isystem.AppRunStateEvent.ACTION.START;
import static com.linfeng.statisticsdemo.isystem.AppRunStateEvent.ACTION.STOP;
import static com.linfeng.statisticsdemo.isystem.MyProcess.RunAction.STATE_DEAD;

/**
 * Created by linfeng on 2019/4/21  20:20
 * Email zhanglinfengdev@163.com
 * Function 模拟系统 生产数据
 */
public class SystemEnvironment extends Thread implements SystemProxy {

    private static final int TIME_RATIO = 1;//分钟
    private static OnSysEventReceive sReceive;
    private static SystemEnvironment systemEnvironment;

    private List<MyProcess> pList = new ArrayList();


    @Override
    public void killPro(int pId) {
//TODO 杀掉制定进程
        for (MyProcess myProcess : pList) {
            if (myProcess.id == pId) {
                myProcess.state = STATE_DEAD;
                final AppRunStateEvent event =
                        new AppRunStateEvent("用户主动退出某个APP", System.currentTimeMillis(), myProcess.name, myProcess.id, STOP);
                sReceive.onReceive(event);
                return;
            }
        }
    }

    @Override
    public void banStarted(String packageName, long time) {
//TODO 禁止启动制定的时长
    }


    //    向系统注册一个监听APP活动信息的观察者
    public static SystemProxy register(OnSysEventReceive receive) {
        sReceive = receive;
        main(null);
        return systemEnvironment;
    }

    //    请求系统杀到指定APP
    public static void requestKillApp(String appName) {
        if (systemEnvironment != null)
            systemEnvironment.exitApp(appName);
    }

    private static void main(String[] args) {
        systemEnvironment = new SystemEnvironment();
        systemEnvironment.start();
    }

    @Override
    public synchronized void start() {
        super.start();
        handler.sendEmptyMessageDelayed(0, 0);
    }

    //随机模仿主动退出任意一个APP
    private void exitApp(String target) {
        final MyProcess randomAliveProcess = getRandomAliveProcess(target);
        if (randomAliveProcess != null) {

            final AppRunStateEvent event =
                    new AppRunStateEvent("用户主动退出某个APP", System.currentTimeMillis(), randomAliveProcess.name, randomAliveProcess.id, STOP);
            sReceive.onReceive(event);

            randomAliveProcess.state = STATE_DEAD;
            pList.remove(randomAliveProcess);
        }
    }

    //随机模仿主动启动新的APP
    private void launcherApp() {
        final MyProcess newProcess = createProcess();
        if (newProcess != null) {
            pList.add(newProcess);

            final AppRunStateEvent event =
                    new AppRunStateEvent("用户主动启动某个APP", newProcess.startTime, newProcess.name, newProcess.id, START);
            sReceive.onReceive(event);
        }
    }

    private MyProcess getRandomAliveProcess(String target) {
        MyProcess myProcess = null;
        try {
            if (target != null) {
                for (MyProcess process : pList) {
                    if (process.name.equalsIgnoreCase(target)) {
                        myProcess = process;
                        break;
                    }
                }
            } else {
                do {
                    myProcess = pList.get((int) (Math.random() * pList.size()));
                }
                while (myProcess == null || myProcess.state == STATE_DEAD);
            }
        } catch (Exception e) {
        }
        return myProcess;
    }


    private MyProcess createProcess() {
        final MyProcess process = ProcessPool.createProcess(pList);
        return process;
    }

    @Override
    public void run() {
        Looper.prepare();
        try {
            execute();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Looper.loop();
    }

    private void execute() throws InterruptedException {
        while (true) {
            final double random = Math.random() * 1000 * 60 * TIME_RATIO;

            for (int i = 0; i < random % 5; i++) {
                launcherApp();
            }

            Thread.sleep((long) random);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


            final double random = Math.random() * 100;
            for (int i = 0; i < random % 5; i++) {
                exitApp(null);
            }


            int delaed = 1000 * 60 * TIME_RATIO * 2;
            delaed = (int) (delaed * Math.random());
            sendEmptyMessageDelayed(0, delaed);
        }
    };


}
