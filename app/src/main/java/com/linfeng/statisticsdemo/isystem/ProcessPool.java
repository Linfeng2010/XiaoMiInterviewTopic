package com.linfeng.statisticsdemo.isystem;

import java.util.ArrayList;
import java.util.List;

import static com.linfeng.statisticsdemo.isystem.MyProcess.RunAction.STATE_RUNNING;

/**
 * Created by linfeng on 2019/4/22  17:29
 * Email zhanglinfengdev@163.com
 * Function details...模拟系统已安装的app
 */
public class ProcessPool {

    private static String[] installed_app = new String[]{
            "com.xiaomi_video",
            "com.xiaomi_pay",
            "com.xiaomi_camera",
            "com.xiaomi_music",
            "com.xiaomi_music_A",
            "com.xiaomi_music_B",
            "com.xiaomi_music_C",
            "com.xiaomi_music_D",
            "com.xiaomi_music_E",
    };
    final static MyProcess myProcess1;
    final static MyProcess myProcess2;
    final static MyProcess myProcess3;
    final static MyProcess myProcess4;
    final static MyProcess myProcess5;
    final static MyProcess myProcess6;
    final static MyProcess myProcess7;
    final static MyProcess myProcess8;
    final static MyProcess myProcess9;
    final static ArrayList<MyProcess> myProcesses;

    static {
        myProcess1 = new MyProcess(installed_app[0], 0, 0, System.currentTimeMillis());
        myProcess2 = new MyProcess(installed_app[1], 0, 0, System.currentTimeMillis());
        myProcess3 = new MyProcess(installed_app[2], 0, 0, System.currentTimeMillis());
        myProcess4 = new MyProcess(installed_app[3], 0, 0, System.currentTimeMillis());
        myProcess5 = new MyProcess(installed_app[4], 0, 0, System.currentTimeMillis());
        myProcess6 = new MyProcess(installed_app[5], 0, 0, System.currentTimeMillis());
        myProcess7 = new MyProcess(installed_app[6], 0, 0, System.currentTimeMillis());
        myProcess8 = new MyProcess(installed_app[7], 0, 0, System.currentTimeMillis());
        myProcess9 = new MyProcess(installed_app[8], 0, 0, System.currentTimeMillis());

        myProcesses = new ArrayList<>();
        myProcesses.add(myProcess1);
        myProcesses.add(myProcess2);
        myProcesses.add(myProcess3);
        myProcesses.add(myProcess4);
        myProcesses.add(myProcess5);
        myProcesses.add(myProcess6);
        myProcesses.add(myProcess7);
        myProcesses.add(myProcess8);
        myProcesses.add(myProcess9);
    }

    static long increasePid = 0;//进程Id最后会在记录表里面当主键  这里创建出来的新进程不允许重复PId

    public static MyProcess createProcess(List<MyProcess> oldRunProcess) {
        final MyProcess myProcess = myProcesses.get((int) (Math.random() * myProcesses.size()));
        myProcess.state = STATE_RUNNING;
        myProcess.id = ++increasePid == Long.MAX_VALUE ? 0 : increasePid;
        myProcess.startTime = System.currentTimeMillis();
        if (!oldRunProcess.contains(myProcess)) {
            return myProcess;
        }
        return null;
    }
}
