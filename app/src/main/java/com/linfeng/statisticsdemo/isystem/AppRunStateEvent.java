package com.linfeng.statisticsdemo.isystem;

/**
 * Created by linfeng on 2019/4/21  23:04
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public class AppRunStateEvent extends SystemEvent {
    private String proceName;
    private int action;

    public AppRunStateEvent(String des, long timestamp, String proceName, long pId, int action) {
        super(des, timestamp,pId);
        this.proceName = proceName;
        this.action = action;
    }

    public String getProceName() {
        return proceName;
    }



    public int getAction() {
        return action;
    }

    public interface ACTION {
        int START = 1;
        int STOP = 2;
    }

    @Override
    public String toString() {
        return "AppRunStateEvent{" +
                "proceName='" + proceName + '\'' +
                ", action=" + ((action == ACTION.START) ? "启动" : "死亡") +
                '}';
    }
}
