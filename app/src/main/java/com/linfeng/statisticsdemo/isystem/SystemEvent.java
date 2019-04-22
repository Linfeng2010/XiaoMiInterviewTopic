package com.linfeng.statisticsdemo.isystem;

/**
 * Created by linfeng on 2019/4/21
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public class SystemEvent {
    private String des;
    private long timestamp;
    private long pId;


    public SystemEvent(String des, long timestamp, long pId) {
        this.des = des;
        this.timestamp = timestamp;
        this.pId = pId;
    }

    public String getDes() {
        return des;
    }

    public long getpId() {
        return pId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "SystemEvent{" +
                "des='" + des + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
